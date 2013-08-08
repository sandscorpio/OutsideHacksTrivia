package com.hack.outsidelandstrivia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class GetTwitter extends Activity {
	private static final int RESULT_CHECK_FOR_BUNDLES = RESULT_FIRST_USER;
	
	private EditText txtTwitter;
	private ImageView btnOK;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_twitter);
		
		Intent intent = getIntent();
		txtTwitter = (EditText) findViewById (R.id.txtTwitter);	
		txtTwitter.setImeActionLabel("Dumbass", KeyEvent.KEYCODE_ENTER);
		txtTwitter.setOnEditorActionListener(new TextView.OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				btnStageClicked.onClick(null);
				return true;
			}
			
		});
		
		btnOK = (ImageView) findViewById (R.id.btnOK);	
		btnOK.setOnClickListener(btnStageClicked);
	}
	
	private OnClickListener btnStageClicked = new OnClickListener() {
		public void onClick(View v) {		
			String twitterId = txtTwitter.getText().toString();
			if (twitterId.length() > 0) {
				MainApplication app = (MainApplication) getApplication();
				app.twitterId = twitterId;
			}
			
			Intent intentPlayGame = new Intent(GetTwitter.this, CheckForBundles.class);
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
