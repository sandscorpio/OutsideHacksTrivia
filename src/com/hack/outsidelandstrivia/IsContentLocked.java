package com.hack.outsidelandstrivia;

import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class IsContentLocked extends Activity {
	private static final int RESULT_PLAY_GAME = RESULT_FIRST_USER;
	
	private TextView txtIsContentLocked;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_is_content_locked);
		
		Intent intent = getIntent();
		String stageName = intent.getStringExtra("stageName");
		
		txtIsContentLocked = (TextView) findViewById(R.id.txtIsContentLocked);
	
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Schedule");
		query.whereEqualTo("stageName", stageName);
		query.findInBackground(new FindCallback<ParseObject>() {
		    public void done(List<ParseObject> bands, ParseException e) {
		        if (e == null) {
		            Log.d("score", "Retrieved " + bands.size() + " bands");
		            
		            //see if a band is about to play or has just finished playing
		            Date now = new Date();
		            for (ParseObject band : bands) {
		            	Date startTime = band.getDate("startTime");
		            	Date endTime = band.getDate("endTime");
		            	
		            	long diff = Long.MAX_VALUE;
		            	if (now.before(startTime)) {
		            		//is it less than 10 mins before a show?
		            		diff = startTime.getTime() - now.getTime();
		            	}
		            	else if (now.after(endTime)) {
		            		//is it less than 10 mins after a show?
		            		diff = now.getTime() - endTime.getTime();
		            	}
		            	
		            	if (diff < 10*60*1000) {
		            		//start game
		            		String bandName = band.getString("band");
		            		txtIsContentLocked.setText(getResources().getString(R.string.txtIsContentAvail));
		            		return;
						}
		            }
		            
		            //no shows starting
		            txtIsContentLocked.setText(getResources().getString(R.string.txtIsContentLocked));
		        } 
		        else {
		            Log.d("score", "Error: " + e.getMessage());
		            txtIsContentLocked.setText("Error");
		        }
		    }
		});
	}
	
	public static boolean isInTimeWindow(String stageName) {
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pick_stage, menu);
		return true;
	}

}
