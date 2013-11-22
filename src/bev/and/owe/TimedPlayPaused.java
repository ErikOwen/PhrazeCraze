package bev.and.owe;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class TimedPlayPaused extends Activity {
	private TextView pauseGameBanner;
	private TextView phrazesCompletedText;
	private TextView timeLeftText;
	private Button resumeButton;
	private Button menuButton;
	private int secondsLeft;
	private int phrazesCompleted;
	private final int SECONDS_PER_MINUTE = 60;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bundle bundle = getIntent().getExtras();
		this.secondsLeft = bundle.getInt("secondsLeft");
		this.phrazesCompleted = bundle.getInt("phrazesCompleted");
		
		initLayout();
		
		initOnClickListeners();
		
		this.phrazesCompletedText.setText("Phrazes Completed: " + this.phrazesCompleted);
		this.timeLeftText.setText("Time Remaining: " + (this.secondsLeft / SECONDS_PER_MINUTE) + ":" +String.format("%02d", this.secondsLeft % SECONDS_PER_MINUTE));
		
	}

	private void initLayout() {
		setContentView(R.layout.timed_play_paused);
		
		this.pauseGameBanner = (TextView) findViewById(R.id.pausedText);
		this.phrazesCompletedText = (TextView) findViewById(R.id.completedPhrazesText);
		this.timeLeftText = (TextView) findViewById(R.id.timeLeftText);
		this.resumeButton = (Button) findViewById(R.id.resumeGameButton);
		this.menuButton = (Button) findViewById(R.id.leaveGameButton);
		
		Typeface font  = Typeface.createFromAsset(getAssets(), "Dimbo.ttf");
		this.pauseGameBanner.setTypeface(font);
		this.phrazesCompletedText.setTypeface(font);
		this.timeLeftText.setTypeface(font);
	}
	
	private void initOnClickListeners() {
		this.resumeButton.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Intent returnToGameIntent = new Intent();
				returnToGameIntent.putExtra("secondsLeft", secondsLeft);
				returnToGameIntent.putExtra("phrazesCompleted", phrazesCompleted);
				setResult(RESULT_OK, returnToGameIntent);        
				finish();
			}
		});
		
		this.menuButton.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Intent returnHomeScreenActivity = new Intent(TimedPlayPaused.this, HomeScreen.class);
				startActivity(returnHomeScreenActivity);
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timed_play_paused, menu);
		return true;
	}
	
    @Override
    public void onBackPressed () {
		Intent returnToGameIntent = new Intent();
		returnToGameIntent.putExtra("secondsLeft", secondsLeft);
		returnToGameIntent.putExtra("phrazesCompleted", phrazesCompleted);
		setResult(RESULT_OK, returnToGameIntent);        
		finish();
    }

}
