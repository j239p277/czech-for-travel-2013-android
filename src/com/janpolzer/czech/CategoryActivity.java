/**
 * @author janman74
 * 
 */

package com.janpolzer.czech;

import com.janpolzer.czech.R;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class CategoryActivity extends Activity {

	private boolean isInFocus = false;
	private ArrayAdapterCzech adapter;
	private TextView selectedPhraseEN;
	private TextView selectedPhraseCSbig;
	private TextView selectedPhraseCS1;
	private TextView selectedPhraseCS2;
	private TextView selectedPhraseCS3;
	String[] itemsEN;
	String[] itemsCSbig;
	String[] itemsCS1;
	String[] itemsCS2;
	String[] itemsCS3;
	int[] itemsAudio;

	// int selectedRow = ArrayAdapterCzech.getSelectedPosition();
	MediaPlayer phraseInCzech;
	Categories cat;
	int position;
	String category;

	// Getting around NO Strings in switch/case statement
	public enum Categories {
		ToDo, Alphabet, Numbers, Tobe, Phrases, Colors, Traveling, Greetings, Calendar, Emergencies, Family, Hotel, Bank, Prague, Time, Countries, Shopping, Food, Restaurant
	}

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	//	setScreenOrientation();
		setContentView(R.layout.activity_category);

		Intent i = getIntent();

		category = i.getStringExtra(MainActivity.CATEGORY_NAME);

		cat = Categories.valueOf(category);
		selectedPhraseEN = (TextView) findViewById(R.id.selected_from_list_EN);
		selectedPhraseCSbig = (TextView) findViewById(R.id.translation_big);
		selectedPhraseCS1 = (TextView) findViewById(R.id.translation_1);
		selectedPhraseCS2 = (TextView) findViewById(R.id.translation_2);
		selectedPhraseCS3 = (TextView) findViewById(R.id.translation_3);

		itemsEN = ChooseArrayInEN(category);
		itemsCSbig = ChooseArrayInCSbig(category);
		itemsCS1 = ChooseArrayInCS1(category);
		itemsCS2 = ChooseArrayInCS2(category);
		itemsCS3 = ChooseArrayInCS3(category);
		// itemsAudio = ChooseArrayAudio(category);

		// Display selected list view via array adapter
		adapter = new ArrayAdapterCzech(this, 0, itemsEN);
		adapter.setNotifyOnChange(true);
		ListView lv = (ListView) findViewById(R.id.list);
		lv.setItemsCanFocus(true);
		lv.setAdapter(adapter);
		setFirstENseleted(category);

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View view, int pos,
					long id) {
				releaseMediaPlayer();
				position = pos;
				adapter.setSelectedPosition(position);

				// Display 3 rows of translation in Czech
				selectedPhraseEN.setText(itemsEN[position]);
				selectedPhraseCSbig.setText(itemsCSbig[position]);
				selectedPhraseCS1.setText(itemsCS1[position]);
				selectedPhraseCS2.setText(itemsCS2[position]);
				selectedPhraseCS3.setText(itemsCS3[position]);
				// selectedAudio = itemsAudio[position];

				// Make CS1 visible if not empty string
				if (itemsCS1[position].equals("")) {
					selectedPhraseCS1.setVisibility(View.GONE);
					selectedPhraseCSbig.setGravity(Gravity.CENTER);
				} else {
					selectedPhraseCS1.setVisibility(View.VISIBLE);
					selectedPhraseCSbig.setGravity(Gravity.RIGHT);
					selectedPhraseCS1.setGravity(Gravity.LEFT);
				}
			}
		});
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
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	// On play button in list
	public void onReplayButtonClick(View v) {
		// final RelativeLayout rlListRow = (RelativeLayout) v.getParent();
		// RelativeLayout childRlRow = (RelativeLayout)
		// rlListRow.findViewById(R.id.rl_row);
		// ImageView childArrow = (ImageView) rlListRow.getChildAt(0);
		// View childDivider = (View) rlListRow.getChildAt(1);
		// TextView childLabel = (TextView) rlListRow.getChildAt(2);
		// ImageButton childButton = (ImageButton) rlListRow.getChildAt(3);
		playPhraseInCzech();
	}

	// on linear layout ll_mid clicked
	public void onTranslationClick(View v) {
		playPhraseInCzech();
	}

	// Initiating values when page is first time open
	private void setFirstENseleted(String cat) {

		String[] translation_en = ChooseArrayInEN(cat);
		String[] translation_big = ChooseArrayInCSbig(cat);
		String[] translation_1 = ChooseArrayInCS1(cat);
		String[] translation_2 = ChooseArrayInCS2(cat);
		String[] translation_3 = ChooseArrayInCS3(cat);

		selectedPhraseEN.setText(translation_en[0] + ":");
		selectedPhraseCSbig.setText(translation_big[0]);
		selectedPhraseCS1.setText(translation_1[0]);
		selectedPhraseCS2.setText(translation_2[0]);
		selectedPhraseCS3.setText(translation_3[0]);

		// Make CS1[0] visible if not empty string
		if (translation_1[0].equals("")) {
			selectedPhraseCS1.setVisibility(View.GONE);
			selectedPhraseCSbig.setGravity(Gravity.CENTER);
		} else {
			selectedPhraseCS1.setVisibility(View.VISIBLE);
			selectedPhraseCSbig.setGravity(Gravity.RIGHT);
			selectedPhraseCS1.setGravity(Gravity.LEFT);
		}
	}

	// Chooses an array depending on category button clicked in main
	private String[] ChooseArrayInEN(String category) {

		String[] dummyENCS = getResources().getStringArray(R.array.array_dummy);
		String[] alphabetEN = getResources().getStringArray(
				R.array.array_alphabet_cs_big);
		String[] numbersEN = getResources().getStringArray(
				R.array.array_numbers_en);
		String[] phrasesEN = getResources().getStringArray(
				R.array.array_phrases_en);
		String[] colorsEN = getResources().getStringArray(
				R.array.array_colors_en);
		String[] travelEN = getResources().getStringArray(
				R.array.array_travel_en);
		String[] greetingsEN = getResources().getStringArray(
				R.array.array_greetings_en);
		String[] daysmonthsEN = getResources().getStringArray(
				R.array.array_calendar_en);
		String[] emergenciesEN = getResources().getStringArray(
				R.array.array_emergencies_en);
		String[] familyEN = getResources().getStringArray(
				R.array.array_family_en);
		String[] atTheBankEN = getResources().getStringArray(
				R.array.array_at_the_bank_en);
		String[] pragueEN = getResources().getStringArray(
				R.array.array_prague_en);
		String[] timeEN = getResources().getStringArray(R.array.array_time_en);
		String[] countriesEN = getResources().getStringArray(
				R.array.array_countries_en);
		String[] foodEN = getResources().getStringArray(R.array.array_food_en);
		String[] restaurantEN = getResources().getStringArray(
				R.array.array_restaurant_en);
		String[] hotelEN = getResources()
				.getStringArray(R.array.array_hotel_en);
		String[] shoppingEN = getResources().getStringArray(
				R.array.array_shopping_en);
		String[] tobeEN = getResources().getStringArray(R.array.array_tobe_en);

		switch (cat) {
		case Alphabet:
			return alphabetEN;
		case Numbers:
			return numbersEN;
		case Phrases:
			return phrasesEN;
		case Colors:
			return colorsEN;
		case Traveling:
			return travelEN;
		case Greetings:
			return greetingsEN;
		case Calendar:
			return daysmonthsEN;
		case Emergencies:
			return emergenciesEN;
		case Family:
			return familyEN;
		case Bank:
			return atTheBankEN;
		case Prague:
			return pragueEN;
		case Time:
			return timeEN;
		case Countries:
			return countriesEN;
		case Food:
			return foodEN;
		case Restaurant:
			return restaurantEN;
		case Hotel:
			return hotelEN;
		case Shopping:
			return shoppingEN;
		case Tobe:
			return tobeEN;
		default:
			return dummyENCS;
		}
	}

	// Chooses an array depending on category button clicked in main
	private String[] ChooseArrayInCSbig(String category) {

		String[] dummyENCS = getResources().getStringArray(R.array.array_dummy);
		String[] alphabetCSbig = getResources().getStringArray(
				R.array.array_alphabet_cs_big);
		String[] numbersCSbig = getResources().getStringArray(
				R.array.array_numbers_cs_big);
		String[] phrasesCSbig = getResources().getStringArray(
				R.array.array_phrases_cs_big);
		String[] colorsCSbig = getResources().getStringArray(
				R.array.array_colors_cs_big);
		String[] travelCSbig = getResources().getStringArray(
				R.array.array_travel_cs_big);
		String[] greetingsCSbig = getResources().getStringArray(
				R.array.array_greetings_cs_big);
		String[] daysmonthsCSbig = getResources().getStringArray(
				R.array.array_calendar_cs_big);
		String[] emergenciesCSbig = getResources().getStringArray(
				R.array.array_emergencies_cs_big);
		String[] familyCSbig = getResources().getStringArray(
				R.array.array_family_cs_big);
		String[] atTheBankCSbig = getResources().getStringArray(
				R.array.array_at_the_bank_cs_big);
		String[] pragueCSbig = getResources().getStringArray(
				R.array.array_prague_cs_big);
		String[] timeCSbig = getResources().getStringArray(
				R.array.array_time_cs_big);
		String[] countriesCSbig = getResources().getStringArray(
				R.array.array_countries_cs_big);
		String[] foodCSbig = getResources().getStringArray(
				R.array.array_food_cs_big);
		String[] restaurantCSbig = getResources().getStringArray(
				R.array.array_restaurant_cs_big);
		String[] hotelCSbig = getResources().getStringArray(
				R.array.array_hotel_cs_big);
		String[] shoppingCSbig = getResources().getStringArray(
				R.array.array_shopping_cs_big);
		String[] tobeCSbig = getResources().getStringArray(
				R.array.array_tobe_cs_big);

		switch (cat) {
		case Alphabet:
			return alphabetCSbig;
		case Numbers:
			return numbersCSbig;
		case Phrases:
			return phrasesCSbig;
		case Colors:
			return colorsCSbig;
		case Traveling:
			return travelCSbig;
		case Greetings:
			return greetingsCSbig;
		case Calendar:
			return daysmonthsCSbig;
		case Emergencies:
			return emergenciesCSbig;
		case Family:
			return familyCSbig;
		case Bank:
			return atTheBankCSbig;
		case Prague:
			return pragueCSbig;
		case Time:
			return timeCSbig;
		case Countries:
			return countriesCSbig;
		case Food:
			return foodCSbig;
		case Restaurant:
			return restaurantCSbig;
		case Hotel:
			return hotelCSbig;
		case Shopping:
			return shoppingCSbig;
		case Tobe:
			return tobeCSbig;
		default:
			return dummyENCS;
		}
	}

	// Chooses an array depending on category button clicked in main
	private String[] ChooseArrayInCS1(String category) {

		String[] dummyENCS = getResources().getStringArray(R.array.array_dummy);
		String[] alphabetCS1 = getResources().getStringArray(
				R.array.array_alphabet_cs_1);
		String[] numbersCS1 = getResources().getStringArray(
				R.array.array_numbers_cs1);
		String[] phrasesCS1 = getResources().getStringArray(
				R.array.array_phrases_cs1);
		String[] colorsCS1 = getResources().getStringArray(
				R.array.array_colors_cs1);
		String[] travelCS1 = getResources().getStringArray(
				R.array.array_travel_cs1);
		String[] greetingsCS1 = getResources().getStringArray(
				R.array.array_greetings_cs1);
		String[] daysmonthsCS1 = getResources().getStringArray(
				R.array.array_calendar_cs1);
		String[] emergenciesCS1 = getResources().getStringArray(
				R.array.array_emergencies_cs1);
		String[] familyCS1 = getResources().getStringArray(
				R.array.array_family_cs1);
		String[] atTheBankCS1 = getResources().getStringArray(
				R.array.array_at_the_bank_cs1);
		String[] pragueCS1 = getResources().getStringArray(
				R.array.array_prague_cs1);
		String[] timeCS1 = getResources()
				.getStringArray(R.array.array_time_cs1);
		String[] countriesCS1 = getResources().getStringArray(
				R.array.array_countries_cs1);
		String[] foodCS1 = getResources()
				.getStringArray(R.array.array_food_cs1);
		String[] restaurantCS1 = getResources().getStringArray(
				R.array.array_restaurant_cs1);
		String[] hotelCS1 = getResources().getStringArray(
				R.array.array_hotel_cs1);
		String[] shoppingCS1 = getResources().getStringArray(
				R.array.array_shopping_cs1);
		String[] tobeCS1 = getResources()
				.getStringArray(R.array.array_tobe_cs1);

		switch (cat) {
		case Alphabet:
			return alphabetCS1;
		case Numbers:
			return numbersCS1;
		case Phrases:
			return phrasesCS1;
		case Colors:
			return colorsCS1;
		case Traveling:
			return travelCS1;
		case Greetings:
			return greetingsCS1;
		case Calendar:
			return daysmonthsCS1;
		case Emergencies:
			return emergenciesCS1;
		case Family:
			return familyCS1;
		case Bank:
			return atTheBankCS1;
		case Prague:
			return pragueCS1;
		case Time:
			return timeCS1;
		case Countries:
			return countriesCS1;
		case Food:
			return foodCS1;
		case Hotel:
			return hotelCS1;
		case Restaurant:
			return restaurantCS1;
		case Shopping:
			return shoppingCS1;
		case Tobe:
			return tobeCS1;
		default:
			return dummyENCS;
		}
	}

	// Chooses an array depending on category button clicked in main
	private String[] ChooseArrayInCS2(String category) {

		String[] dummyENCS = getResources().getStringArray(R.array.array_dummy);
		String[] alphabetCS2 = getResources().getStringArray(
				R.array.array_alphabet_cs_2);
		String[] numbersCS2 = getResources().getStringArray(
				R.array.array_numbers_cs2);
		String[] phrasesCS2 = getResources().getStringArray(
				R.array.array_phrases_cs2);
		String[] colorsCS2 = getResources().getStringArray(
				R.array.array_colors_cs2);
		String[] travelCS2 = getResources().getStringArray(
				R.array.array_travel_cs2);
		String[] greetingsCS2 = getResources().getStringArray(
				R.array.array_greetings_cs2);
		String[] daysmonthsCS2 = getResources().getStringArray(
				R.array.array_calendar_cs2);
		String[] emergenciesCS2 = getResources().getStringArray(
				R.array.array_emergencies_cs2);
		String[] familyCS2 = getResources().getStringArray(
				R.array.array_family_cs2);
		String[] atTheBankCS2 = getResources().getStringArray(
				R.array.array_at_the_bank_cs2);
		String[] pragueCS2 = getResources().getStringArray(
				R.array.array_prague_cs2);
		String[] timeCS2 = getResources()
				.getStringArray(R.array.array_time_cs2);
		String[] countriesCS2 = getResources().getStringArray(
				R.array.array_countries_cs2);
		String[] foodCS2 = getResources()
				.getStringArray(R.array.array_food_cs2);
		String[] restaurantCS2 = getResources().getStringArray(
				R.array.array_restaurant_cs2);
		String[] hotelCS2 = getResources().getStringArray(
				R.array.array_hotel_cs2);
		String[] shoppingCS2 = getResources().getStringArray(
				R.array.array_shopping_cs2);
		String[] tobeCS2 = getResources()
				.getStringArray(R.array.array_tobe_cs2);

		switch (cat) {
		case Alphabet:
			return alphabetCS2;
		case Numbers:
			return numbersCS2;
		case Phrases:
			return phrasesCS2;
		case Colors:
			return colorsCS2;
		case Traveling:
			return travelCS2;
		case Greetings:
			return greetingsCS2;
		case Calendar:
			return daysmonthsCS2;
		case Emergencies:
			return emergenciesCS2;
		case Family:
			return familyCS2;
		case Bank:
			return atTheBankCS2;
		case Prague:
			return pragueCS2;
		case Time:
			return timeCS2;
		case Countries:
			return countriesCS2;
		case Hotel:
			return hotelCS2;
		case Restaurant:
			return restaurantCS2;
		case Food:
			return foodCS2;
		case Shopping:
			return shoppingCS2;
		case Tobe:
			return tobeCS2;
		default:
			return dummyENCS;
		}
	}

	// Chooses an array depending on category button clicked in main
	private String[] ChooseArrayInCS3(String category) {

		String[] dummyENCS = getResources().getStringArray(R.array.array_dummy);
		String[] alphabetCS3 = getResources().getStringArray(
				R.array.array_alphabet_cs_3);
		String[] numbersCS3 = getResources().getStringArray(
				R.array.array_numbers_cs3);
		String[] phrasesCS3 = getResources().getStringArray(
				R.array.array_phrases_cs3);
		String[] colorsCS3 = getResources().getStringArray(
				R.array.array_colors_cs3);
		String[] travelCS3 = getResources().getStringArray(
				R.array.array_travel_cs3);
		String[] greetingsCS3 = getResources().getStringArray(
				R.array.array_greetings_cs3);
		String[] daysmonthsCS3 = getResources().getStringArray(
				R.array.array_calendar_cs3);
		String[] emergenciesCS3 = getResources().getStringArray(
				R.array.array_emergencies_cs3);
		String[] familyCS3 = getResources().getStringArray(
				R.array.array_family_cs3);
		String[] atTheBankCS3 = getResources().getStringArray(
				R.array.array_at_the_bank_cs3);
		String[] pragueCS3 = getResources().getStringArray(
				R.array.array_prague_cs3);
		String[] timeCS3 = getResources()
				.getStringArray(R.array.array_time_cs3);
		String[] countriesCS3 = getResources().getStringArray(
				R.array.array_countries_cs3);
		String[] foodCS3 = getResources()
				.getStringArray(R.array.array_food_cs3);
		String[] restaurantCS3 = getResources().getStringArray(
				R.array.array_restaurant_cs3);
		String[] hotelCS3 = getResources().getStringArray(
				R.array.array_hotel_cs3);
		String[] shoppingCS3 = getResources().getStringArray(
				R.array.array_shopping_cs3);
		String[] tobeCS3 = getResources()
				.getStringArray(R.array.array_tobe_cs3);

		switch (cat) {
		case Alphabet:
			return alphabetCS3;
		case Numbers:
			return numbersCS3;
		case Phrases:
			return phrasesCS3;
		case Colors:
			return colorsCS3;
		case Traveling:
			return travelCS3;
		case Greetings:
			return greetingsCS3;
		case Calendar:
			return daysmonthsCS3;
		case Emergencies:
			return emergenciesCS3;
		case Family:
			return familyCS3;
		case Bank:
			return atTheBankCS3;
		case Prague:
			return pragueCS3;
		case Time:
			return timeCS3;
		case Countries:
			return countriesCS3;
		case Food:
			return foodCS3;
		case Hotel:
			return hotelCS3;
		case Restaurant:
			return restaurantCS3;
		case Shopping:
			return shoppingCS3;
		case Tobe:
			return tobeCS3;
		default:
			return dummyENCS;
		}
	}

	// Chooses an audio file depending on selection
	private int[] ChooseArrayAudio(String category) {
		switch (cat) {
		case Alphabet:
			return new MediaStorage().getArrayAlphabet();
		case Numbers:
			return new MediaStorage().getArrayNumbers();
		case Phrases:
			return new MediaStorage().getArrayPhrases();
		case Colors:
			return new MediaStorage().getArrayColors();
		case Traveling:
			new MediaStorage().getArrayTraveling();
		case Greetings:
			return new MediaStorage().getArrayGreetings();
		case Calendar:
			return new MediaStorage().getArrayCalendar();
		case Emergencies:
			return new MediaStorage().getArrayEmergencies();
		case Family:
			return new MediaStorage().getArrayFamily();
		case Bank:
			return new MediaStorage().getArrayAtTheBank();
		case Prague:
			return new MediaStorage().getArrayPrague();
		case Time:
			return new MediaStorage().getArrayTime();
		case Countries:
			return new MediaStorage().getArrayCountries();
		case Food:
			return new MediaStorage().getArrayFood();
		case Hotel:
			return new MediaStorage().getArrayAtTheHotel();
		case Restaurant:
			return new MediaStorage().getArrayAtRestaurant();
		case Shopping:
			return new MediaStorage().getArrayShopping();
		case Tobe:
			return new MediaStorage().getArrayToBe();
		default:
			return new MediaStorage().getArrayDummy();
		}
	}

	// Play audio file
	private void playPhraseInCzech() {
		if (phraseInCzech != null) {
			phraseInCzech.reset();
		}
		itemsAudio = ChooseArrayAudio(category);
		phraseInCzech = MediaPlayer.create(CategoryActivity.this,
				itemsAudio[position]);
		phraseInCzech.start();
	}

	private void releaseMediaPlayer() {
		if (phraseInCzech != null) {
			phraseInCzech.stop();
			phraseInCzech.release();
			phraseInCzech = null;
		}
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		// Log.d(TAG, "FOCUS = " + hasFocus);
		isInFocus = hasFocus;
	}

	@Override
	public void onStop() {
		super.onStop();
		if (!isInFocus) {
			releaseMediaPlayer();
			// finish();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		releaseMediaPlayer();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
//		setScreenOrientation();
		// setContentView(R.layout.activity_main);
	}
}
