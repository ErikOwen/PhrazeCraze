package bev.and.owe;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import bev.and.owe.*;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.games.GamesClient;
import com.google.android.gms.plus.PlusClient;
import com.google.android.gms.appstate.*;
import com.google.example.games.basegameutils.BaseGameActivity;


public class HomeScreen extends BaseGameActivity implements GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener{

	protected ImageButton timedPlay_button;
	protected ImageButton freePlay_button;
	protected ImageButton highScores_button;
	protected ImageButton settings_button;
	
	protected GamesClient gameClient;
	protected Button pointButton;
	protected TextView pointValue;
	protected TextView homeScreenTitle;
	
	protected ImageButton signInOutButton;
	protected ImageButton signOutButton;
	protected int REQUEST_LEADERBOARD = 20;
	protected boolean isSignedIn;

	
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		
		initLayout();
		initOnClickListeners();
		initAddLeaderboard();
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if (isSignedIn() == true) {
        	signInOutButton.setImageDrawable(getResources().getDrawable(R.drawable.sign_out_selector));
        }
        else {
        	signInOutButton.setImageDrawable(getResources().getDrawable(R.drawable.sign_in_selector));
        }
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		
		return true;
	}
	
	protected void initLayout() {
		
		setContentView(R.layout.home_screen);
		
		this.timedPlay_button = (ImageButton) findViewById(R.id.timed_play_button);
		this.freePlay_button = (ImageButton) findViewById(R.id.free_play_button);
		this.highScores_button = (ImageButton) findViewById(R.id.high_scores_button);
		this.settings_button = (ImageButton) findViewById(R.id.settings_button);
		this.signInOutButton = (ImageButton) findViewById(R.id.signInOutButton);
		this.homeScreenTitle = (TextView) findViewById(R.id.homeScreenTitle);
		
		Typeface font  = Typeface.createFromAsset(getAssets(), "Dimbo.ttf");
		this.homeScreenTitle.setTypeface(font);
	}
	
	protected void initOnClickListeners(){
		this.timedPlay_button.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Intent startTimedPlayActivity = new Intent(HomeScreen.this, PreTimedPlay.class);
				startActivity(startTimedPlayActivity);
			}
		});
		this.freePlay_button.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Intent startFreePlayActivity = new Intent(HomeScreen.this, FreePlay.class);
				startActivity(startFreePlayActivity);
			}
		});
		
		this.settings_button.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Intent settingsScreen = new Intent(HomeScreen.this, Settings.class);
				startActivity(settingsScreen);
			}
		});
		
		this.highScores_button.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				if (isSignedIn()) {
					Intent highScoresIntent = new Intent(HomeScreen.this, HighScores.class);
					startActivity(highScoresIntent);
				}
				else {
					Toast.makeText(getBaseContext(), "You must sign in to view high scores.", Toast.LENGTH_LONG).show();
				}
			}
		});
		
		
	}
	
	protected void initAddLeaderboard() {
		this.gameClient = new GamesClient.Builder(getBaseContext(), this, this).create();
		
		if (isSignedIn()) {
			gameClient.connect();
			this.signInOutButton.setImageDrawable(getResources().getDrawable(R.drawable.sign_out_selector));
		}
		else {
			this.signInOutButton.setImageDrawable(getResources().getDrawable(R.drawable.sign_in_selector));
		}
		
		this.signInOutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isSignedIn() == true) {
                	signOut();
                	Toast.makeText(getBaseContext(), "Signed out of Google Play", Toast.LENGTH_SHORT).show();
                	signInOutButton.setImageDrawable(getResources().getDrawable(R.drawable.sign_in_selector));
                }
                else {
                	 beginUserInitiatedSignIn();
                }
            }
        });
	}
	
	@Override
	public void onSignInFailed() {
		Toast.makeText(this, "Google Play sign in failed", Toast.LENGTH_LONG).show();
		this.signInOutButton.setImageDrawable(getResources().getDrawable(R.drawable.sign_in_selector));
		
	}

	@Override
	public void onSignInSucceeded() {
		if (!gameClient.isConnected()) {
		   gameClient.connect();
		}
		this.signInOutButton.setImageDrawable(getResources().getDrawable(R.drawable.sign_out_selector));
		
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		Toast.makeText(this, "Unable to connect to Google Play Leaderbaords - connection failed.", Toast.LENGTH_LONG).show();
		
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		//Toast.makeText(this, "Able to connect to Google Play Leaderboards", Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public void onDisconnected() {
		Toast.makeText(this, "Disconnected from Google Play Leaderboards", Toast.LENGTH_SHORT).show();
		
	}

}
