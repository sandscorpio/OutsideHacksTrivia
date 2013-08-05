package com.hack.outsidelandstrivia;

import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

public class IsContentLocked extends Activity {
	private static final int RESULT_PLAY_GAME = RESULT_FIRST_USER;
	
	private String[] stages = new String[] {
		"Lands End",
		"Sutro",
		"Twin Peaks",
		"Panhandle",
		"The Dome by Heineken",
		"The Barbary"
	};
	
	//TODO: this date should be in a JSON or XML file or fetch from server
	private static Date[][] startTimes = new Date[][] {
			{	//new Date(113, 7, 9, 12, 50), 
				new Date(113, 7, 4, 12, 50), //TESTING!
				new Date(113, 7, 9, 2, 10), 
				new Date(113, 7, 9, 3, 30), 
				new Date(113, 7, 9, 5, 00), 
				new Date(113, 7, 9, 7, 10), 
			},
			{	new Date(113, 7, 9, 12, 00), 
				new Date(113, 7, 9, 1, 05), 
				new Date(113, 7, 9, 2, 10), 
				new Date(113, 7, 9, 3, 25), 
				new Date(113, 7, 9, 4, 45),
				new Date(113, 7, 9, 6, 05)
			}
	};
	
	private static Date[][] endTimes = new Date[][] {
			{	new Date(113, 7, 9, 1, 40), 
				new Date(113, 7, 9, 3, 00), 
				new Date(113, 7, 9, 4, 30), 
				new Date(113, 7, 9, 6, 10), 
				new Date(113, 7, 9, 9, 55), 
			},
			{	new Date(113, 7, 9, 12, 40), 
				new Date(113, 7, 9, 1, 45), 
				new Date(113, 7, 9, 2, 55), 
				new Date(113, 7, 9, 4, 15), 
				new Date(113, 7, 9, 5, 35),
				new Date(113, 7, 9, 7, 05)
			}
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_is_content_locked);
		
		Intent intent = getIntent();
		String stageName = intent.getStringExtra("stageName");	
	}
	
	public static boolean isInTimeWindow(String stageName) {
		return true;
	}
	
	public static boolean isInTimeWindow(int stageNumber) {
		Date[] stageStartTimes = startTimes[stageNumber];
		Date[] stageEndTimes = endTimes[stageNumber];
		Date now = new Date();
		
		//are we in a time window where content is available?
		//ie, 10 mins before and after show
		for (Date date : stageStartTimes) {
			if (now.before(date)) {
				long nowMillis = now.getTime();
				long dateMillis = date.getTime();
				long difference = dateMillis - nowMillis;
				if (difference < 10*60*1000) {
					//10 mins before show
					//start game
					return true;
				}
			}
		}
		
		for (Date date : stageEndTimes) {
			if (now.after(date)) {
				long nowMillis = now.getTime();
				long dateMillis = date.getTime();
				long difference = nowMillis - dateMillis;
				if (difference < 10*60*1000) {
					//10 mins after show
					//start game
					return true;
				}
			}
		}
		
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pick_stage, menu);
		return true;
	}

}
