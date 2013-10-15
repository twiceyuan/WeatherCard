package info.twiceyuan.weather;

import info.twiceyuan.weather.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class WelcomeActivity extends Activity {

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                setContentView(R.layout.welcome);
            }
        };

        init();
    }

    private void init() {
        Thread thread = new Thread(new InitRunnable());
        thread.start();
    }

    private class InitRunnable implements Runnable {

        @Override
        public void run() {
            handler.sendEmptyMessage(0);
            SystemClock.sleep(800);
            handler.sendEmptyMessage(0);

            Intent intent = new Intent();
            intent.setClass(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            WelcomeActivity.this.finish();
        }
    }

}
