package bev.and.owe;

import android.os.CountDownTimer;
import android.support.v4.internal.view.SupportMenuItem;
import android.widget.TextView;

public class MyCountdownTimer extends CountDownTimer {
    private TextView counterDisplay;
    private final int MILLIS_PER_SECOND = 1000;
    private final int SECONDS_PER_MINUTE = 60;

    public MyCountdownTimer(long millisInFuture, long countDownInterval, TextView counterDisplay) {
        super(millisInFuture, countDownInterval);
        this.counterDisplay = counterDisplay;
        this.counterDisplay.setText("Time Left: " + String.format("%02d", millisInFuture / MILLIS_PER_SECOND));
    }

    @Override
    public void onFinish()
    {
        this.counterDisplay.setText("Done!");
    }

    @Override
    public void onTick(final long millisUntilFinished)
    {
    	this.counterDisplay.setText("Time: " + ((millisUntilFinished / MILLIS_PER_SECOND) / SECONDS_PER_MINUTE) + ":" + String.format("%02d", ((millisUntilFinished / MILLIS_PER_SECOND) % SECONDS_PER_MINUTE)));
    }
}