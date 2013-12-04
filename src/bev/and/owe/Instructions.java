package bev.and.owe;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.Window;
import android.widget.TextView;

public class Instructions extends Activity {

	private TextView instructionsBanner;
	private TextView instructionsBody;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initLayout();
	}
	
	private void initLayout() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.instructions);
		
		this.instructionsBanner = (TextView) findViewById(R.id.instructionsTextHeading);
		this.instructionsBody = (TextView) findViewById(R.id.instructionsTextBody);
		
		Typeface font  = Typeface.createFromAsset(getAssets(), "Dimbo.ttf");
		this.instructionsBanner.setTypeface(font);
		this.instructionsBody.setTypeface(font);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.instructions, menu);
		return true;
	}

}
