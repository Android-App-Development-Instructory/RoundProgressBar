package com.alaminkarno.roundprogressbard;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private ProgressBar circularProgressBar;
    private TextView countdownText;
    private static final long TOTAL_TIME = 24 * 60 * 60 * 1000;
    private static final int MAX_PROGRESS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        circularProgressBar = findViewById(R.id.circularProgressBar);
        countdownText = findViewById(R.id.countdownText);

        circularProgressBar.setMax(MAX_PROGRESS);

        new CountDownTimer(TOTAL_TIME,1000 ) {
            @Override
            public void onTick(long millisUntilFinished) {
                long days = millisUntilFinished / (24 * 60 * 60 * 1000);
                long hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished);
                long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60;
                long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60;

                String timeString = days + " days\n" + String.format("%02d:%02d:%02d", hours, minutes, seconds);
                countdownText.setText(timeString);

                int progress = (int) (millisUntilFinished / (double) TOTAL_TIME * MAX_PROGRESS);
                circularProgressBar.setProgress(progress);
            }
            @Override
            public void onFinish() {
                countdownText.setText(R.string.time_s_up);
                circularProgressBar.setProgress(MAX_PROGRESS);
            }
        }.start();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}