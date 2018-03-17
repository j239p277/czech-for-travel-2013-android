package com.janpolzer.czech;



import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.janpolzer.czech.R;

public class PreferencesActivity extends PreferenceActivity implements
		OnSharedPreferenceChangeListener {

	public final static String PREFS_NAME = "Prefs";

	@Override
	protected void onRestoreInstanceState(Bundle state) {
		super.onRestoreInstanceState(state);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setScreenOrientation();
		addPreferencesFromResource(R.xml.preferences);
	}

	// Lock the screen orientation
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	private void setScreenOrientation() {
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
	}

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

	@SuppressWarnings("deprecation")
	@Override
	protected void onResume() {
		super.onResume();
		// Set up a listener whenever a key changes
		getPreferenceScreen().getSharedPreferences()
				.registerOnSharedPreferenceChangeListener(this);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onPause() {
		super.onPause();
		// Unregister the listener whenever a key changes
		getPreferenceScreen().getSharedPreferences()
				.unregisterOnSharedPreferenceChangeListener(this);
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		setScreenOrientation();
		setContentView(R.layout.activity_main);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

}
