package bev.and.owe;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class TimedPlayPaused extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timed_play_paused);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timed_play_paused, menu);
		return true;
	}

}
