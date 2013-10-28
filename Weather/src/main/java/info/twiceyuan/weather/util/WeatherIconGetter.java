package info.twiceyuan.weather.util;

import android.app.Activity;
import android.graphics.drawable.Drawable;

import info.twiceyuan.weather.R;

/**
 * 天气图标选择。输入图标编号，输出Drawable
 */
public class WeatherIconGetter {

    public static Drawable getWatherIcon(Activity activity,int icon) {

        int iconId;

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
