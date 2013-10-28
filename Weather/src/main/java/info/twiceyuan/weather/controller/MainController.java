package info.twiceyuan.weather.controller;

import java.io.InputStream;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.*;

import info.twiceyuan.weather.R;
import info.twiceyuan.weather.WeatherDetail;
import info.twiceyuan.weather.domain.Weather;
import info.twiceyuan.weather.util.*;
import info.twiceyuan.weather.view.CardViewTools;
import info.twiceyuan.weather.view.NowLayout;

public class MainController implements Button.OnClickListener {

    /** init github **/

    private Activity activity; // 主程序的activity

    private NowLayout card_container; // 用来显示卡片的布局
    private EditText inputText; // 城市输入框
    private Button button; // 查找按钮
    private Handler handler;
    private Weather weather; // 用来存储当前天气信息的对象
    private DatabaseTools dbTools;
    private LinearLayout card_parent;

    public final static int NOT_FOUND = 404;
    public final static int CLEAN = 0;
    public final static int REFRESH = 1024;
    public final static int ADD_ONE = 1993;
    public final static int EMPTY_INPUT = 408;

    public MainController(Activity activity) {

        this.activity = activity;

        init();
    }


    void init() {

        card_parent = (LinearLayout) activity.findViewById(R.id.card_panel);

        button = (Button) activity.findViewById(R.id.add_button);

        button.setOnClickListener(this);

        handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                if (msg.what == NOT_FOUND) {
                    /** 没有找到该城市 **/
                    Toast.makeText(activity, "城市不存在，请重新输入", Toast.LENGTH_SHORT).show();
                    inputText.setText("");

                } else if (msg.what == EMPTY_INPUT) {
                    /** 没有任何输入 **/
                    Toast.makeText(activity, "请输入所要查找的城市(县)名", Toast.LENGTH_SHORT).show();

                } else if (msg.what == CLEAN) {
                    /** 清除View中的所有CardView **/

                    card_parent.removeAllViews();

                } else if (msg.what == ADD_ONE) {
                    /** 添加一个 **/
                    card_parent.addView(card_container);
                    card_parent.invalidate();

                } else if(msg.what == REFRESH) {
                    /** 重新加载城市 */

                    card_parent.removeAllViews();
                    loadCity();
                }
            }
        };

        /** 数据库操作相关工具类 **/
        dbTools = new DatabaseTools(activity);

        loadCity();
    }

    /**
     * 加载城市列表 *
     */
    void loadCity() {

        List<String> cityList = dbTools.getCurCityList();

        Thread loadCurrent = new Thread(new LoadCurrentCity(cityList));
        loadCurrent.start();
    }


    /** 主页按钮 按下弹出城市输入窗口 **/
    @Override
    public void onClick(View v) {

        inputText = new EditText(activity);

        AlertDialog.Builder dialog = new AlertDialog.Builder(activity)
                .setTitle("请输入")
                .setMessage("请输入城市全称，如“徐州”")
                .setView(inputText)
                .setPositiveButton("确定", new InputButtonListener())
                .setNegativeButton("取消", new CancelButtonListener());

        dialog.show();

    }

    // 用来从网络获得信息并构造出Weather类的线程
    public class LoadCurrentCity implements Runnable {

        private List<String> cityNumbers;

        public LoadCurrentCity(List<String> cityNumbers) {
            this.cityNumbers = cityNumbers;
        }

        @Override
        public synchronized void run() {

            handler.sendEmptyMessage(CLEAN);

            // 新建一个卡片容器
            card_container = new NowLayout(activity);

            for (int i = 0; i < cityNumbers.size(); i++) {

                // http://m.weather.com.cn/data/[城市编号].html
                // http://m.weather.com.cn/data/101070201.html

                String url = "http://m.weather.com.cn/data/" + cityNumbers.get(i) + ".html";

                InputStream is = StreamGetter.get(url);

                card_container = new NowLayout(activity);
                final Weather current = WeatherGetter.get(is);

                weather = current;

                CardViewTools cvtools = new CardViewTools(activity, weather, handler);
                RelativeLayout cardView = cvtools.getCardView();

                card_container.addView(cardView);
                card_container.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        // 传递 Weather 参数并启动新的天气详情 Activity
                        Bundle bundle = new Bundle();

                        bundle.putSerializable("weather", current);

                        Intent intent = new Intent();
                        intent.putExtras(bundle);
                        intent.setClass(activity, WeatherDetail.class);
                        activity.startActivity(intent);
                    }
                });

                handler.sendEmptyMessage(ADD_ONE);
            }
        }
    }


    /** 城市选择窗口确定按钮监听器 **/
    private class InputButtonListener implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface mDialog, int i) {

            String cityName;
            try {
                cityName = inputText.getText().toString().trim();
            } catch (NullPointerException e) {
                handler.sendEmptyMessage(EMPTY_INPUT);
                return;
            }

            // 没有输入城市名时
            if(cityName.equals("")) {
                handler.sendEmptyMessage(EMPTY_INPUT);

                DialogTool.closeDialog(mDialog, false);
                return;
            }

            // 获取城市名，并清空输入框
            inputText.setText("");

            String cityNumber = dbTools.getCityNumber(cityName);

            if (cityNumber.equals("ERROR")) {
                handler.sendEmptyMessage(NOT_FOUND);

                DialogTool.closeDialog(mDialog, false);
                return;
            }

            // 添加城市到current中
            dbTools.addCity(cityNumber);

            // 并重新加载整个卡片布局
            loadCity();

            // 关闭对话框
            DialogTool.closeDialog(mDialog, true);
        }
    }

    // 取消按钮事件监听器
    private class CancelButtonListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {

            // 关闭窗口不执行任何操作
            DialogTool.closeDialog(dialog, true);
        }
    }



}
