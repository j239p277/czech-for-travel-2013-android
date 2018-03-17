package com.janpolzer.czech;



import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.janpolzer.czech.R;

/**
 * @author janman74 Custom array adapter for list view in the category activity
 */
public class ArrayAdapterCzech extends ArrayAdapter<Object> {

	private int selectedPos = 0;

	// Constructor
	public ArrayAdapterCzech(Context context, int textViewResourceId,
			String[] objects) {
		super(context, textViewResourceId, objects);
	}

	// Make list view work and pretty
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		LayoutInflater vi;

		// only inflate the view if it's null
		if (v == null) {
			vi = (LayoutInflater) this.getContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.list_row, null);
		}

		// RelativeLayout rlRow = (RelativeLayout) v.findViewById(R.id.rl_row);
		ImageView arrow = (ImageView) v.findViewById(R.id.arrow);
		TextView label = (TextView) v.findViewById(R.id.label);
		View divider = (View) v.findViewById(R.id.divider);
		final ImageButton replayButon = (ImageButton) v
				.findViewById(R.id.replayButton);
		// Delegate for replay button
		View parent2 = v.findViewById(R.id.rl_row);
		parent2.post(new Runnable() {
			public void run() {
				// Post in the parent's message queue to make sure the parent
				// lays out its children before we call getHitRect()
				Rect delegateArea = new Rect();
				ImageButton delegate = replayButon;
				delegate.getHitRect(delegateArea);
				delegateArea.left -= 72;
				delegateArea.right += 48;
				delegateArea.top = 0;
				delegateArea.bottom = 0;
				TouchDelegate expandedArea = new TouchDelegate(delegateArea,
						delegate);
				// Give the delegate to an ancestor of the view we're
				// delegating the area to
				if (View.class.isInstance(delegate.getParent())) {
					((View) delegate.getParent())
							.setTouchDelegate(expandedArea);
				}
			};
		});
		arrow.setVisibility(View.GONE);
		replayButon.setVisibility(View.GONE);
		divider.setVisibility(View.GONE);

		// Change the row look based on selected state

		if (selectedPos == position) {

			arrow.setVisibility(View.VISIBLE);
			divider.setVisibility(View.VISIBLE);
			replayButon.setVisibility(View.VISIBLE);

			label.setTextAppearance(getContext(),
					R.style.style_text_list_selected_bl);
		} else {
			label.setTextAppearance(getContext(), R.style.style_text_list_bl);
		}

		// Make list view black and grey
		if ((position + 1) % 2 == 0) {
			v.setBackgroundColor(Color.parseColor("#292929")); // black
		} else {
			v.setBackgroundColor(Color.parseColor("#474747")); // grey
		}

		label.setText(this.getItem(position).toString());
		return (v);
	}

	// // Get selected row in list view
	// public static int getSelectedPosition() {
	// return selectedPos;
	// }

	// Set selected row in list view
	public void setSelectedPosition(int pos) {
		selectedPos = pos;
		notifyDataSetChanged();
	}

	// @Override
	// public void onClick(View v) {
	// Toast.makeText(getContext(),
	// "replay button pushed \nrow " + getSelectedPosition(),
	// Toast.LENGTH_SHORT).show();
	// // Audio test
	// MediaPlayer playPop;
	// playPop = MediaPlayer.create(getContext(), R.raw.pop);
	// playPop.start();
	// }
	// });
	//
}
