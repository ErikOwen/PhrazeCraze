package bev.and.owe;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
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

public class TimedPlayGameOver extends BaseGameActivity implements GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener {
	private TextView gameOverBanner;
	private TextView completedPhrazesNotification;
	private ImageButton newGameButton;
	private ImageButton mainMenuButton;
	private int phrazesCompleted;
	private int gameTime;
	protected GamesClient gameClient;
	private final int ONE_MIN = 1;
	private final int TWO_MIN = 2;
	private final int THREE_MIN = 3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
				
		Bundle bundle = getIntent().getExtras();
		this.phrazesCompleted = bundle.getInt("correctAnswers");
		this.gameTime = bundle.getInt("timeSelected");
		
		this.gameClient = new GamesClient.Builder(getBaseContext(), this, this).create();
		beginUserInitiatedSignIn();
		
		initLayout();
		
		initOnClickListeners();
		
		//checkAndSubmitHighScore();
		
	}

	private void initLayout() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_timed_play_game_over);
		
		this.gameOverBanner = (TextView) findViewById(R.id.gameOverText);
		this.completedPhrazesNotification = (TextView) findViewById(R.id.gameOverCompletedPhrazes);
		this.newGameButton = (ImageButton) findViewById(R.id.playAgainButton);
		this.mainMenuButton = (ImageButton) findViewById(R.id.returnHomeButton);
		
		Typeface font  = Typeface.createFromAsset(getAssets(), "Dimbo.ttf");
		this.gameOverBanner.setTypeface(font);
		this.completedPhrazesNotification.setTypeface(font);
		
		String phrazePlural, minutePlural;
		
		phrazePlural = (this.phrazesCompleted == 1) ? "phraze" : "phrazes";
		minutePlural = (this.gameTime == ONE_MIN) ? "minute" : "minutes";
		
		this.completedPhrazesNotification.setText(getResources().getString(R.string.completedPhrazes) + " " + this.phrazesCompleted + " " + phrazePlural + " in " + this.gameTime + " " + minutePlural + "!");
	}
	
	private void initOnClickListeners() {
		this.newGameButton.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Intent newGameActivity = new Intent(TimedPlayGameOver.this, PreTimedPlay.class);
				
				startActivity(newGameActivity);
			}
		});
		
		this.mainMenuButton.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Intent mainMenuActivity = new Intent(TimedPlayGameOver.this, HomeScreen.class);
				
				startActivity(mainMenuActivity);
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
		//Toast.makeText(this, "sign in failed", Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public void onSignInSucceeded() {
		if (!gameClient.isConnected()) {
			   gameClient.connect();
			   //Toast.makeText(this, "sign in succeeded!", Toast.LENGTH_SHORT).show();
		}
		
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		//Toast.makeText(this, "Could not connect to Google Play.", Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		//Toast.makeText(this, "Successfully connected to Google Play.", Toast.LENGTH_SHORT).show();
		if (this.gameTime == ONE_MIN) {
			//Toast.makeText(this, "2min High score submitted", Toast.LENGTH_LONG).show();
			gameClient.submitScore(getString(R.string.LEADERBOARD_ID_1_MIN), this.phrazesCompleted);
		}
		else if (this.gameTime == TWO_MIN) {
			gameClient.submitScore(getString(R.string.LEADERBOARD_ID_2_MIN), this.phrazesCompleted);
		}
		else if (this.gameTime == THREE_MIN) {
			gameClient.submitScore(getString(R.string.LEADERBOARD_ID_3_MIN), this.phrazesCompleted);
		}
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}

}
