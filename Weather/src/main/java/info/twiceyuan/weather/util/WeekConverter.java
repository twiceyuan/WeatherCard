package info.twiceyuan.weather.util;

/**
 * Created by twiceyuan on 13-10-13.
 */

/** 星期几与数字间的转换 **/
public class WeekConverter {

    // 根据字符串输出星期几，比如输入“星期一”，则返回1
    public static int getWeekint(String week) {
        String[] strings = {"星期一","星期二","星期三","星期四","星期五","星期六","星期天"};
        for(int i = 0;i < strings.length;i++) {
            if(strings[i].equals(week)) {
                return i;
            }
        }
        return 0;
    }

    // 和上面那个方法相反
    public static String getWeek(int i) {
        return (new String[]{"星期一","星期二","星期三","星期四","星期五","星期六","星期天"})[i % 7];
    }
}
