package com.example.anagram;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipDescription;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class GameActivity extends AnagramActivity 
{
	final ArrayList<Button> buttons = new ArrayList<Button>();
	LinearLayout layout;
	
	public GameActivity() 
	{	super(R.layout.activity_game);}
	
	@SuppressLint("NewApi")
	@Override
	public void onCreate()
	{
		layout = (LinearLayout) this.findViewById(R.id.wordLayout);
		layout.setOnDragListener(new DragListener());
		String word = "Test Word";
		layout.setWeightSum(word.length());
		for(int i = 0; i < word.length(); ++i)
		{
			char c = word.charAt(i);
			if(Character.isLetter(c))
			{
				Button button = new Button(this, null, android.R.attr.buttonStyleSmall);
				button.setId(i);
				button.setText(Character.toString(word.charAt(i)));
				button.setLayoutParams(new LinearLayout.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT, 1));
				button.setOnTouchListener(new TouchListener());
				layout.addView(button);
				buttons.add(button);
			}
		}
	}
	
	@SuppressLint("NewApi")
	class DragListener implements OnDragListener
	{
		@Override
		public boolean onDrag(View v, DragEvent event) 
		{	
			switch(event.getAction())
			{
				case DragEvent.ACTION_DRAG_LOCATION:
					return true;
				case DragEvent.ACTION_DRAG_STARTED:
					if (!event.getClipDescription().hasMimeType
					(ClipDescription.MIMETYPE_TEXT_PLAIN)) 
					{return false;}
				case DragEvent.ACTION_DRAG_ENTERED:
				case DragEvent.ACTION_DRAG_EXITED:
				case DragEvent.ACTION_DROP:
				case DragEvent.ACTION_DRAG_ENDED:
					v.invalidate();
					return true;
				default:
					return false;
			}
		}
	}
	
	@SuppressLint({ "NewApi", "ClickableViewAccessibility" })
	class TouchListener implements OnTouchListener
	{
		@Override
		public boolean onTouch(View view, MotionEvent event) 
		{
			switch(event.getActionMasked())
			{
				case MotionEvent.ACTION_DOWN:
			        ClipData data = ClipData.newPlainText("", "");
			        DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
			        view.startDrag(data, shadowBuilder, view, 0);
			        view.setVisibility(View.INVISIBLE);
			        return true;
				case MotionEvent.ACTION_UP:
				//case MotionEvent.ACTION_CANCEL:
					view.setVisibility(View.VISIBLE);
					layout.removeAllViews();
					layout.invalidate();
					Collections.sort(buttons, new ButtonComparator());
					for(Button button : buttons)
						layout.addView(button);
					return true;
				default: 
					return false;
			}
		}
	}
	
	class ButtonComparator implements Comparator<Button>
	{
		@SuppressLint("NewApi")
		@Override
		public int compare(Button lhs, Button rhs) 
		{	return Float.compare(lhs.getX(), rhs.getX());}
	}
}
