package bev.and.owe;

import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.support.v4.internal.view.SupportMenuItem;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.internal.view.menu.MenuItemImpl;
import android.support.v7.internal.view.menu.ActionMenuItem;
import android.support.v7.internal.view.SupportMenuInflater;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class TimedPlay extends Activity {

	private Menu timedMenu;
	private ImageButton pauseButton;
	private TextView timerDisplay;
	private TextView phrazeText;
	private ImageButton skipPhraze;
	private ImageButton submitAnswer;
	private CountDownTimer timer;
	private int phrazesCompleted;
	private int secondsLeft;
	private final int MILLISECONDS_PER_SECOND = 1000;
	private final int SECONDS_PER_MINUTE = 60;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        initLayout();
        initOnClickListeners();
        
        this.phrazesCompleted = 0;
        
        Bundle bundle = getIntent().getExtras();
        int timerLength = bundle.getInt("secondsLeft");
        startTimer(timerLength);
        
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
    	this.submitAnswer = (ImageButton) findViewById(R.id.submitButton);
    	this.pauseButton = (ImageButton) findViewById(R.id.pauseButton);
    	this.timerDisplay = (TextView) findViewById(R.id.timeLeft);
		Typeface font  = Typeface.createFromAsset(getAssets(), "Dimbo.ttf");
		this.phrazeText.setTypeface(font);
    }

	protected void initOnClickListeners() {
		this.pauseButton.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Intent pauseScreenActivity = new Intent(TimedPlay.this, TimedPlayPaused.class);
				Toast.makeText(getBaseContext(), "How many seconds left method thinks: " + stringToSeconds((String) timerDisplay.getText()), Toast.LENGTH_SHORT).show();
				
				pauseScreenActivity.putExtra("secondsLeft", stringToSeconds((String) timerDisplay.getText()));
				pauseScreenActivity.putExtra("phrazesCompleted", phrazesCompleted);
				
				TimedPlay.this.startActivity(pauseScreenActivity);
			}
		});
	}
	
    private void startTimer(int timerLength) {
    	this.timer = new MyCountdownTimer(timerLength * MILLISECONDS_PER_SECOND, MILLISECONDS_PER_SECOND, this.timerDisplay);
    	this.timer.start();
    }
    
    private int stringToSeconds(String str) {
    	int spaceIndex = str.indexOf(' ');
    	int colonIndex = str.indexOf(':', spaceIndex);
    	String minutesString = str.substring(spaceIndex + 1, colonIndex);
    	String secondsString = str.substring(colonIndex + 1, str.length());
    	//Toast.makeText(this, "minutesString: " + minutesString, Toast.LENGTH_LONG).show();
    	Integer minutes = Integer.parseInt(minutesString);
    	Integer seconds = Integer.parseInt(secondsString);
    	
    	//Toast.makeText(this, "seconds: " + seconds, Toast.LENGTH_LONG).show();
    	//Toast.makeText(this, "minutes: " + minutes, Toast.LENGTH_LONG).show();
    	
    	return (minutes.intValue() * SECONDS_PER_MINUTE) + seconds.intValue();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.timed_play, menu);
        
        return true;
    }
    
}
