package com.example.anagram;

import java.util.ArrayList;
import java.util.Collections;

import android.annotation.SuppressLint;
import android.content.ClipDescription;
import android.view.DragEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.widget.LinearLayout;

public class GameActivity extends AnagramActivity 
{
	private final ArrayList<AnagramButton> buttons = new ArrayList<AnagramButton>();
	
	public GameActivity() 
	{	super(R.layout.activity_game);}
	
	@SuppressLint("NewApi")
	@Override
	public void onCreate()
	{
		LinearLayout layout = (LinearLayout) this.findViewById(R.id.wordLayout);
		layout.setOnDragListener(new DragListener());
		String word = "Test Word";
		for(int i = 0; i < word.length(); ++i)
		{
			char c = word.charAt(i);
			if(Character.isLetter(c))
			{
				AnagramButton button = new AnagramButton(this, c, i);
				layout.addView(button);
				buttons.add(button);
			}
		}
		layout.setWeightSum(buttons.size());
	}
	
	@SuppressLint("NewApi")
	class DragListener implements OnDragListener
	{
		@Override
		public boolean onDrag(View view, DragEvent event) 
		{
			switch(event.getAction())
			{
				case DragEvent.ACTION_DRAG_STARTED:
					if (!event.getClipDescription().hasMimeType
					(ClipDescription.MIMETYPE_TEXT_PLAIN)) 
					{return false;}
				case DragEvent.ACTION_DRAG_LOCATION:
				case DragEvent.ACTION_DROP:
					((AnagramButton) event.getLocalState()).setX(event.getX());
				case DragEvent.ACTION_DRAG_ENTERED:
				case DragEvent.ACTION_DRAG_EXITED:
					return true;
				case DragEvent.ACTION_DRAG_ENDED:
					Collections.sort(buttons);
					LinearLayout layout = (LinearLayout) view;
					layout.removeAllViews();
					for(AnagramButton button : buttons)
						layout.addView(new AnagramButton(button));
					buttons.clear();
					for(int i = 0, n = layout.getChildCount(); i < n; ++i)
						buttons.add((AnagramButton) layout.getChildAt(i));
					layout.postInvalidate();
					return true;
				default:
					return false;
			}
		}
	}
}
