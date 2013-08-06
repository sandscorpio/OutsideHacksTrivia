package com.hack.outsidelandstrivia;

import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

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
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.check_for_bundles);
		
		imgDay = (ImageView) findViewById(R.id.imgDay);
		imgLock = (ImageView) findViewById(R.id.imgLock);
		btnEasy = (ImageView) findViewById(R.id.btnEasy);
		btnTough = (ImageView) findViewById(R.id.btnTough);
		btnSuperFan = (ImageView) findViewById(R.id.btnSuperFan);
		
		//TODO: set day image

		imgLock.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		
		btnEasy.setOnClickListener(playClicked);
		btnTough.setOnClickListener(playClicked);
		btnSuperFan.setOnClickListener(playClicked);
	
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Bundles");
		query.findInBackground(new FindCallback<ParseObject>() {
		    public void done(List<ParseObject> bundles, ParseException e) {
		        if (e == null) {
		            //see if a bundle is available
		            Date now = new Date();
		            for (ParseObject bundle : bundles) {
		            	Date startTime = bundle.getDate("startTime");
		            	Date endTime = bundle.getDate("endTime");
		            	
		            	if (true || now.after(startTime) && now.before(endTime)) {
		            		//start game with this bundle
		            		bundleId = bundle.getObjectId();

		            		//TODO: show easy/tough/superfan buttons
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
	
	private OnClickListener playClicked = new OnClickListener() {
		@Override
		public void onClick(View view) {
			//TODO: set level based on btn clicked
			
    		Intent intentPlayGame = new Intent(CheckForBundles.this, PlayGame.class);
    		intentPlayGame.putExtra("bundleId", bundleId);
    		startActivityForResult(intentPlayGame, RESULT_PLAY_GAME);
		}
	};
	
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
