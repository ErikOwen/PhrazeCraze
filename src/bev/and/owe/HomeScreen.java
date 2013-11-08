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

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.games.GamesClient;
import com.google.android.gms.plus.PlusClient;
import com.google.android.gms.appstate.*;
import com.google.example.games.basegameutils.BaseGameActivity;


public class HomeScreen extends BaseGameActivity {

	protected ImageButton timedPlay_button;
	protected ImageButton freePlay_button;
	protected ImageButton highScores_button;
	protected ImageButton settings_button;
	
	protected GamesClient gameClient;
	protected Button pointButton;
	protected TextView pointValue;
	
	protected Button signInButton;
	protected Button signOutButton;

	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initLayout();
		initOnClickListeners();
		initAddLeaderboard();
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
		
		this.pointButton = (Button) findViewById(R.id.pointButton);
		this.pointValue = (TextView) findViewById(R.id.pointValue);
		this.signInButton = (Button) findViewById(R.id.signInButton);
		this.signOutButton = (Button) findViewById(R.id.signOutButton);
	}
	
	protected void initOnClickListeners(){
		this.timedPlay_button.setOnTouchListener(new OnClickListener() {
			public void onClick(View view) {
				timedPlay_button.setImageResource(R.drawable.timed_play_button_pushed);
				Intent startTimedPlayActivity = new Intent(HomeScreen.this, PreTimedPlay.class);
				HomeScreen.this.startActivity(startTimedPlayActivity);
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
		this.pointButton.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				String pointString = pointValue.getText().toString();
				try {
					Integer points = Integer.parseInt(pointString);
					Log.d("POINTS", points.toString());
					
				} catch (Exception e) {
					
				}
			}
		});
		
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
		// TODO Create a new GamesClient
		
	}

}
