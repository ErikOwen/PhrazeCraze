package bev.and.owe;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class TimedPlay extends Activity {

	private TextView phrazeText;
	private ImageButton skipPhraze;
	private ImageButton submitAnswer;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        initLayout();
        
    }

    private void initLayout() {
    	setContentView(R.layout.timed_play);
    	
    	this.phrazeText = (TextView) findViewById(R.id.phrazeText);
    	this.skipPhraze = (ImageButton) findViewById(R.id.skipButton);
    	this.submitAnswer = (ImageButton) findViewById(R.id.submitButton);
    	
		Typeface font  = Typeface.createFromAsset(getAssets(), "Dimbo.ttf");
		this.phrazeText.setTypeface(font);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.timed_play, menu);
        return true;
    }
    
}
