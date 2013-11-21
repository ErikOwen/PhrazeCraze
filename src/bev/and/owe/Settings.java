package bev.and.owe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Settings extends Activity {
	
	private Button instructionsButton;
	private Button suggestPhrazeButton;
	private Button buyMorePhrazeButton;

	

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
		setContentView(R.layout.settings);
		instructionsButton = (Button) findViewById(R.id.instructionsButton);
		suggestPhrazeButton = (Button) findViewById(R.id.suggestPhrazeButton);
		buyMorePhrazeButton = (Button) findViewById(R.id.buyPhrazeButton);
		
		

	}
	

}
