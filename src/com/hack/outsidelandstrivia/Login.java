package com.hack.outsidelandstrivia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;

public class Login extends Activity {
	private static final int RESULT_CHECK_FOR_BUNDLES = RESULT_FIRST_USER;
	
	private Button btnFacebook, btnTwitter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		Intent intent = getIntent();
		btnFacebook = (Button) findViewById (R.id.btnFacebook);
		btnTwitter = (Button) findViewById (R.id.btnTwitter);
		
		btnFacebook.setOnClickListener(btnStageClicked);
		btnTwitter.setOnClickListener(btnStageClicked);
		
		ParseAnalytics.trackAppOpened(getIntent());
		
		ParseInstallation parseInstall = ParseInstallation.getCurrentInstallation();
		parseInstall.saveEventually();
	}
	
	private OnClickListener btnStageClicked = new OnClickListener() {
		public void onClick(View v) {		
			Intent intentPlayGame = new Intent(Login.this, CheckForBundles.class);
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
