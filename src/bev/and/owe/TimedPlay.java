package bev.and.owe;

import android.os.Bundle;
import android.os.CountDownTimer;
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
	private CountDownTimer timer;
	private final int MILLISECONDS_PER_SECOND = 1000;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        initLayout();
        
        Bundle bundle = getIntent().getExtras();
        int timerSecondsLeft = bundle.getInt("secondsLeft", 120);
        Toast.makeText(this, "Seconds passed in: " + timerSecondsLeft, Toast.LENGTH_LONG).show();
        startTimer(timerSecondsLeft);
        
    }

    private void initLayout() {
    	setContentView(R.layout.timed_play);
    	
    	this.phrazeText = (TextView) findViewById(R.id.phrazeText);
    	this.skipPhraze = (ImageButton) findViewById(R.id.skipButton);
    	this.submitAnswer = (ImageButton) findViewById(R.id.submitButton);
    	
		Typeface font  = Typeface.createFromAsset(getAssets(), "Dimbo.ttf");
		this.phrazeText.setTypeface(font);
    }

    private void startTimer(int timerSecondsLeft) {
    	//this.timer = new CountDownTimer
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.timed_play, menu);
        return true;
    }
    
}
