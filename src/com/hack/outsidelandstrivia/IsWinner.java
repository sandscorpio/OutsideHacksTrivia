package com.hack.outsidelandstrivia;

import com.parse.ParseObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class IsWinner extends Activity {
	private static final int RESULT_CHECK_FOR_BUNDLES = RESULT_FIRST_USER;
	
	private RelativeLayout lay;
	private Button btnNext;
	private Button btnChallenge;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_winner);
		
		Intent intent = getIntent();
		
		lay = (RelativeLayout) findViewById (R.id.lay);		
		lay.setOnClickListener(finishClicked);
		
		btnNext = (Button) findViewById (R.id.btnNext);		
		btnNext.setOnClickListener(finishClicked);
		
		btnChallenge = (Button) findViewById (R.id.btnChallenge);		
		btnChallenge.setOnClickListener(challengeClicked);
		
		MainApplication app = (MainApplication) getApplication();
		if (app.twitterId != null) {
			//insert a row in OutgoingTweets
			String msg = getResources().getString(R.string.txtIsWinner);
			msg = msg.replace("*0", app.twitterId);
			
			ParseObject gameScore = new ParseObject("OutgoingTweets");
			gameScore.put("tweetText", msg);
			gameScore.put("twitterHandle", app.twitterId);
			gameScore.saveInBackground();
		}
	}
	
	private OnClickListener finishClicked = new OnClickListener() {
		public void onClick(View v) {		
			finish();
		}
	};
	
	private OnClickListener challengeClicked = new OnClickListener() {
		public void onClick(View v) {
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pick_stage, menu);
		return true;
	}

}
