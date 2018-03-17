/**
 * This version has preferences that give me error
 * next version will be without them. Just trying to 
 * launch before 2014 for tax reasons.
 */
package com.janpolzer.czech;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

import com.janpolzer.czech.R;

public class MainActivity extends Activity {

	public final static String CATEGORY_NAME = "Category";

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setScreenOrientation();
		setContentView(R.layout.activity_main);

		// super.onStart();

		// Action bar work around old APIs
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			ActionBar actionBar = getActionBar();
			actionBar.setTitle("Czech for Travel");
			actionBar.setSubtitle("Full Version");
			actionBar.show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_activity_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}

	// Action bar options
	@Override
	public boolean onOptionsItemSelected(MenuItem itemM) {
		switch (itemM.getItemId()) {
//		case R.id.action_settings:
//			Intent p = new Intent("com.janpolzer.PREFS");
//			startActivity(p);
//			break;
		case R.id.action_about:
			Intent a = new Intent("com.janpolzer.ABOUT");
			startActivity(a);
			break;
		case R.id.action_exit:
			exitByBackKey();
			break;
		}
		return false;
	}

	// Buttons
	public void onCategoryButtonClick(View target) {
		Intent clickedCat = new Intent(this, CategoryActivity.class);
		clickedCat.putExtra(CATEGORY_NAME, target.getContentDescription());
		// clickedCat.putExtra(CATEGORY_NAME,
		// target.getContentDescription().toString());
		startActivity(clickedCat);
	}

	// Exiting
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			SharedPreferences sharedPrefs = PreferenceManager
					.getDefaultSharedPreferences(getBaseContext());
			boolean exitConfirmation = sharedPrefs.getBoolean(
					"key_checkboxexit", true);
			if (exitConfirmation) {
				exitByBackKey();
			} else {
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	// Exiting with confirmation
	protected void exitByBackKey() {
//		if (getExitConfirmation()) {
		
			// Make dialog box
	//		View checkBoxView = View.inflate(this, R.layout.checkbox, null);
	//		final CheckBox checkBoxExit = (CheckBox) checkBoxView.findViewById(R.id.checkbox);
//			checkBoxExit.setText("Show this confirmation?");
//			checkBoxExit.setChecked(true);
			AlertDialog.Builder exitBuilder = new AlertDialog.Builder(this);
			exitBuilder.setIcon(R.drawable.ic_launcher_czech);
			exitBuilder.setTitle("Exit");
			exitBuilder.setMessage("Are you sure you want to quit?");
//			exitBuilder.setView(checkBoxView);
			exitBuilder.setPositiveButton("Quit",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface d, int m) {

//							if (checkBoxExit.isChecked()) {
//								setExitConfirmation(true);
//							} else {
//								setExitConfirmation(false);
//							}
							finish();
						}
					});
			exitBuilder.setNegativeButton("Cancel",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface d, int m) {
//							if (checkBoxExit.isChecked()) {
//								setExitConfirmation(true);
//							} else {
//								setExitConfirmation(false);
//							}
							// Do nothing
						}
					});
			exitBuilder.show();
	//	}
	}

//	private void setExitConfirmation(boolean b) {
//
//		final SharedPreferences sharedPrefs = PreferenceManager
//				.getDefaultSharedPreferences(getBaseContext());
//		Editor editor = sharedPrefs.edit();
//		if (b == true) {
//			editor.putBoolean("key_checkboxexit", true);
//		} else {
//			editor.putBoolean("key_checkboxexit", false);
//		}
//		editor.commit();
//	}

//	private boolean getExitConfirmation() {
//		SharedPreferences sharedPrefs = PreferenceManager
//				.getDefaultSharedPreferences(getBaseContext());
//		boolean wantConfirmationShow = sharedPrefs.getBoolean(
//				"key_checkboxexit", true);
//		if (wantConfirmationShow != true) {
//			return false;
//		}
//		return true;
//	}

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
	protected void onRestart() {
		super.onRestart();
//		setScreenOrientation();
		setContentView(R.layout.activity_main);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}

	// // Full screen mode (should use only before android 4.0)
	// private void setAppFullScreen() {
	// requestWindowFeature(Window.FEATURE_NO_TITLE);
	// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	// WindowManager.LayoutParams.FLAG_FULLSCREEN);
	// }

}
