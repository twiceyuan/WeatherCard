package info.twiceyuan.weather.util;

import android.content.DialogInterface;

import java.lang.reflect.Field;

/**
 * 此类终中的方法用于Dialog的监听器OnClick方法中，传递DialogInterface参数
 */
public class CloseDialog {

    /** 是否关闭对话框 **/
    public static void closeDialog(DialogInterface mDialog, boolean flag) {

        /** 不关闭对话框的方法 来源：http://www.cnblogs.com/sipher/articles/2419958.html */
        try {
            Field field = mDialog.getClass().getSuperclass().getDeclaredField("mShowing");

            field.setAccessible(true);
            field.set(mDialog, flag);

        }catch(Exception e) {

            e.printStackTrace();
        }

    }
}
