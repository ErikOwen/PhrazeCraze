package bev.and.owe;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;
import bev.and.owe.*;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.games.GamesClient;
import com.google.android.gms.plus.PlusClient;
import com.google.android.gms.appstate.*;
import com.google.example.games.basegameutils.BaseGameActivity;

public class HomeScreen extends BaseGameActivity {

	protected Button timedPlay_button;
	protected Button freePlay_button;
	protected Button highScores_button;
	protected Button settings_button;
	
	protected GamesClient gameClient;
	protected Button pointButton;
	protected TextView pointValue;
	
	protected Button signInButton;
	protected Button signOutButton;

	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_screen);
		
		initLayout();
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
		
		pointButton = (Button) findViewById(R.id.pointButton);
		pointValue = (TextView) findViewById(R.id.pointValue);
		signInButton = (Button) findViewById(R.id.signInButton);
		signOutButton = (Button) findViewById(R.id.signOutButton);
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
                signOut();
                setSigninButtonState();
            }
        });
        
	}
	
	
	public void setSigninButtonState() {        
        if(isSignedIn()) {
            findViewById(R.id.signOutButton).setVisibility(View.VISIBLE);
            findViewById(R.id.signInButton).setVisibility(View.GONE);
        } else {
            findViewById(R.id.signOutButton).setVisibility(View.GONE);
            findViewById(R.id.signInButton).setVisibility(View.VISIBLE);          
        }           
    }

	
	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSignInSucceeded() {
		// TODO Auto-generated method stub
		
	}

}
