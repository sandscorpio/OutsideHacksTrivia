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

public class IsIncorrect extends Activity {
	private static final int RESULT_CHECK_FOR_BUNDLES = RESULT_FIRST_USER;
	
	private RelativeLayout lay;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_is_incorrect);
		
		Intent intent = getIntent();
		
		lay = (RelativeLayout) findViewById (R.id.lay);		
		lay.setOnClickListener(btnStageClicked);
	}
	
	private OnClickListener btnStageClicked = new OnClickListener() {
		public void onClick(View v) {		
			finish();
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pick_stage, menu);
		return true;
	}

}
