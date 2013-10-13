package info.twiceyuan.weather.util;

import info.twiceyuan.weather.domain.Weather;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

/**
 * Created by twiceyuan on 13-5-27.
 * Use for get the weather object from json file
 */

public class WeatherGetter {

    public static Weather get(InputStream is) {

        JSONObject jo;

        Weather weather = new Weather();

        try {

            jo = new JSONObject(Stream2String.get(is)).getJSONObject("weatherinfo");
            weather = getAttributes(jo, weather);

        } catch (JSONException e) {
            System.out.println("JSON文件获得错误：" + e.toString());
            e.printStackTrace();
        }

        return weather;
    }

    // Get the weather's attributes
    private static Weather getAttributes(JSONObject jo,Weather w) {

        try {
            w.setCity(jo.getString("city"));
            w.setDate_y(jo.getString("date_y"));
            w.setWeek(jo.getString("week"));
            w.setCityid(jo.getString("cityid"));

            String[] temp = new String[6];
            temp[0] = jo.getString("temp1");
            temp[1] = jo.getString("temp2");
            temp[2] = jo.getString("temp3");
            temp[3] = jo.getString("temp4");
            temp[4] = jo.getString("temp5");
            temp[5] = jo.getString("temp6");

            // 调整温度格式
            for(int i = 0;i < temp.length;i++) {
                temp[i] = exchange(temp[i]);
            }

            w.setTemp(temp);

            String[] tempF = new String[6];
            tempF[0] = jo.getString("tempF1");
            tempF[1] = jo.getString("tempF2");
            tempF[2] = jo.getString("tempF3");
            tempF[3] = jo.getString("tempF4");
            tempF[4] = jo.getString("tempF5");
            tempF[5] = jo.getString("tempF6");
            w.setTempF(tempF);

            String[] weather = new String[6];
            weather[0] = jo.getString("weather1");
            weather[1] = jo.getString("weather2");
            weather[2] = jo.getString("weather3");
            weather[3] = jo.getString("weather4");
            weather[4] = jo.getString("weather5");
            weather[5] = jo.getString("weather6");
            w.setWeather(weather);

            String[] wind = new String[6];
            wind[0] = jo.getString("wind1");
            wind[1] = jo.getString("wind2");
            wind[2] = jo.getString("wind3");
            wind[3] = jo.getString("wind4");
            wind[4] = jo.getString("wind5");
            wind[5] = jo.getString("wind6");
            w.setWind(wind);

            int[] weathericon = new int[6];
            weathericon[0] = jo.getInt("img1");
            weathericon[1] = jo.getInt("img2");
            weathericon[2] = jo.getInt("img3");
            weathericon[3] = jo.getInt("img4");
            weathericon[4] = jo.getInt("img5");
            weathericon[5] = jo.getInt("img6");
            w.setWeathericon(weathericon);

            w.setMsg(jo.getString("index")); //
            w.setMeg_advice(jo.getString("index_d"));
            w.setMsg48(jo.getString("index48")); // 48С
            w.setMeg48_advice(jo.getString("index48_d"));
            w.setUv_info(jo.getString("index_uv")); //
            w.setUv48_info(jo.getString("index48_uv")); // 48С
            w.setCleancar(jo.getString("index_xc")); //
            w.setTravel(jo.getString("index_tr")); //
            w.setComfort(jo.getString("index_co")); //
            w.setMorningexer(jo.getString("index_cl")); //
            w.setDrying(jo.getString("index_ls")); //
            w.setCold(jo.getString("index_ag")); //

        } catch (JSONException e) {
            System.out.println("设置Weather属性失败" + e.toString());
            e.printStackTrace();
            return null;
        }
        return w;
    }

    // 调整温度格式为[低温]～[高温]的格式 如：原为21～15，则替换为15~21
    private static String exchange(String origin) {

        int number[] = new int[2];
        String numberStr[] = origin.split("~");
        for(int i = 0;i < 2;i++) {
            number[i] = Integer.parseInt(numberStr[i].substring(0,numberStr[i].length()-1));
        }
        if(number[0] < number[1]) {
            return number[0] + "℃~" + number[1] + "℃";
        } else {
            return number[1] + "℃~" + number[0] + "℃";
        }
    }
}
