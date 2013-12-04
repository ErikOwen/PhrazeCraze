package bev.and.owe;

import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.support.v4.internal.view.SupportMenuItem;
import android.support.v4.view.MenuItemCompat;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class TimedPlay extends Activity {
	private Menu timedMenu;
	private ImageButton pauseButton;
	private TextView timerDisplay;
	private TextView phrazesCompletedDisplay;
	private TextView phrazeText;
	private ImageButton skipPhraze;
	//private ImageButton submitAnswer;
	private EditText userAnswer;
	private CountDownTimer timer;
	private int phrazesCompleted;
	private int secondsLeft;
	private int initialTimeSelected;
	private int skipsLeft;
	private String currentPhraze;
	private String currentAnswer;
	private final int MILLISECONDS_PER_SECOND = 1000;
	private final int SECONDS_PER_MINUTE = 60;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		initLayout();
		initOnClickListeners();

		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		this.phrazesCompleted = 0;
		Bundle bundle = getIntent().getExtras();
		this.skipsLeft = bundle.getInt("remainingSkips");
		this.secondsLeft = bundle.getInt("secondsLeft");
		this.initialTimeSelected = this.secondsLeft / SECONDS_PER_MINUTE;
		startTimer(this.secondsLeft);

	}

	@Override
	public void onPause() {
		super.onPause();  // Always call the superclass method first
		this.timer.cancel();
	}

	private void initLayout() {
		setContentView(R.layout.timed_play);

		this.phrazeText = (TextView) findViewById(R.id.phrazeText);
		this.skipPhraze = (ImageButton) findViewById(R.id.skipButton);
		//this.submitAnswer = (ImageButton) findViewById(R.id.submitButton);
		this.pauseButton = (ImageButton) findViewById(R.id.pauseButton);
		this.timerDisplay = (TextView) findViewById(R.id.timeLeft);
		this.phrazesCompletedDisplay = (TextView) findViewById(R.id.completedPhrazesText);
		this.userAnswer = (EditText) findViewById(R.id.answerInput);

		Typeface font  = Typeface.createFromAsset(getAssets(), "Dimbo.ttf");
		this.phrazeText.setTypeface(font);

		PhrazePack pack = PhrazesAndAnswers.getRandomPhrazePack();
		this.currentPhraze = pack.getPhraze();
		this.currentAnswer = pack.getAnswer();

		this.phrazeText.setText(this.currentPhraze);
		this.phrazesCompletedDisplay.setText(getResources().getString(R.string.phrazesFinished) + " " + this.phrazesCompleted);
	}

	protected void initOnClickListeners() {
		this.pauseButton.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Intent pauseScreenActivity = new Intent(TimedPlay.this, TimedPlayPaused.class);

				pauseScreenActivity.putExtra("secondsLeft", stringToSeconds((String) timerDisplay.getText()));
				pauseScreenActivity.putExtra("phrazesCompleted", phrazesCompleted);
				pauseScreenActivity.putExtra("remainingSkips", skipsLeft);

				startActivityForResult(pauseScreenActivity, 1);
			}
		});

		/*this.submitAnswer.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				if (StringComparer.stringChecker(userAnswer.getText().toString(), currentAnswer) < 15) {
					phrazesCompleted++;
					phrazesCompletedDisplay.setText(getResources().getString(R.string.phrazesFinished) + " " + phrazesCompleted);
					Toast.makeText(getBaseContext(), "Correct!", Toast.LENGTH_SHORT).show();
				}
				else {
					Toast.makeText(getBaseContext(), "Incorrect answer", Toast.LENGTH_SHORT).show();
				}

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
							phrazesCompleted++;
							phrazesCompletedDisplay.setText(getResources().getString(R.string.phrazesFinished) + " " + phrazesCompleted);
							Toast.makeText(getBaseContext(), "Correct!", Toast.LENGTH_SHORT).show();
						}
						else {
							Toast.makeText(getBaseContext(), "Incorrect answer", Toast.LENGTH_SHORT).show();
						}

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
				if (skipsLeft > 0) {
					userAnswer.getText().clear();
					PhrazePack pack = PhrazesAndAnswers.getRandomPhrazePack();
					currentPhraze = pack.getPhraze();
					currentAnswer = pack.getAnswer();
					phrazeText.setText(currentPhraze);
					skipsLeft--;
				}
				else {
					int numSkipsAllowed = 0;
					String skipPlural = "skips";
					if (initialTimeSelected == 1) {
						numSkipsAllowed = 1;
						skipPlural = "skip";
					}
					else if (initialTimeSelected == 2) {
						numSkipsAllowed = 2;
					}
					else if (initialTimeSelected == 3){
						numSkipsAllowed = 3;
					}
					else {
						Log.w("PhrazeCraze", "ERROR! TimedPlay thinks the initial time selected was not 1, 2, or 3.");
					}
					Toast.makeText(getBaseContext(), "Only " + numSkipsAllowed + " " + skipPlural + " allowed for " + initialTimeSelected + " Minute Timed Play", Toast.LENGTH_SHORT).show();
				}

				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(userAnswer.getWindowToken(), 0);
			}
		});
	}

	private void startTimer(int timerLength) {
		this.timer = new CountDownTimer(timerLength * MILLISECONDS_PER_SECOND, MILLISECONDS_PER_SECOND) {
			@Override
			public void onFinish()
			{
				timerDisplay.setText("Done!");
				Intent gameOverIntent = new Intent(TimedPlay.this, TimedPlayGameOver.class);
				gameOverIntent.putExtra("correctAnswers", phrazesCompleted);
				gameOverIntent.putExtra("timeSelected", initialTimeSelected);
				startActivity(gameOverIntent);
			}

			@Override
			public void onTick(final long millisUntilFinished)
			{
				timerDisplay.setText("Time: " + ((millisUntilFinished / MILLISECONDS_PER_SECOND) / SECONDS_PER_MINUTE) + ":" + String.format("%02d", ((millisUntilFinished / MILLISECONDS_PER_SECOND) % SECONDS_PER_MINUTE)));
			}
		}.start();
		/*this.timer = new MyCountdownTimer(timerLength * MILLISECONDS_PER_SECOND, MILLISECONDS_PER_SECOND, this.timerDisplay);
            this.timer.start();*/
	}

	private int stringToSeconds(String str) {
		int spaceIndex = str.indexOf(' ');
		int colonIndex = str.indexOf(':', spaceIndex);
		String minutesString = str.substring(spaceIndex + 1, colonIndex);
		String secondsString = str.substring(colonIndex + 1, str.length());

		Integer minutes = Integer.parseInt(minutesString);
		Integer seconds = Integer.parseInt(secondsString);

		return (minutes.intValue() * SECONDS_PER_MINUTE) + seconds.intValue();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.timed_play, menu);

		return true;
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1) {
			this.secondsLeft = data.getIntExtra("secondsLeft", 60);
			this.phrazesCompleted = data.getIntExtra("phrazesCompleted", 0);
			this.skipsLeft = data.getIntExtra("remainingSkips", 0);
			
			startTimer(this.secondsLeft);
		}
	}
}