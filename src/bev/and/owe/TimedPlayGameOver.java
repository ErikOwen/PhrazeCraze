package bev.and.owe;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.games.GamesClient;
import com.google.example.games.basegameutils.BaseGameActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TimedPlayGameOver extends BaseGameActivity implements GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener {
	private TextView gameOverBanner;
	private TextView completedPhrazesNotification;
	private TextView highScoreNotification;
	private Button newGameButton;
	private Button mainMenuButton;
	private int phrazesCompleted;
	private int gameTime;
	protected GamesClient gameClient;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
				
		Bundle bundle = getIntent().getExtras();
		this.phrazesCompleted = bundle.getInt("correctAnswers");
		this.gameTime = bundle.getInt("timeSelected");

		gameClient = new GamesClient.Builder(getBaseContext(), this, this).create();
		beginUserInitiatedSignIn();
		
		initLayout();
		
		initOnClickListeners();
		
	}

	private void initLayout() {
		setContentView(R.layout.activity_timed_play_game_over);
		
		this.gameOverBanner = (TextView) findViewById(R.id.gameOverText);
		this.completedPhrazesNotification = (TextView) findViewById(R.id.gameOverCompletedPhrazes);
		this.highScoreNotification = (TextView) findViewById(R.id.possibleHighScore);
		this.newGameButton = (Button) findViewById(R.id.playAgainButton);
		this.mainMenuButton = (Button) findViewById(R.id.returnHomeButton);
		
		Typeface font  = Typeface.createFromAsset(getAssets(), "Dimbo.ttf");
		this.gameOverBanner.setTypeface(font);
		this.completedPhrazesNotification.setTypeface(font);
		this.highScoreNotification.setTypeface(font);
		
		checkAndSubmitHighScore();
	}
	
	private void checkAndSubmitHighScore() {
		if (isSignedIn()) {
			gameClient.connect();
			gameClient.submitScore(getString(R.string.LEADERBOARD_ID), this.phrazesCompleted);
		}
		else {
			this.highScoreNotification.setVisibility(View.GONE);
		}
	}
	
	private void initOnClickListeners() {
		this.newGameButton.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Intent newGameActivity = new Intent(TimedPlayGameOver.this, PreTimedPlay.class);
				
				TimedPlayGameOver.this.startActivity(newGameActivity);
			}
		});
		
		this.mainMenuButton.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Intent mainMenuActivity = new Intent(TimedPlayGameOver.this, HomeScreen.class);
				
				TimedPlayGameOver.this.startActivity(mainMenuActivity);
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timed_play_game_over, menu);
		return true;
	}

	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSignInSucceeded() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Successfully connected to Google Play.", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}

}
