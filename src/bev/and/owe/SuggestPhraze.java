package bev.and.owe;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class SuggestPhraze extends Activity {
	
	private ImageButton suggestButton;
	private EditText phrazeField;
	private EditText answerField;
	private String sugPhraze;
	private String sugAnswer;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initLayout();
		initOnClickListeners();
	}

	private void initOnClickListeners() {
		this.suggestButton.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				sugPhraze = phrazeField.getText().toString();
			    sugAnswer = answerField.getText().toString();
			    Log.d("SUGGESTEDPHRAZE", "Suggested Phraze: " + sugPhraze + "\nSuggested Answer: " + sugAnswer);
			    
			    if (sugPhraze.equals("") || sugAnswer.equals("")) {
			    	Toast.makeText(getBaseContext(), "You must fill out both fields before submitting your phraze.", Toast.LENGTH_SHORT).show();
			    }
			    else {
			    	Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto","erik@jelo.com", null));
			    	emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Phraze Suggestion");
			    	emailIntent.putExtra(Intent.EXTRA_TEXT, "Phraze: " + sugPhraze + "\n" + "Answer: " + sugAnswer);
			    	startActivity(Intent.createChooser(emailIntent, "Send email..."));
			    }
			}
		});
		
	}

	private void initLayout() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.submit_phraze);
		
		this.suggestButton = (ImageButton) findViewById(R.id.suggestButton);
		this.phrazeField = (EditText) findViewById(R.id.suggest_phraze);
		this.answerField = (EditText) findViewById(R.id.suggest_answer);

	}
	

}

