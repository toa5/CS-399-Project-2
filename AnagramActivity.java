package com.example.anagram;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public abstract class AnagramActivity extends Activity 
{
	final int id;
	public AnagramActivity(int id)
	{
		super();
		this.id = id;
	}
	
	@Override
    final protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(id);
        onCreate();
    }
	
	protected void onCreate(){}
	
	public void gotoGame(View view)
    {	startActivity(new Intent(this, GameActivity.class));}

    public void gotoCredits(View view)
    {	startActivity(new Intent(this, CreditsActivity.class));}
    
    public void gotoTutorial(View view)
    {	startActivity(new Intent(this, TutorialActivity.class));}
	
	public void gotoMenu(View view)
	{	finish();}
}
