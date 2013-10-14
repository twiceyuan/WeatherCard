package info.twiceyuan.weather.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import info.twiceyuan.weather.R;
import info.twiceyuan.weather.domain.Weather;
import info.twiceyuan.weather.util.WeatherIconGetter;
import info.twiceyuan.weather.util.WeekConverter;
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

    final private int SLEEP_TIME = 500;
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

        cityView.setText(weather.getCity() +"  "+ weather.getDate_y() + "  " + weather.getWeek());

        // 添加今日详细天气，样式：Card1
        RelativeLayout todayWeather = (RelativeLayout) RelativeLayout.inflate(activity, R.layout.weather_detail_card1, null);;

        ImageView twIcon = (ImageView) ((RelativeLayout)todayWeather.getChildAt(0)).getChildAt(0);
        TextView twText = (TextView) (((RelativeLayout) todayWeather.getChildAt(0)).getChildAt(2));
        TextView tempText = (TextView) (((RelativeLayout) todayWeather.getChildAt(0)).getChildAt(1));
        TextView fxText = (TextView) ((LinearLayout)todayWeather.getChildAt(1)).getChildAt(0);
        TextView zwxText = (TextView) ((LinearLayout)todayWeather.getChildAt(1)).getChildAt(1);

        assert twText != null;
        assert tempText != null;
        assert fxText != null;
        assert zwxText != null;

        twIcon.setImageDrawable(WeatherIconGetter.getWatherIcon(activity,weather.getWeathericon()[0]));
        twText.setText(weather.getWeather()[0]);
        tempText.setText(weather.getTemp()[0]);
        fxText.setText("风力：" + weather.getWind()[0]);
        zwxText.setText("紫外线：" + weather.getUv_info());

        card_container = new NowLayout(activity);
        card_container.addView(todayWeather);
        handler.sendEmptyMessage(ADD_TO_DETAIL);

        SystemClock.sleep(SLEEP_TIME);

        // 获得当前星期数 1~7的形式
        int thisWeekint = WeekConverter.getWeekint(weather.getWeek());

        for (int i = 1; i <= 5; i++) {

            SystemClock.sleep(200);

            RelativeLayout nextDay = getCard(R.layout.weather_detail_card2);

            TextView weekText = (TextView) (nextDay.getChildAt(0));
            ImageView ndIcon = (ImageView) ((RelativeLayout)nextDay.getChildAt(1)).getChildAt(0); // nd = nextDay
            TextView ndWeather = (TextView) ((RelativeLayout)nextDay.getChildAt(1)).getChildAt(2);
            TextView ndTemp = (TextView) ((RelativeLayout)nextDay.getChildAt(1)).getChildAt(1);
            TextView ndFx = (TextView) nextDay.getChildAt(2);

            weekText.setText(WeekConverter.getWeek((thisWeekint + i) % 7));
            ndIcon.setImageDrawable(WeatherIconGetter.getWatherIcon(activity,weather.getWeathericon()[i]));
            ndWeather.setText(weather.getWeather()[i]);
            ndTemp.setText(weather.getTemp()[i]);
            ndFx.setText("风向：\t" + weather.getWind()[i]);

            card_container = new NowLayout(activity);
            card_container.addView(nextDay);
            handler.sendEmptyMessage(ADD_TO_DETAIL);
        }
    }

    // 创造一个Card
    RelativeLayout getCard(int layoutId) {
        RelativeLayout card = (RelativeLayout) RelativeLayout.inflate(activity, layoutId, null);
        return card;
    }

    // 设置卡片标题
    void setLineTitle(LinearLayout card, String context) {
        ((TextView) card.getChildAt(0)).setText(context);
    }

    // 设置卡片内容
    void setLineBody(LinearLayout card, String context) {
        LinearLayout temp = (LinearLayout)card.getChildAt(1);
        ((TextView) temp.getChildAt(0)).setText(context);
    }

}
