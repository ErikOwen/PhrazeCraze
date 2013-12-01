package bev.and.owe;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class PreTimedPlay extends Activity {

	private TextView preTimedPlayBanner;
	private ImageButton menuButton;
	private ImageButton startButton;
	private CheckBox oneMinCheckBox;
	private CheckBox twoMinCheckBox;
	private CheckBox threeMinCheckBox;
	private final int ONE_MIN = 60;
	private final int TWO_MIN = 120;
	private final int THREE_MIN = 180;
	private int timeSelected;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initLayout();
		initOnClickListeners();
	}
	
	protected void initLayout() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.pre_timed_play);
		
		this.preTimedPlayBanner = (TextView) findViewById(R.id.pre_timedplay_banner);
		this.menuButton = (ImageButton) findViewById(R.id.preTimedPlayMenuButton);
		this.startButton = (ImageButton) findViewById(R.id.preTimedPlayStartButton);
		this.oneMinCheckBox = (CheckBox) findViewById(R.id.checkBox1min);
		this.twoMinCheckBox = (CheckBox) findViewById(R.id.checkBox2min);
		this.threeMinCheckBox = (CheckBox) findViewById(R.id.checkBox3min);
		
		Typeface font  = Typeface.createFromAsset(getAssets(), "Dimbo.ttf");
		this.preTimedPlayBanner.setTypeface(font);
		this.oneMinCheckBox.setTypeface(font);
		this.twoMinCheckBox.setTypeface(font);
		this.threeMinCheckBox.setTypeface(font);
		this.twoMinCheckBox.setChecked(true);
		this.timeSelected = TWO_MIN;
	}

	protected void initOnClickListeners() {
		this.oneMinCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked) {
					twoMinCheckBox.setChecked(false);
					threeMinCheckBox.setChecked(false);
					
					timeSelected = ONE_MIN;
				}
			}
		});
		this.twoMinCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked) {
					oneMinCheckBox.setChecked(false);
					threeMinCheckBox.setChecked(false);
					timeSelected = TWO_MIN;
				}
			}
		});
		this.threeMinCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked) {
					oneMinCheckBox.setChecked(false);
					twoMinCheckBox.setChecked(false);
					timeSelected = THREE_MIN;
				}
			}
		});
		this.menuButton.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Intent returnHomeScreenActivity = new Intent(PreTimedPlay.this, HomeScreen.class);
				startActivity(returnHomeScreenActivity);
			}
		});
		this.startButton.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Intent startTimedPlayActivity = new Intent(PreTimedPlay.this, TimedPlay.class);
				startTimedPlayActivity.putExtra("secondsLeft", timeSelected);
				startActivity(startTimedPlayActivity);
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pre_timed_play_menu, menu);
		return true;
	}

}
