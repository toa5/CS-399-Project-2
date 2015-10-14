package com.example.anagram;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import android.annotation.SuppressLint;
import android.content.ClipDescription;
import android.view.DragEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.widget.LinearLayout;

public class GameActivity extends AnagramActivity 
{
	private final ArrayList<AnagramButton> buttons = new ArrayList<AnagramButton>();
    private final ArrayList<String> puzzles = new ArrayList<String>();
    private final ArrayList<String> solutions = new ArrayList<String>();
    private final Random rand = new Random();
	
	public GameActivity() 
	{	super(R.layout.activity_game);}
	
	@SuppressLint("NewApi")
	@Override
	public void onCreate()
	{
		LinearLayout layout = (LinearLayout) this.findViewById(R.id.wordLayout);
		layout.setOnDragListener(new DragListener());
        setArrayLists();
        int index = rand.nextInt(14);
		String word = puzzles.get(index);
        char c;
		for(int i = 0; i < word.length(); ++i)
		{
			c = word.charAt(i);
			if(Character.isLetter(c))
			{
				AnagramButton button = new AnagramButton(this, c, i);
				layout.addView(button);
                button.setPadding(0,0,0,0); // this is important to show the entire letter
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
					for(int i = 0, n = layout.getChildCount(); i < n; ++i) {
                        buttons.add((AnagramButton) layout.getChildAt(i));
                        buttons.get(buttons.size()-1).setPadding(0,0,0,0); // uncover letters
                    }
					layout.postInvalidate();
					return true;
				default:
					return false;
			}
		}
	}

    private void setArrayLists(){
        puzzles.add(0, "saucy tea dot");
        solutions.add(0, "taco tusday");
        puzzles.add(1, "auto hub wits");
        solutions.add(1, "without a bus");
        puzzles.add(2, "nice dong thievery");
        solutions.add(2, "code in everything");
        puzzles.add(3, "banks alert");
        solutions.add(3, "blank stare");
        puzzles.add(4,"abolish tennis gipsy");
        solutions.add(4, "is anything possible");
        puzzles.add(5, "rave crust erie");
        solutions.add(5, "vice treasurer");
        puzzles.add(6, "goth trip");
        solutions.add(6, "top right");
        puzzles.add(7, "oak boo dog");
        solutions.add(7, "a good book");
        puzzles.add(8, "berry bum guy");
        solutions.add(8, "buy my burger");
        puzzles.add(9, "beehive sod tom");
        solutions.add(9, "move the bodies");
        puzzles.add(10, "sassy chosen pill");
        solutions.add(10, "physical lessons");
        puzzles.add(11, "solar dusk");
        solutions.add(11, "dark souls");
        puzzles.add(12, "mad meal slag");
        solutions.add(12, "small damage");
        puzzles.add(13, "booth rouse");
        solutions.add(13,"robot house");
    }
}
