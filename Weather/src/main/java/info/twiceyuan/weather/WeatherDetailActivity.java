package info.twiceyuan.weather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.widget.LinearLayout;
import android.widget.TextView;

import info.twiceyuan.weather.controller.DetailController;
import info.twiceyuan.weather.domain.Weather;
import info.twiceyuan.weather.view.NowLayout;

/**
 * Created by twiceyuan on 13-6-16.
 */
public class WeatherDetailActivity extends Activity {


    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_detail);

        new DetailController(this);
    }

}