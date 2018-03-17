package com.janpolzer.czech;



import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.janpolzer.czech.R;


public class AboutActivity extends Activity {

	@Override
	protected void onRestoreInstanceState(Bundle state) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(state);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setScreenOrientation();
		setContentView(R.layout.activity_about);
		

		Button ok = (Button) findViewById(R.id.but_about_ok);
		ok.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
	}

//	// Lock the screen orientation
//	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
//	private void setScreenOrientation() {
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
//			SharedPreferences sp = PreferenceManager
//					.getDefaultSharedPreferences(getBaseContext());
//			String screenOrientation = sp.getString("key_orientation", "1");
//			if (screenOrientation.contentEquals("1")) {
//				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
//			} else {
//				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
//			}
//		}
//	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.pref_activity_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}

	// Action bar options
	@Override
	public boolean onOptionsItemSelected(MenuItem itemM) {
		switch (itemM.getItemId()) {
		case R.id.action_back:
			finish();
			break;

		}
		return false;
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	//	setScreenOrientation();
		setContentView(R.layout.activity_main);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}

}
