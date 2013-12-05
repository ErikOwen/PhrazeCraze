package bev.and.owe;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.games.GamesClient;
import com.google.example.games.basegameutils.BaseGameActivity;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.util.Log;
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
	private final int FIVE_PHRAZES_COMPLETED = 5;
	private final int TEN_PHRAZES_COMPLETED = 10;
	private final int FIFTEEN_PHRAZES_COMPLETED = 15;
	private final int ONE_HUNDRED_PHRAZES_ACHIEVEMENT = 100;
	private Cursor curs;
    private String auth = "bev.and.owe.contentprovider";
    private String base = "phraze_table";
    private ContentValues cv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);	
		
		Bundle bundle = getIntent().getExtras();
		this.phrazesCompleted = bundle.getInt("correctAnswers");
		this.gameTime = bundle.getInt("timeSelected");
		
		this.gameClient = new GamesClient.Builder(getBaseContext(), this, this).create();
		beginUserInitiatedSignIn();
		
		initLayout();
		
		initOnClickListeners();
		
	}

	private void initLayout() {
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
	
	private void checkForAchievements() {
		Log.w("PhrazeCraze", "Signed into gp leaderboards");
		
		/** Check to see how many phrazes are completed **/
		int numCompleted = 0;
        Uri uri = Uri.parse("content://" + auth + "/" + base + "/" + "phrazes/0");
		String[] projection = {PhrazeTable.PHRAZE_KEY_ID, PhrazeTable.PHRAZE_KEY_TEXT, PhrazeTable.PHRAZE_KEY_ANSWER, PhrazeTable.PHRAZE_KEY_TIMES_SEEN, PhrazeTable.PHRAZE_KEY_COMPLETED};
        String whereClause = PhrazeTable.PHRAZE_KEY_COMPLETED + " = 1";
		curs = getContentResolver().query(uri, projection, whereClause, projection, null);
		numCompleted = curs.getCount();
		Log.d("COMPLETED", "" + numCompleted);
		
		if (numCompleted >= ONE_HUNDRED_PHRAZES_ACHIEVEMENT) {
			this.gameClient.unlockAchievement(getString(R.string.overOneHundredAchievement));
		}

		switch (this.gameTime) {
		case ONE_MIN:
			if (this.phrazesCompleted >= FIVE_PHRAZES_COMPLETED) {
				this.gameClient.unlockAchievement(getString(R.string.oneMinAchievement));
			}
		break;
		case TWO_MIN:
			if (this.phrazesCompleted >= TEN_PHRAZES_COMPLETED) {
				this.gameClient.unlockAchievement(getString(R.string.twoMinAchievement));
			}
		break;
		case THREE_MIN:
			if (this.phrazesCompleted >= FIFTEEN_PHRAZES_COMPLETED) {
				this.gameClient.unlockAchievement(getString(R.string.threeMinAchievement));
			}
		break;
		}
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
		
		checkForAchievements();
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}

}
