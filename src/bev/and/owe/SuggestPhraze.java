package bev.and.owe;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SuggestPhraze extends Activity {
	
	private Button suggestButton;
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
			    
			    if (sugPhraze.equals(null) && sugAnswer.equals(null)) {
			    	Toast.makeText(getBaseContext(), "You must fill out both fields before submitting your phraze.", Toast.LENGTH_SHORT).show();
			    }
			    else {
			    	Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto","erik@jelo.com", null));
			    	emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Phraze Suggestion");
			    	startActivity(Intent.createChooser(emailIntent, "Send email..."));
			    	emailIntent.putExtra(Intent.EXTRA_TEXT, "Phraze: " + sugPhraze + "\n" + "Answer: " + sugAnswer);
			    }
			}
		});
		
	}

	private void initLayout() {
		setContentView(R.layout.submit_phraze);
		
		suggestButton = (Button) findViewById(R.id.suggestButton);
		phrazeField = (EditText) findViewById(R.id.suggest_phraze);
		answerField = (EditText) findViewById(R.id.suggest_answer);

	}
	

}

