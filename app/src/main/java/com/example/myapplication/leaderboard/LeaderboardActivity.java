package com.example.myapplication.leaderboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.difficulty_menu.DifficultyMenu;
import com.example.myapplication.main_menu.MainActivity;
import androidx.recyclerview.widget.RecyclerView.Adapter;


import java.util.ArrayList;
import java.util.List;

public class LeaderboardActivity extends AppCompatActivity {

    private View decorView;

    private int wave;
    private ListView listView;
    private LeaderboardAdapter adapter;
    private List<LeaderboardEntry> leaderboardEntries;

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
                leaderboardEntries.clear(); // Clear the existing data
                leaderboardEntries.addAll(generateLeaderboardEntries());
                adapter.notifyDataSetChanged();

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
                leaderboardEntries.clear(); // Clear the existing data
                leaderboardEntries.addAll(generateLeaderboardEntries());
                adapter.notifyDataSetChanged();
            }
        });

        leftButton.setClickable(false);

        listView = findViewById(R.id.listViewLeaderboard);
//        listView.setLayoutManager(new LinearLayoutManager(this));
        leaderboardEntries = generateLeaderboardEntries(); // Replace this with your actual data
        adapter = new LeaderboardAdapter(this, leaderboardEntries);
        listView.setAdapter(adapter );
    }

    private List<LeaderboardEntry> generateLeaderboardEntries() {
        // Generate dummy data for the leaderboard
        List<LeaderboardEntry> entries = new ArrayList<>();
        // Add your leaderboard entries here
        entries.add(new LeaderboardEntry("Player 1", 100, (wave - 1) * 5 + 1));
        entries.add(new LeaderboardEntry("Player 2", 90, (wave -1) * 5 + 2));
        entries.add(new LeaderboardEntry("Player 3", 80, (wave -1) * 5 + 3));
        entries.add(new LeaderboardEntry("Player 4", 70, (wave -1) * 5 + 4));
        entries.add(new LeaderboardEntry("Player 5", 100, (wave -1) * 5 + 5));
        // Add more entries as needed
        return entries;
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