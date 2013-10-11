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
        weatherImageView.setImageDrawable(getWatherIcon(activity,weather.getWeathericon()[0]));
		tempView.setText(weather.getTemp()[0]);


		for(int i = 0;i < 4;i++) {
			weekViews[i].setText(getWeek(iweek + i + 1));
            weekWeatherIconView[i].setImageDrawable(getWatherIcon(activity,weather.getWeathericon()[i+1]));
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

    // 天气图标选择。输入图标编号，输出Drawable
    private static Drawable getWatherIcon(Activity activity,int icon) {

        int iconId = 99;
        switch (icon) {
            case 0: // 晴
                iconId = R.drawable.weather_sunny;
                break;
            case 1: // 多云
            case 99:
                iconId = R.drawable.weather_cloud;
                break;
            case 2: // 阴
                iconId = R.drawable.weather_cloudy;
                break;
            case 3: // 雨
            case 9:
            case 10:
            case 11:
            case 12:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
                iconId = R.drawable.weather_rain;
                break;
            case 4: // 雷阵雨
            case 5:
                iconId = R.drawable.weather_storm;
                break;
            case 6: // 雨夹雪
                iconId = R.drawable.weather_rain_snow;
                break;
            case 13: // 雪
            case 14:
            case 15:
            case 16:
            case 17:
                iconId = R.drawable.weather_snow;
                break;
            case 20: // 沙尘
            case 29:
            case 30:
            case 31:
                iconId = R.drawable.weather_fog;
                break;

            default:
                iconId = R.drawable.weather_cloud;
        }
        return activity.getResources().getDrawable(iconId);
    }
}
