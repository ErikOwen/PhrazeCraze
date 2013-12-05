package bev.and.owe;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class FreePlay extends Activity {
	private TextView currentStreakText;
	private TextView phrazeText;
	private ImageButton homeButton;
	private ImageButton skipPhraze;
	private ImageButton submitAnswer;
	private EditText userAnswer;
	private String currentPhraze;
	private String currentAnswer;
	private int currentStreak;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		this.currentStreak = 0;	
		
		initLayout();
		initOnClickListeners();
		
	}

	private void initLayout() {
		setContentView(R.layout.free_play);
		
		this.currentStreakText = (TextView) findViewById(R.id.phrazeStreakText);
		this.phrazeText = (TextView) findViewById(R.id.phrazeTextFreePlay);
		this.homeButton = (ImageButton) findViewById(R.id.homeButtonFreePlay);
		this.skipPhraze = (ImageButton) findViewById(R.id.skipButtonFreePlay);
		//this.submitAnswer = (ImageButton) findViewById(R.id.submitButtonFreePlay);
		this.userAnswer = (EditText) findViewById(R.id.answerInputFreePlay);
		
		Typeface font  = Typeface.createFromAsset(getAssets(), "Dimbo.ttf");
		this.phrazeText.setTypeface(font);
		this.currentStreakText.setTypeface(font);
		
		PhrazePack pack = PhrazesAndAnswers.getRandomPhrazePack();
		this.currentPhraze = pack.getPhraze();
		this.currentAnswer = pack.getAnswer();
		
		this.phrazeText.setText(this.currentPhraze);
		this.currentStreakText.setText(getResources().getString(R.string.currentStreak) + " " + this.currentStreak);
	}
	
	protected void initOnClickListeners() {
		this.homeButton.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Intent homeScreenActivity = new Intent(FreePlay.this, HomeScreen.class);
				startActivity(homeScreenActivity);
			}
		});
		
		/*this.submitAnswer.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				if (StringComparer.stringChecker(userAnswer.getText().toString(), currentAnswer) < 15) {
					currentStreak++;
					Toast.makeText(getBaseContext(), "Correct!", Toast.LENGTH_SHORT).show();
				}
				else {
					Toast.makeText(getBaseContext(), "Incorrect answer.\n Answer was: " + currentAnswer, Toast.LENGTH_SHORT).show();
					currentStreak = 0;
				}
				currentStreakText.setText(getResources().getString(R.string.currentStreak) + " " + currentStreak);
				
				userAnswer.getText().clear();
				PhrazePack pack = PhrazesAndAnswers.getRandomPhrazePack();
				currentPhraze = pack.getPhraze();
				currentAnswer = pack.getAnswer();
				phrazeText.setText(currentPhraze);
				
				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(userAnswer.getWindowToken(), 0);
			}
		});*/
		
		this.userAnswer.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View view, int keyCode, KeyEvent event) {
				if(event.getAction() == KeyEvent.ACTION_DOWN) {
					if (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
						if (StringComparer.stringChecker(userAnswer.getText().toString(), currentAnswer) < 15) {
							currentStreak++;
							Toast.makeText(getBaseContext(), "Correct!", Toast.LENGTH_SHORT).show();
						}
						else {
							Toast.makeText(getBaseContext(), "Incorrect answer.\n Answer was: " + currentAnswer, Toast.LENGTH_SHORT).show();
							currentStreak = 0;
						}
						currentStreakText.setText(getResources().getString(R.string.currentStreak) + " " + currentStreak);
						
						userAnswer.getText().clear();
						PhrazePack pack = PhrazesAndAnswers.getRandomPhrazePack();
						currentPhraze = pack.getPhraze();
						currentAnswer = pack.getAnswer();
						phrazeText.setText(currentPhraze);
						
						InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(userAnswer.getWindowToken(), 0);
						
						return true;
					}
				}
				return false;
			}
		});
		this.skipPhraze.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Toast.makeText(getBaseContext(), "Answer was: " + currentAnswer, Toast.LENGTH_SHORT).show();
				userAnswer.getText().clear();
				PhrazePack pack = PhrazesAndAnswers.getRandomPhrazePack();
				currentPhraze = pack.getPhraze();
				currentAnswer = pack.getAnswer();
				phrazeText.setText(currentPhraze);
				
				currentStreak = 0;
				currentStreakText.setText(getResources().getString(R.string.currentStreak) + " " + currentStreak);
				
				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(userAnswer.getWindowToken(), 0);
			}
		});
	}	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.free_play, menu);
		return true;
	}

}
