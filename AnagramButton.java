package com.example.anagram;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;

@SuppressLint({ "NewApi", "ClickableViewAccessibility" })
public class AnagramButton extends Button implements Comparable<AnagramButton>
{	
	public AnagramButton(Context context, char c, int id) 
	{	
		super(context, null, android.R.attr.buttonStyleSmall);
		setId(id);
		setText(Character.toString(c));
		setLayoutParams(new LayoutParams(
			ViewGroup.LayoutParams.WRAP_CONTENT,
			ViewGroup.LayoutParams.WRAP_CONTENT, 1));
		setOnTouchListener(new TouchListener());
	}
	
	/**
	 * Copy constructor
	 * @param button
	 */
	public AnagramButton(AnagramButton button)
	{
		super(button.getContext(), null, android.R.attr.buttonStyleSmall);
		setId(button.getId());
		setText(button.getText());
		setLayoutParams(button.getLayoutParams());
		setOnTouchListener(new TouchListener());
	}

	/**
	 * 
	 * @return returns the x value from {@link View#getLocationOnScreen}
	 */
	public int getAbsoluteX()
	{	
		int [] pos = new int[2];
		this.getLocationOnScreen(pos);
		return pos[0];
	}

	@Override
	public int compareTo(AnagramButton another) 
	{	return Integer.valueOf(getAbsoluteX()).compareTo(another.getAbsoluteX());}
}
