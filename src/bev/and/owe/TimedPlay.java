package bev.and.owe;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class TimedPlay extends Activity {

	private TextView phrazeText;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        initLayout();
        
    }

    private void initLayout() {
    	setContentView(R.layout.timed_play);
    	
    	phrazeText = (TextView) findViewById(R.id.phrazeText);
    	
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
