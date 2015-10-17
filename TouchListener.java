package com.example.anagram;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnTouchListener;

@SuppressLint({ "NewApi", "ClickableViewAccessibility" })
final class TouchListener implements OnTouchListener
{
	@Override
	public boolean onTouch(View view, MotionEvent event) 
	{
		switch(event.getActionMasked())
		{
			case MotionEvent.ACTION_DOWN:
		        view.startDrag(ClipData.newPlainText("", ""), 
		        		new DragShadowBuilder(), view, 0);
		        return true;
			default: 
				return false;
		}
	}
}
