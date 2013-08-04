package com.hack.outsidelandstrivia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class IsCorrect extends Activity {
	private static final int RESULT_PICK_STAGE = RESULT_FIRST_USER;
	
	private Button btnOK;
	private TextView txtIsCorrect;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_iscorrect);
		
		Intent intent = getIntent();
		boolean isCorrect = intent.getBooleanExtra("isCorrect", false);
		
		txtIsCorrect = (TextView) findViewById (R.id.txtIsCorrect);
		if (isCorrect) {
			txtIsCorrect.setText("Correct!");
		}
		else {
			txtIsCorrect.setText("Wrong!");
		}
		
		btnOK = (Button) findViewById(R.id.btnOK);
		btnOK.setOnClickListener(btnOKClicked);
	}
	
	private OnClickListener btnOKClicked = new OnClickListener() {
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
