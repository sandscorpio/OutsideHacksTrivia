package com.hack.outsidelandstrivia;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class CheckForBundles extends Activity {
	private static final int RESULT_PLAY_GAME = RESULT_FIRST_USER;
	
	private String bundleId;
	private ImageView imgDay;
	private ImageView imgLock;
	private ImageView btnEasy;
	private ImageView btnTough;
	private ImageView btnSuperFan;
	private ImageView imgHeader;
	private TableLayout tblBtns;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.check_for_bundles);
		
		imgHeader = (ImageView) findViewById(R.id.imgHeader);
		imgDay = (ImageView) findViewById(R.id.imgDay);
		imgLock = (ImageView) findViewById(R.id.imgLock);
		btnEasy = (ImageView) findViewById(R.id.btnEasy);
		btnTough = (ImageView) findViewById(R.id.btnTough);
		btnSuperFan = (ImageView) findViewById(R.id.btnSuperFan);
		tblBtns = (TableLayout) findViewById(R.id.tblBtns);
		
		//set main image based on day of week and time of day
		setImg();
		
		imgLock.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		
		//TESTING!
		imgHeader.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				bundleId = "jlx90PqBMN";
				tblBtns.setVisibility(View.VISIBLE);
        		imgLock.setVisibility(View.GONE);
			}
		});
		
		btnEasy.setOnClickListener(playClicked);
		btnEasy.setTag("easy");
		btnTough.setOnClickListener(playClicked);
		btnTough.setTag("tough");
		btnSuperFan.setOnClickListener(playClicked);
		btnSuperFan.setTag("superfan");
	
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Bundles");
		query.findInBackground(new FindCallback<ParseObject>() {
		    public void done(List<ParseObject> bundles, ParseException e) {
		        if (e == null) {
		            //see if a bundle is available
		            Date now = new Date();
		            for (ParseObject bundle : bundles) {
		            	Date startTime = bundle.getDate("startTime");
		            	Date endTime = bundle.getDate("endTime");
		            	
		            	if (now.after(startTime) && now.before(endTime)) {
		            		//start game with this bundle
		            		bundleId = bundle.getObjectId();

		            		tblBtns.setVisibility(View.VISIBLE);
		            		imgLock.setVisibility(View.GONE);
		            		return;
		            	}
		            }
		            
		            //find earliest bundle
		            Date earliestBundle = null;
		            for (ParseObject bundle : bundles) {
		            	Date startTime = bundle.getDate("startTime");
		            	if (earliestBundle == null || earliestBundle.after(startTime)) {
		            		earliestBundle = startTime;
		            	}
		            }
		            
		            //no bundles available
		            String txtNoBundles = getResources().getString(R.string.txtNoBundles);
		            txtNoBundles = txtNoBundles.replace("*", earliestBundle.toString());
		        } 
		        else {
		        	//Error
		        }
		    }
		});
	}
	
	private void setImg() {
		int imgId = R.drawable.friday1;
		
		//get time window
		//we want to show current bundle if there is an active one
		//if there is no active bundle, show next bundle's image
		int timeWindow = 1;
		int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		if (hour < 15) {
			timeWindow = 1;
		}
		else if (hour < 19) {
			timeWindow = 2;
		}
		else if (hour < 23) {
			timeWindow = 3;
		}
		else {
			//TODO: show next day's first image
		}
		
		//get day
		switch (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
		case Calendar.FRIDAY:
			switch (timeWindow) {
			case 1:
				imgId = R.drawable.friday1;
				break;
			case 2:
				imgId = R.drawable.friday2;
				break;
			case 3:
				imgId = R.drawable.friday3;
				break;
			}
			break;
		case Calendar.SATURDAY:
			switch (timeWindow) {
			case 1:
				imgId = R.drawable.saturday1;
				break;
			case 2:
				imgId = R.drawable.saturday2;
				break;
			case 3:
				imgId = R.drawable.saturday3;
				break;
			}
			break;
		case Calendar.SUNDAY:
			switch (timeWindow) {
			case 1:
				imgId = R.drawable.sunday1;
				break;
			case 2:
				imgId = R.drawable.sunday2;
				break;
			case 3:
				imgId = R.drawable.sunday3;
				break;
			}
			break;
		}
		
		imgDay.setImageDrawable(getResources().getDrawable(imgId));
	}
	
	private OnClickListener playClicked = new OnClickListener() {
		@Override
		public void onClick(View view) {
			ImageView btn = (ImageView) view;
			
    		Intent intentPlayGame = new Intent(CheckForBundles.this, PlayGame.class);
    		intentPlayGame.putExtra("bundleId", bundleId);
    		intentPlayGame.putExtra("level", (String) btn.getTag());
    		startActivityForResult(intentPlayGame, RESULT_PLAY_GAME);
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pick_stage, menu);
		return true;
	}

}
