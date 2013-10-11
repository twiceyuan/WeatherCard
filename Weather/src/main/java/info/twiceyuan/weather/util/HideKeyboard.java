package info.twiceyuan.weather.util;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by twiceyuan on 13-5-22.
 */
public class HideKeyboard {

    public static void hide(Activity activity) {
        ((InputMethodManager) activity
                .getSystemService(activity.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(activity.getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

}
