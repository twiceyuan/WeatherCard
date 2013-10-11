package info.twiceyuan.weather.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.os.Handler;

import info.twiceyuan.weather.R;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author twiceyuan
 *
 * ****** 数据库操作类 *******
 * 构造方法：    如果数据库存在，则设置属性中的db为所要操作的数据库；如果不存在
 *             如果不存在，则从res/raw中复制文件到存储卡
 *
 */
public class DatabaseTools {

    private SQLiteDatabase db;
    private final String dbName = "weather.db";
    private final String packageName = "info.twiceyuan.weather";
    private final String dbDirPath = "/sdcard/Android/data/" + packageName;
    private final String dbPath = dbDirPath + "/" + dbName;
    private Handler handler;

    public DatabaseTools(Activity activity, Handler handler) {

        File dir = new File("/sdcard/Android/data/info.twiceyuan.weather/");
        File dbFile = new File(dir,dbName);
        this.handler = handler;

        if(!dir.exists()) {
            dir.mkdirs();
        }

        if (!dbFile.exists()) {
            copyDbFile(activity, dbFile);
        }

        db = activity.openOrCreateDatabase(dbPath, Activity.MODE_PRIVATE, null);
    }

    /** 复制db文件 **/
    private synchronized void copyDbFile(Activity activity, File objectfile) {

        InputStream is = activity.getResources().openRawResource(R.raw.weather);
        try {

            objectfile.createNewFile();
            FileOutputStream fos = new FileOutputStream(objectfile);

            int rd;
            byte buffer[] = new byte[1024];

            while ((rd = is.read(buffer)) != -1) {
                fos.write(buffer, 0, rd);
            }

            is.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    /** 查找城市编号 如果不存在，则返回ERROR **/
    public String getCityNumber(String cityname) {

        Cursor cursor = db.rawQuery("select citynumber from city where cityname=?;", new String[]{cityname});

        if (cursor.getCount() == 0) {
            return "ERROR";
        }
        cursor.moveToFirst();

        return cursor.getString(0);
    }

    /** 添加保存的城市 **/
    public String addCity(String cityNumber) {

        // 查看原来数据库中是否有这个数据
        Cursor cursor;
        cursor = db.rawQuery("select * from current where city=?",new String[]{cityNumber});
        if(cursor.getCount() != 0) {
            return "EXIST";
        }

        // 获得当前时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String nowTime = format.format(new Date());

        // 获得原来数据库中有多少项
        cursor = db.rawQuery("select * from current;", new String[]{});
        int currentNumber = cursor.getCount();

        db.execSQL("insert into current values(?,?,?);", new String[]{
                String.valueOf(currentNumber + 1),
                cityNumber, nowTime}
        );
        return "SUCCESS";
    }

    /** 查看已经添加的城市号码列表 **/
    public ArrayList<String> getCurCityList() {
        ArrayList<String> clist = new ArrayList<String>();

        Cursor cursor = db.rawQuery("select city from current;",new String[]{});
        while(cursor.moveToNext()) {
            clist.add(cursor.getString(0));
        }
        return clist;
    }

}
