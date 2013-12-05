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
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class HighScores extends BaseGameActivity implements GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener{
	private TextView highScoresBanner;
	private ImageButton oneMinScores;
	private ImageButton twoMinScores;
	private ImageButton threeMinScores;
	private Button viewAchievements;
	private GamesClient gameClient;
	private final int REQUEST_LEADERBOARD = 20;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		
		initLayout();
		initOnClickListeners();
		
		this.gameClient = new GamesClient.Builder(getBaseContext(), this, this).create();
		
		beginUserInitiatedSignIn();

	}

	private void initLayout() {
		setContentView(R.layout.high_scores);
		
		this.highScoresBanner = (TextView) findViewById(R.id.highScoresBanner);
		this.oneMinScores = (ImageButton) findViewById(R.id.oneMinHighScoreButton);
		this.twoMinScores = (ImageButton) findViewById(R.id.twoMinHighScoreButton);
		this.threeMinScores = (ImageButton) findViewById(R.id.threeMinHighScoreButton);
		this.viewAchievements = (Button) findViewById(R.id.achievementsButton);
		
		Typeface font  = Typeface.createFromAsset(getAssets(), "Dimbo.ttf");
		this.highScoresBanner.setTypeface(font);
		this.viewAchievements.setTypeface(font);
	}
	
	private void initOnClickListeners() {
		this.oneMinScores.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				if (isSignedIn()) {
					startActivityForResult(gameClient.getLeaderboardIntent(getString(R.string.LEADERBOARD_ID_1_MIN)), REQUEST_LEADERBOARD);
				}
				else {
					Toast.makeText(getBaseContext(), "Unable to connect to Google Play Leaderboards at this time: not signed in.", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		this.twoMinScores.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				if (isSignedIn()) {
					startActivityForResult(gameClient.getLeaderboardIntent(getString(R.string.LEADERBOARD_ID_2_MIN)), REQUEST_LEADERBOARD);
				}
				else {
					Toast.makeText(getBaseContext(), "Unable to connect to Google Play Leaderboards at this time: not signed in.", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		this.threeMinScores.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				if (isSignedIn()) {
					startActivityForResult(gameClient.getLeaderboardIntent(getString(R.string.LEADERBOARD_ID_3_MIN)), REQUEST_LEADERBOARD);
				}
				else {
					Toast.makeText(getBaseContext(), "Unable to connect to Google Play Leaderboards at this time: not signed in.", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		this.viewAchievements.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				if (isSignedIn()) {
					startActivityForResult(gameClient.getAchievementsIntent(), 0);
				}
				else {
					Toast.makeText(getBaseContext(), "Unable to connect to Google Play Leaderboards at this time: not signed in.", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.high_scores, menu);
		return true;
	}

	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSignInSucceeded() {
		if (!gameClient.isConnected()) {
			   gameClient.connect();
		}
		
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		Toast.makeText(this, "Unable to connect to Google Play Leaderbaords - connection failed.", Toast.LENGTH_LONG).show();
		
	}

	@Override
	public void onConnected(Bundle arg0) {
		//Toast.makeText(this, "Able to connect to Google Play Leaderboards", Toast.LENGTH_SHORT).show();
		initOnClickListeners();
		
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}

}
