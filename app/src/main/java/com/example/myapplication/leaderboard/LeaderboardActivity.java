package com.example.myapplication.leaderboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.difficulty_menu.DifficultyMenu;
import com.example.myapplication.main_menu.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.recyclerview.widget.RecyclerView.Adapter;


import java.util.ArrayList;
import java.util.List;

public class LeaderboardActivity extends AppCompatActivity {

    private View decorView;

    private int wave;
    private ListView listView;
    private LeaderboardAdapter adapter;
    private List<LeaderboardEntry> leaderboardEntries;
    private DatabaseReference database;

    private ArrayList<LeaderboardEntry>  easyPoints;
    private ArrayList<LeaderboardEntry> easyWaves;
    private ArrayList<LeaderboardEntry> mediumPoints;
    private ArrayList<LeaderboardEntry> mediumWaves;
    private ArrayList<LeaderboardEntry> hardPoints;
    private ArrayList<LeaderboardEntry> hardWaves;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard);

        database = FirebaseDatabase.getInstance().getReference();
        easyPoints = new ArrayList<>();
        easyWaves = new ArrayList<>();
        mediumPoints = new ArrayList<>();
        mediumWaves = new ArrayList<>();
        hardPoints = new ArrayList<>();
        hardWaves = new ArrayList<>();
        initList(easyPoints, getRef(1, 1));
        initList(easyWaves, getRef(1, 2));
        initList(mediumPoints, getRef(2, 1));
        initList(mediumWaves, getRef(2, 2));
        initList(hardPoints,getRef(3, 1));
        initList(hardWaves, getRef(3, 2));


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
        leaderboardEntries = easyWaves; // Replace this with your actual data
        adapter = new LeaderboardAdapter(this, leaderboardEntries);
        listView.setAdapter(adapter );
        leaderboardEntries.clear();
        leaderboardEntries.addAll(hardPoints); // Replace this with your actual data
        adapter.notifyDataSetChanged();
    }

    private List<LeaderboardEntry> generateLeaderboardEntries() {
        return (List<LeaderboardEntry>) hardPoints;
    }

    private int hideSystemBars() {
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    }

    private DatabaseReference getRef(int difficulty, int data) {
        DatabaseReference base = database;
        switch (difficulty) {
            case 1:
                base = base.child("easy");
                break;
            case 2:
                base = base.child("medium");
                break;
            case 3:
                base = base.child("hard");
                break;
            default:
                break;
        }
        switch (data) {
            case 1:
                base = base.child("points");
                break;
            case 2:
                base = base.child("waves");
                break;
            default:
                break;
        }

        return base;
    }

    private void initList(ArrayList<LeaderboardEntry> list, DatabaseReference base) {
        base.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                long count = snapshot.getChildrenCount();
                Log.d("DB", "Children count: " + count);
                Log.d("DB", "Client count: " + snapshot.child("clients").getChildrenCount());

                // need to recreate the mItems list somehow
                // another way is to use a FirebaseRecyclerView - see Sample Database code

                list.clear();
                Iterable<DataSnapshot> entries = snapshot.getChildren();
                for (DataSnapshot entry : entries) {
                    list.add(entry.getValue(LeaderboardEntry.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("DB", "Failed to read value.", error.toException());
            }
        });
    }


}