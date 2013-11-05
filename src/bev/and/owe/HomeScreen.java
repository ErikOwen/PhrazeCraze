package bev.and.owe;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesUtil;

public class HomeScreen extends Activity {

	protected Button timedPlay_button;
	protected Button freePlay_button;
	protected Button highScores_button;
	protected Button settings_button;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_screen);
		
		initLayout();
		initAddJokeListeners();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}
	
	protected void initLayout() {
		setContentView(R.layout.home_screen);
	}
	
	protected void initAddJokeListeners() {
		int connectionResult = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		switch (connectionResult) {
		case 0:
			Toast.makeText(this, "Google Play Services: Success", Toast.LENGTH_LONG).show();
			break;
		default:
			Toast.makeText(this, "Google Play Services: Error Occured", Toast.LENGTH_LONG).show();
			break;
		}
		/*this.highScores_button.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				
			}
		});*/
	}

}
