package bev.and.owe;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.games.GamesClient;
import com.google.example.games.basegameutils.BaseGameActivity;

import android.net.Uri;
import android.os.Bundle;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class FreePlay extends BaseGameActivity implements GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener {
	private TextView currentStreakText;
	private TextView phrazeText;
	private ImageButton homeButton;
	private ImageButton skipPhraze;
	private EditText userAnswer;
	private String currentPhraze;
	private String currentAnswer;
	private int currentStreak;
	private Cursor curs;
	private ContentValues cv;
    private String auth = "bev.and.owe.contentprovider";
    private String base = "phraze_table";
    private String [] projection;
    private static final int TEN_STREAK_ACHIEVEMENT = 10;
    protected GamesClient gameClient;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		this.currentStreak = 0;	
		this.gameClient = new GamesClient.Builder(getBaseContext(), this, this).create();
		
		getPhrazesFromDB();
		initLayout();
		initOnClickListeners();
		
	}

	private void getPhrazesFromDB() {
		this.cv = new ContentValues();
		String auth = "bev.and.owe.contentprovider";
		String base = "phraze_table";
		String[] projection = {PhrazeTable.PHRAZE_KEY_ID, PhrazeTable.PHRAZE_KEY_TEXT, PhrazeTable.PHRAZE_KEY_ANSWER, PhrazeTable.PHRAZE_KEY_TIMES_SEEN};
        
		Uri uri = Uri.parse("content://" + auth + "/" + base + "/" + "phrazes/0");
		
		this.curs = getContentResolver().query(uri, projection, null, projection, PhrazeTable.PHRAZE_KEY_TIMES_SEEN);
		
		if (curs.moveToFirst()) {
			this.currentPhraze = this.curs.getString(PhrazeTable.PHRAZE_COL_TEXT);
			this.currentAnswer = this.curs.getString(PhrazeTable.PHRAZE_COL_ANSWER);
			
			/** Update the DB to add one to the "Seen" column **/
			cv.put(PhrazeTable.PHRAZE_KEY_TIMES_SEEN, curs.getInt(PhrazeTable.PHRAZE_COL_TIMES_SEEN) + 1);
			Uri uriSeen = Uri.parse("content://" + auth + "/" + base + "/" + "phrazes/" + curs.getInt(PhrazeTable.PHRAZE_COL_ID));
			getContentResolver().update(uriSeen, cv, null, null);
			cv.clear();
		}
		else {
			Toast.makeText(this, "Something went terribly wrong.", Toast.LENGTH_SHORT).show();
		}
	}
	
	private void initLayout() {
		setContentView(R.layout.free_play);
		
		this.currentStreakText = (TextView) findViewById(R.id.phrazeStreakText);
		this.phrazeText = (TextView) findViewById(R.id.phrazeTextFreePlay);
		this.homeButton = (ImageButton) findViewById(R.id.homeButtonFreePlay);
		this.skipPhraze = (ImageButton) findViewById(R.id.skipButtonFreePlay);
		this.userAnswer = (EditText) findViewById(R.id.answerInputFreePlay);
		
		Typeface font  = Typeface.createFromAsset(getAssets(), "Dimbo.ttf");
		this.phrazeText.setTypeface(font);
		this.currentStreakText.setTypeface(font);
		
		this.phrazeText.setText(this.currentPhraze);
		this.currentStreakText.setText(getResources().getString(R.string.currentStreak) + " " + this.currentStreak);
	}
	
	protected void initOnClickListeners() {
		this.homeButton.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Intent homeScreenActivity = new Intent(FreePlay.this, HomeScreen.class);
				startActivity(homeScreenActivity);
			}
		});
		
		this.userAnswer.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View view, int keyCode, KeyEvent event) {
				if(event.getAction() == KeyEvent.ACTION_DOWN) {
					if (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
						if (StringComparer.stringChecker(userAnswer.getText().toString(), currentAnswer) < 15) {
							currentStreak++;
							
							cv.put(PhrazeTable.PHRAZE_KEY_COMPLETED, 1);
							Uri uri = Uri.parse("content://" + auth + "/" + base + "/" + "phrazes/" + curs.getInt(PhrazeTable.PHRAZE_COL_ID));
							getContentResolver().update(uri, cv, null, null);
							cv.clear();
							
							if (isSignedIn() && currentStreak >= TEN_STREAK_ACHIEVEMENT) {
								gameClient.unlockAchievement(getString(R.string.tenInARowAchievement));
							}
							
							Toast.makeText(getBaseContext(), "Correct!", Toast.LENGTH_SHORT).show();
						}
						else {
							Toast.makeText(getBaseContext(), "Incorrect answer.\n Answer was: " + currentAnswer, Toast.LENGTH_SHORT).show();
							currentStreak = 0;
						}
						currentStreakText.setText(getResources().getString(R.string.currentStreak) + " " + currentStreak);
						
						userAnswer.getText().clear();
						
						if (curs.moveToNext()) {
							currentPhraze = curs.getString(PhrazeTable.PHRAZE_COL_TEXT);
							currentAnswer = curs.getString(PhrazeTable.PHRAZE_COL_ANSWER);
							
							/** Update the DB to add one to the "Seen" column **/
							cv.put(PhrazeTable.PHRAZE_KEY_TIMES_SEEN, curs.getInt(PhrazeTable.PHRAZE_COL_TIMES_SEEN) + 1);
							Uri uriSeen = Uri.parse("content://" + auth + "/" + base + "/" + "phrazes/" + curs.getInt(PhrazeTable.PHRAZE_COL_ID));
							getContentResolver().update(uriSeen, cv, null, null);
							cv.clear();
						}
						else {
							Toast.makeText(getBaseContext(), "Something went terribly wrong.", Toast.LENGTH_SHORT).show();
						}

						phrazeText.setText(currentPhraze);
						
						InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(userAnswer.getWindowToken(), 0);
						
						return true;
					}
				}
				return false;
			}
		});
		
		this.skipPhraze.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Toast.makeText(getBaseContext(), "Answer was: " + currentAnswer, Toast.LENGTH_SHORT).show();
				userAnswer.getText().clear();
				
				if (curs.moveToNext()) {
					currentPhraze = curs.getString(PhrazeTable.PHRAZE_COL_TEXT);
					currentAnswer = curs.getString(PhrazeTable.PHRAZE_COL_ANSWER);
					
					/** Update the DB to add one to the "Seen" column **/
					cv.put(PhrazeTable.PHRAZE_KEY_TIMES_SEEN, curs.getInt(PhrazeTable.PHRAZE_COL_TIMES_SEEN) + 1);
					Uri uri = Uri.parse("content://" + auth + "/" + base + "/" + "phrazes/" + curs.getInt(PhrazeTable.PHRAZE_COL_ID));
					getContentResolver().update(uri, cv, null, null);
					cv.clear();
				}
				else {
					Toast.makeText(getBaseContext(), "Something went terribly wrong.", Toast.LENGTH_SHORT).show();
				}
				phrazeText.setText(currentPhraze);
				
				currentStreak = 0;
				currentStreakText.setText(getResources().getString(R.string.currentStreak) + " " + currentStreak);
				
				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(userAnswer.getWindowToken(), 0);
			}
		});
	}	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.free_play, menu);
		return true;
	}

	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSignInSucceeded() {
		if (!gameClient.isConnected()) {
			   gameClient.connect();
		}
		
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}

}
