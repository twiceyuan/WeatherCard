package info.twiceyuan.weather.view;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import info.twiceyuan.weather.R;
import info.twiceyuan.weather.domain.Weather;
import info.twiceyuan.weather.util.WeatherIconGetter;

/**
 * 获得单个卡片的View，类型为RelativeLayout
 * */

public class GetCardView {

	public static RelativeLayout get(Activity activity, Weather weather) {

		TextView cityNameView;
		Button addButton;
		ImageView weatherImageView;
		TextView weatherView;
		TextView tempView;
		TextView[] weekViews = new TextView[4];
		ImageView[] weekWeatherIconView = new ImageView[4];
		TextView[] weekUps = new TextView[4];
		TextView[] weekDowns = new TextView[4];

        int iweek = getWeekint(weather.getWeek());

		RelativeLayout layout = (RelativeLayout) RelativeLayout.inflate(
				activity, R.layout.cardview, null);
		
		RelativeLayout toplayout = (RelativeLayout) layout.getChildAt(0);
		cityNameView = (TextView) toplayout.getChildAt(1);
		addButton = (Button) toplayout.getChildAt(0);

		RelativeLayout todayLayout = (RelativeLayout) layout.getChildAt(1);
		weatherImageView = (ImageView) todayLayout.getChildAt(0);
		tempView = (TextView) todayLayout.getChildAt(1);
		weatherView = (TextView) todayLayout.getChildAt(2);

		LinearLayout weeklayout = (LinearLayout) layout.getChildAt(2);
		for (int i = 0; i < 4; i++) {
			LinearLayout aday = (LinearLayout) weeklayout.getChildAt(i);
			weekViews[i] = (TextView) aday.getChildAt(0);
			weekWeatherIconView[i] = (ImageView) aday.getChildAt(1);
			weekUps[i] = (TextView) aday.getChildAt(2);
			weekDowns[i] = (TextView) aday.getChildAt(3);
		}
		
		cityNameView.setText(weather.getCity());
		addButton.setText("添加");
		weatherView.setText(weather.getWeather()[0]);
        weatherImageView.setImageDrawable(WeatherIconGetter.getWatherIcon(activity,weather.getWeathericon()[0]));
		tempView.setText(weather.getTemp()[0]);


		for(int i = 0;i < 4;i++) {
			weekViews[i].setText(getWeek(iweek + i + 1));
            weekWeatherIconView[i].setImageDrawable(WeatherIconGetter.getWatherIcon(activity, weather.getWeathericon()[i + 1]));
			weekUps[i].setText(weather.getTemp()[i+1].split("~")[0]);
			weekDowns[i].setText(weather.getTemp()[i+1].split("~")[1]);
		}
		return layout;
	}

    // 根据字符串输出星期几，比如输入“星期一”，则返回1
    private static int getWeekint(String week) {
        String[] strings = {"星期一","星期二","星期三","星期四","星期五","星期六","星期天"};
        for(int i = 0;i < strings.length;i++) {
            if(strings[i].equals(week)) {
                return i;
            }
        }
        return 0;
    }

	private static String getWeek(int i) {
		return (new String[]{"星期一","星期二","星期三","星期四","星期五","星期六","星期天"})[i % 7];
	}
}
