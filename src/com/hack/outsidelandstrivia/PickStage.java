package com.hack.outsidelandstrivia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class PickStage extends Activity {
	private static final int RESULT_IS_CONTENT_LOCKED = RESULT_FIRST_USER;
	private static final int RESULT_PLAY_GAME = RESULT_FIRST_USER+1;
	
	private Button btnStage1, btnStage2, btnStage3, btnStage4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pick_stage);
		
		btnStage1 = (Button) findViewById (R.id.btnStage1);
		btnStage2 = (Button) findViewById (R.id.btnStage2);
		btnStage3 = (Button) findViewById (R.id.btnStage3);
		btnStage4 = (Button) findViewById (R.id.btnStage4);
		
		btnStage1.setOnClickListener(btnStageClicked);
		btnStage2.setOnClickListener(btnStageClicked);
		btnStage3.setOnClickListener(btnStageClicked);
		btnStage4.setOnClickListener(btnStageClicked);
	}
	
	private OnClickListener btnStageClicked = new OnClickListener() {
		public void onClick(View v) {		
			Button btn = (Button) v;
			String stageName = btn.getText().toString();
			
			//check if content locked			
			if (!IsContentLocked.isInTimeWindow(stageName)) {
	    		Intent intent = new Intent(PickStage.this, IsContentLocked.class);
	    		intent.putExtra("stageName", stageName);
	    		startActivityForResult(intent, RESULT_IS_CONTENT_LOCKED);
			}
			else {
				//start game as there content available
				Intent intentPlayGame = new Intent(PickStage.this, PlayGame.class);
	    		startActivityForResult(intentPlayGame, RESULT_PLAY_GAME);
			}
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pick_stage, menu);
		return true;
	}

}
