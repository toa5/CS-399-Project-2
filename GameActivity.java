package com.example.anagram;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import android.annotation.SuppressLint;
import android.content.ClipDescription;
import android.view.DragEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("NewApi")
public class GameActivity extends AnagramActivity 
{
	private final static Random random = new Random();
	private final ArrayList<AnagramButton> buttons = new ArrayList<AnagramButton>();
	private final static int[][] strings = 
	{
		{R.string.p1, R.string.s1},
		{R.string.p2, R.string.s2},
		{R.string.p3, R.string.s3},
		{R.string.p4, R.string.s4},
		{R.string.p5, R.string.s5},
		{R.string.p6, R.string.s6},
		{R.string.p7, R.string.s7},
		{R.string.p8, R.string.s8},
		{R.string.p9, R.string.s9},
		{R.string.p10, R.string.s10},
		{R.string.p11, R.string.s11},
		{R.string.p12, R.string.s12},
		{R.string.p13, R.string.s13},
		{R.string.p14, R.string.s14},
	};
	private String word, solution;
	
	public GameActivity() 
	{	super(R.layout.activity_game);}
	
	@Override
	public void onCreate()
	{
		setRandomWord();
		
		LinearLayout layout = (LinearLayout) this.findViewById(R.id.wordLayout);
		layout.setOnDragListener(new DragListener());
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
	
	void setRandomWord()
	{
		int wordIndex = random.nextInt(strings.length);
		word = getString(strings[wordIndex][0]);
		word = word.replaceAll("\\s", "");
		solution = getString(strings[wordIndex][1]);
		solution = solution.replaceAll("\\s", "");
	}
	
	void checkSolution()
	{
		StringBuilder str = new StringBuilder(buttons.size());
		for(Button button : buttons)
			str.append(button.getText());
		
		//anagram solved
		if(solution.equalsIgnoreCase(str.toString()))
			endGame();
	}
	
	void endGame()
	{
		LinearLayout layout = (LinearLayout) this.findViewById(R.id.wordLayout);
		layout.setOnDragListener(null);
		
		for(Button button : buttons)
			button.setOnTouchListener(null);
		
		TextView text = (TextView) this.findViewById(R.id.gameText);
		text.setText(getString(R.string.successMessage));
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
					checkSolution();
					return true;
				default:
					return false;
			}
		}
	}
}
