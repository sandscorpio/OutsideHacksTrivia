package com.hack.outsidelandstrivia;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class PlayGame extends Activity {
	private static final int RESULT_IS_CORRECT = RESULT_FIRST_USER;
	private static final int RESULT_IS_GAMEOVER = RESULT_FIRST_USER+1;
	
	private TextView txtQuestion;
	private Button btnTrue, btnFalse;
	
	private String bundleId;
	private String level;
	private ArrayList<String> questions = new ArrayList<String>();
	private ArrayList<Boolean> answers = new ArrayList<Boolean>();
	private int questionIdx = -1;
	private boolean isLoser = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play_game);
		
		Intent intent = getIntent();
		bundleId = intent.getStringExtra("bundleId");
		level = intent.getStringExtra("level");
	
		txtQuestion = (TextView) findViewById (R.id.txtQuestion);
		Typeface font = Typeface.createFromAsset(getAssets(), "PermanentMarker.ttf"); 
		txtQuestion.setTypeface(font);
		
		btnTrue = (Button) findViewById (R.id.btnTrue);
		btnFalse = (Button) findViewById (R.id.btnFalse);
		
		btnTrue.setOnClickListener(btnTrueClicked);
		btnFalse.setOnClickListener(btnFalseClicked);
		
		 btnTrue.setEnabled(false);
    	 btnFalse.setEnabled(false);
		
		getBundle(bundleId);
	}
	
	private void getBundle(String bundleId) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Bundles");
		query.getInBackground(bundleId, new GetCallback<ParseObject>() {
			@Override
			public void done(ParseObject bundle, ParseException e) {
				if (e == null) {
					getTrivia(bundle);
				}
			}
		});
	}
	
	private void getTrivia(ParseObject bundle) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("BundleTrivia");
		query.whereEqualTo("bundle", bundle);
		query.whereEqualTo("level", level);
		query.findInBackground(new FindCallback<ParseObject>() {
		    public void done(List<ParseObject> trivia, ParseException e) {
		        if (e == null) {
		        	 for (ParseObject row : trivia) {
		        		 String question = row.getString("question");
		        		 boolean answer = row.getBoolean("answer");
		        		 
		        		 questions.add(question);
		        		 answers.add(answer);
		        	 }
		        	 
		        	 //display first question
		        	 nextQuestion();
		        	 
		        	 btnTrue.setEnabled(true);
		        	 btnFalse.setEnabled(true);
		        }
		        else {
		        	//error
		        }
		    }
		});
	}
	
	private void nextQuestion() {			
		if (questionIdx < questions.size()-1) {
			questionIdx++;
			
			String question = questions.get(questionIdx);
			txtQuestion.setText(question);
		}
		else {
			//game over
			Class c = isLoser ? IsLoser.class : IsWinner.class;
			Intent intent = new Intent(PlayGame.this, c);
			startActivityForResult(intent, RESULT_IS_GAMEOVER);
			finish();
		}	
	}
	
	private OnClickListener btnTrueClicked = new OnClickListener() {
		public void onClick(View v) {	
			boolean answer = answers.get(questionIdx);	
			Class c = answer == true ? IsCorrect.class : IsIncorrect.class;
			Intent intent = new Intent(PlayGame.this, c);
			startActivityForResult(intent, RESULT_IS_CORRECT);
			
			if (!isLoser) {
				if (answer == false) {
					isLoser = true;
				}
			}
		}
	};
	
	private OnClickListener btnFalseClicked = new OnClickListener() {
		public void onClick(View v) {		
			boolean answer = answers.get(questionIdx);
			Class c = answer == false ? IsCorrect.class : IsIncorrect.class;
			Intent intent = new Intent(PlayGame.this, c);
			startActivityForResult(intent, RESULT_IS_CORRECT);
			
			if (!isLoser) {
				if (answer == true) {
					isLoser = true;
				}
			}
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
