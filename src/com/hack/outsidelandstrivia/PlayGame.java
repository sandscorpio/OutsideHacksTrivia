package com.hack.outsidelandstrivia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class PlayGame extends Activity {
	private static final int RESULT_IS_CORRECT = RESULT_FIRST_USER;
	
	private TextView txtQuestion;
	private Button btnTrue, btnFalse;
	
	private String[] questions = new String[] {
		"This band is from Alabama",
		"Dog's name is Rooky",
		"Lead singer is Jonny Knoxville",
		"Produced by Kevin Spacey",
		"Went platinum this year"
	};
	
	private boolean[] answerKey = new boolean[] {
		true,
		false,
		true,
		false,
		true
	};
	
	private int questionNumber = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play_game);
		
		Intent intent = getIntent();
	
		txtQuestion = (TextView) findViewById (R.id.txtQuestion);
		btnTrue = (Button) findViewById (R.id.btnTrue);
		btnFalse = (Button) findViewById (R.id.btnFalse);
		
		btnTrue.setOnClickListener(btnTrueClicked);
		btnFalse.setOnClickListener(btnFalseClicked);
		
		nextQuestion();
	}
	
	private void nextQuestion() {
		if (questionNumber < questions.length-1) {
			questionNumber++;
			
			String question = questions[questionNumber];
			txtQuestion.setText(question);
		}
		else {
			//quiz finished
			txtQuestion.setText("Finished!");
			btnTrue.setVisibility(View.INVISIBLE);
			btnFalse.setVisibility(View.INVISIBLE);
		}
	}
	
	private OnClickListener btnTrueClicked = new OnClickListener() {
		public void onClick(View v) {	
			boolean answer = answerKey[questionNumber];
			if (answer == true) {
				//correct
				Intent intent = new Intent(PlayGame.this, IsCorrect.class);
	    		intent.putExtra("isCorrect", true);
	    		startActivityForResult(intent, RESULT_IS_CORRECT);
			}
			else {
				//wrong
				Intent intent = new Intent(PlayGame.this, IsCorrect.class);
	    		intent.putExtra("isCorrect", false);
	    		startActivityForResult(intent, RESULT_IS_CORRECT);
			}
		}
	};
	
	private OnClickListener btnFalseClicked = new OnClickListener() {
		public void onClick(View v) {		
			boolean answer = answerKey[questionNumber];
			Intent intent = new Intent(PlayGame.this, IsCorrect.class);
			if (answer == false) {
				//display is_correct
				intent.putExtra("isCorrect", true);
			}
			else {
				//display is_wrong
				intent.putExtra("isCorrect", false);
			}
			startActivityForResult(intent, RESULT_IS_CORRECT);
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pick_stage, menu);
		return true;
	}
	
	  public void onActivityResult(int requestCode, int resultCode, Intent data) {    	
	    	super.onActivityResult(requestCode, resultCode, data);
	    	
	    	switch(requestCode) {
	    	case RESULT_IS_CORRECT:
	    		nextQuestion();
	    		break;
	    	}
	  }

}
