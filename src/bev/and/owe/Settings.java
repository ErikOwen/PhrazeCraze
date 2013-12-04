package bev.and.owe;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class Settings extends Activity {
	private TextView settingsTextBanner;
	private ImageButton instructionsButton;
	private ImageButton suggestPhrazeButton;
	private ImageButton buyMorePhrazeButton;

	

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initLayout();
		initOnClickListeners();
	}

	private void initOnClickListeners() {
		this.instructionsButton.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Intent instructionsScreen = new Intent(Settings.this, Instructions.class);
				Settings.this.startActivityForResult(instructionsScreen, 1);
			}
		});
		this.suggestPhrazeButton.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Intent suggestPhrazeScreen = new Intent(Settings.this, SuggestPhraze.class);
				Settings.this.startActivityForResult(suggestPhrazeScreen, 1);
			}
		});
		this.buyMorePhrazeButton.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
			
			}
		});
		
		
	}

	private void initLayout() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.settings);
		this.settingsTextBanner = (TextView) findViewById(R.id.settingsText);
		this.instructionsButton = (ImageButton) findViewById(R.id.instructionsButton);
		this.suggestPhrazeButton = (ImageButton) findViewById(R.id.suggestPhrazeButton);
		this.buyMorePhrazeButton = (ImageButton) findViewById(R.id.buyPhrazeButton);
		
		Typeface font  = Typeface.createFromAsset(getAssets(), "Dimbo.ttf");
		this.settingsTextBanner.setTypeface(font);
	}
	

}
