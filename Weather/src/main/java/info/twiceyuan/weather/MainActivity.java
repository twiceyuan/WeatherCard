package info.twiceyuan.weather;

import android.app.Activity;
import android.os.Bundle;

import info.twiceyuan.weather.controller.MainController;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		new MainController(this);
	}

}
