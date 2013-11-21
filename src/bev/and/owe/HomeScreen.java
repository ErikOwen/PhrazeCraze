package bev.and.owe;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
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
	
	protected Button signInButton;
	protected Button signOutButton;
	protected int REQUEST_LEADERBOARD = 20;

	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initLayout();
		initOnClickListeners();
		initAddLeaderboard();
		
		gameClient = new GamesClient.Builder(getBaseContext(), this, this).create();
		if (isSignedIn()) {
			gameClient.connect();
		}
		else {
			Toast.makeText(this, "Please sign in", Toast.LENGTH_SHORT).show();
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
		
		//this.timedPlay_button.setBackgroundResource(R.drawable.timed_play_button);
		
		//this.pointButton = (Button) findViewById(R.id.pointButton);
		//this.pointValue = (TextView) findViewById(R.id.pointValue);
		this.signInButton = (Button) findViewById(R.id.signInButton);
		this.signOutButton = (Button) findViewById(R.id.signOutButton);
	}
	
	protected void initOnClickListeners(){
		this.timedPlay_button.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				timedPlay_button.setImageResource(R.drawable.timed_play_button_pushed);
				Intent startTimedPlayActivity = new Intent(HomeScreen.this, PreTimedPlay.class);
				HomeScreen.this.startActivity(startTimedPlayActivity);
			}
		});
		this.freePlay_button.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				freePlay_button.setImageResource(R.drawable.free_play_button_pushed);
				Intent startFreePlayActivity = new Intent(HomeScreen.this, FreePlay.class);
				HomeScreen.this.startActivity(startFreePlayActivity);
			}
		});
		
		this.settings_button.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				settings_button.setImageResource(R.drawable.settings_button_pushed);
				Intent settingsScreen = new Intent(HomeScreen.this, Settings.class);
				HomeScreen.this.startActivity(settingsScreen);
			}
		});
		
		this.highScores_button.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				highScores_button.setImageResource(R.drawable.settings_button_pushed);
				
				if (isSignedIn()) {
					startActivityForResult(gameClient.getLeaderboardIntent(getString(R.string.LEADERBOARD_ID_1_MIN)), REQUEST_LEADERBOARD);
				}
				else {
					Toast.makeText(getBaseContext(), "You must sign in to view high scores.", Toast.LENGTH_LONG).show();
				}
			}
		});
		
		
	}
	
	protected void initAddLeaderboard() {
		int connectionResult = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		switch (connectionResult) {
		case 0:
			Toast.makeText(this, "Google Play Services: Success", Toast.LENGTH_LONG).show();
			break;
		default:
			Toast.makeText(this, "Error code: " + connectionResult, Toast.LENGTH_LONG).show();
			break;
		}
		/*this.pointButton.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				String pointString = pointValue.getText().toString();
				try {
					Integer points = Integer.parseInt(pointString);
					if (isSignedIn()) {
						//gameClient.submitScore(getString(R.string.LEADERBOARD_ID), points);
						startActivityForResult(gameClient.getLeaderboardIntent(getString(R.string.LEADERBOARD_ID_1_MIN)), REQUEST_LEADERBOARD);
						//startActivityForResult(gameClient.getLeaderboardIntent(getString(R.string.LEADERBOARD_ID)), REQUEST_LEADERBOARD);
					}
					Log.d("POINTS", points.toString());
					
				} catch (Exception e) {
					
				}
			}
		});*/
		
		this.signInButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // start the asynchronous sign in flow
                beginUserInitiatedSignIn();
            }
        });
		
		this.signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	if (isSignedIn()) {
                    signOut();
                    Toast.makeText(getBaseContext(), "Sign out successful", Toast.LENGTH_SHORT).show();
            	}
           
            }
        });
        
	}
	
	/*
	public void setSigninButtonState() {        
        if(isSignedIn()) {
            findViewById(R.id.signOutButton).setVisibility(View.VISIBLE);
            findViewById(R.id.signInButton).setVisibility(View.GONE);
        } else {
            findViewById(R.id.signOutButton).setVisibility(View.GONE);
            findViewById(R.id.signInButton).setVisibility(View.VISIBLE);          
        }           
    }
    */

	
	@Override
	public void onSignInFailed() {
		// TODO Tell them to sign in again
		
	}

	@Override
	public void onSignInSucceeded() {
		if (!gameClient.isConnected()) {
		   gameClient.connect();
		}
		// TODO Create a new GamesClient
		
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		Toast.makeText(this, "Unable to connect to google play services - connection failed.", Toast.LENGTH_LONG).show();
		
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		Toast.makeText(this, "Able to connect to google play services.", Toast.LENGTH_SHORT).show();
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}

}
