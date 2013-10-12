package info.twiceyuan.weather.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.widget.LinearLayout;
import android.widget.TextView;

import info.twiceyuan.weather.R;
import info.twiceyuan.weather.domain.Weather;
import info.twiceyuan.weather.view.NowLayout;

/**
 * Created by twiceyuan on 13-10-12.
 */
public class DetailController implements Runnable {

    Activity activity;
    Handler handler;

    private TextView cityView;
    private Weather weather;
    private LinearLayout cardLayout;
    private NowLayout card_container;

    final private int SLEEP_TIME = 100;
    final private int ADD_TO_DETAIL = 1984;

    public DetailController(Activity acitity) {

        this.activity = acitity;

        Intent intent = acitity.getIntent();
        Bundle bundle = intent.getExtras();

        cityView = (TextView) acitity.findViewById(R.id.city_title);
        cardLayout = (LinearLayout) acitity.findViewById(R.id.detail_layout);

        weather = (Weather) bundle.getSerializable("weather");

        handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case ADD_TO_DETAIL:
                        // 添加当前的Card
                        cardLayout.addView(card_container);
                        break;
                }
            }
        };

        Thread load = new Thread(this);
        load.start();
    }


    @Override
    public void run() {
        cardLayout.removeAllViews();

        cityView.setText(weather.getCity());

        // 添加天气卡片
        LinearLayout todaWeather = getCard();
        setLineTitle(todaWeather, "今日天气");
        setLineBody(todaWeather, weather.getWeather()[0]);


        card_container = new NowLayout(activity);
        card_container.addView(todaWeather);
        handler.sendEmptyMessage(ADD_TO_DETAIL);

        SystemClock.sleep(SLEEP_TIME);

        // 添加温度卡片
        LinearLayout todayTemp = getCard();
        setLineTitle(todayTemp, "今日温度");
        setLineBody(todayTemp, weather.getTemp()[0]);

        card_container = new NowLayout(activity);
        card_container.addView(todayTemp);
        handler.sendEmptyMessage(ADD_TO_DETAIL);

        SystemClock.sleep(SLEEP_TIME);

        // 添加风力指数
        LinearLayout todayWind = getCard();
        setLineTitle(todayWind, "风力");
        setLineBody(todayWind, weather.getWind()[0]);

        card_container = new NowLayout(activity);
        card_container.addView(todayWind);
        handler.sendEmptyMessage(ADD_TO_DETAIL);

        SystemClock.sleep(SLEEP_TIME);

        // 添加紫外线指数
        LinearLayout todayUr = getCard();
        setLineTitle(todayUr, "紫外线");
        setLineBody(todayUr, weather.getUv_info());

        card_container = new NowLayout(activity);
        card_container.addView(todayUr);
        handler.sendEmptyMessage(ADD_TO_DETAIL);

        SystemClock.sleep(SLEEP_TIME);

        // 获得当前星期数 1~7的形式
        int thisWeek = getWeekint(weather.getWeek());

        for (int i = 1; i <= 5; i++) {

            SystemClock.sleep(50);

            LinearLayout nextWeek = getCard();
            // 以星期作为标题
            setLineTitle(nextWeek, getWeek((thisWeek + i) % 7));

            String thisWeekInfo =

                    "天气：" + weather.getWeather()[i] + "\n" +
                            "温度：" + weather.getTemp()[i] + "\n" +
                            "风向：" + weather.getWind()[i];

            setLineTitle(nextWeek, weather.getUv_info());
            setLineBody(nextWeek,thisWeekInfo);

            card_container = new NowLayout(activity);
            card_container.addView(nextWeek);
            handler.sendEmptyMessage(ADD_TO_DETAIL);
        }
    }

    // 创造一个Card
    LinearLayout getCard() {
        LinearLayout card = (LinearLayout) LinearLayout.inflate(activity, R.layout.weather_detail_card, null);
        // ((ViewGroup)card.getParent()).removeAllViews();
        return card;
    }

    // 设置卡片标题
    void setLineTitle(LinearLayout card, String context) {
        ((TextView) card.getChildAt(0)).setText(context);
    }

    // 设置卡片内容
    void setLineBody(LinearLayout card, String context) {
        ((TextView) card.getChildAt(1)).setText(context);
    }

    // 根据字符串输出星期几，比如输入“星期一”，则返回1
    private int getWeekint(String week) {
        String[] strings = {"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期天"};
        for (int i = 0; i < strings.length; i++) {
            if (strings[i].equals(week)) {
                return i;
            }
        }
        return 0;
    }

    // 和上面那个方法相反
    private String getWeek(int i) {
        return (new String[]{"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期天"})[i % 7];
    }
}