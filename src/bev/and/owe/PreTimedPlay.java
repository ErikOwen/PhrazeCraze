package bev.and.owe;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Typeface;
import android.view.Menu;
import android.widget.TextView;

public class PreTimedPlay extends Activity {

	TextView preTimedPlayBanner;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initLayout();
	}
	
	protected void initLayout() {
		setContentView(R.layout.pre_timed_play);
		
		this.preTimedPlayBanner = (TextView) findViewById(R.id.pre_timedplay_banner);
		
		Typeface font  = Typeface.createFromAsset(getAssets(), "Dimbo.ttf");
		this.preTimedPlayBanner.setTypeface(font);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pre_timed_play_menu, menu);
		return true;
	}

}
