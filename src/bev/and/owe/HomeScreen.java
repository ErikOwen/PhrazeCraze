package bev.and.owe;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class HomeScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_screen);
		initLayout();
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

}
