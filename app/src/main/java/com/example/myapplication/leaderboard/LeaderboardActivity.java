package com.example.myapplication.leaderboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.difficulty_menu.DifficultyMenu;
import com.example.myapplication.main_menu.MainActivity;

public class LeaderboardActivity extends AppCompatActivity {

    private View decorView;

    private int wave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard);

        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                decorView.setSystemUiVisibility(hideSystemBars());
            }
        });

        wave = 1;

        Button backButton = findViewById(R.id.leaderboardBackButton);
        Button easyButton = findViewById(R.id.easyButton);
        Button mediumButton = findViewById(R.id.mediumButton);
        Button hardButton = findViewById(R.id.hardButton);
        Button pointsButton = findViewById(R.id.pointsButton);
        Button wavesButton = findViewById(R.id.wavesButton);
        ImageButton leftButton = findViewById(R.id.leftButton);
        ImageButton rightButton = findViewById(R.id.rightButton);
        TextView waveText = findViewById(R.id.pageText);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LeaderboardActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        easyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                easyButton.setBackgroundColor(getResources().getColor(R.color.purple));
                mediumButton.setBackgroundColor(getResources().getColor(R.color.white));
                hardButton.setBackgroundColor(getResources().getColor(R.color.white));
            }
        });

        mediumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                easyButton.setBackgroundColor(getResources().getColor(R.color.white));
                mediumButton.setBackgroundColor(getResources().getColor(R.color.purple));
                hardButton.setBackgroundColor(getResources().getColor(R.color.white));
            }
        });

        hardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                easyButton.setBackgroundColor(getResources().getColor(R.color.white));
                mediumButton.setBackgroundColor(getResources().getColor(R.color.white));
                hardButton.setBackgroundColor(getResources().getColor(R.color.purple));
            }
        });

        pointsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pointsButton.setBackgroundColor(getResources().getColor(R.color.purple));
                wavesButton.setBackgroundColor(getResources().getColor(R.color.white));
            }
        });

        wavesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pointsButton.setBackgroundColor(getResources().getColor(R.color.white));
                wavesButton.setBackgroundColor(getResources().getColor(R.color.purple));
            }
        });

        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wave == 1) {
                    leftButton.setBackgroundResource(R.drawable.left_purple);
                    leftButton.setClickable(true);
                }
                wave++;
                waveText.setText("" + wave);
            }
        });

        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wave--;
                waveText.setText("" + wave);
                if (wave == 1) {
                    leftButton.setBackgroundResource(R.drawable.left_fade);
                    leftButton.setClickable(false);
                }
            }
        });

        leftButton.setClickable(false);
    }

    private int hideSystemBars() {
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    }


}