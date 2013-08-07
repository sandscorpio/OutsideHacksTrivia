package com.hack.outsidelandstrivia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;

public class Start extends Activity {
	private static final int RESULT_CHECK_FOR_BUNDLES = RESULT_FIRST_USER;
	
	private RelativeLayout lay;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
		Intent intent = getIntent();
		
		lay = (RelativeLayout) findViewById (R.id.lay);		
		lay.setOnClickListener(btnStageClicked);
		
		/*ParseAnalytics.trackAppOpened(getIntent());
		
		ParseInstallation parseInstall = ParseInstallation.getCurrentInstallation();
		parseInstall.saveEventually();*/
	}
	
	private OnClickListener btnStageClicked = new OnClickListener() {
		public void onClick(View v) {		
			Intent intentPlayGame = new Intent(Start.this, CheckForBundles.class);
    		startActivityForResult(intentPlayGame, RESULT_CHECK_FOR_BUNDLES);
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pick_stage, menu);
		return true;
	}

}
