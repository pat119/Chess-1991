package com.example.myapplication.leaderboard;

import androidx.annotation.NonNull;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.recyclerview.widget.RecyclerView.Adapter;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeaderboardActivity extends AppCompatActivity {

    private View decorView;

    private boolean pressed;

    private int wave;

    int maxWave;
    private int difficulty;
    private int dataType;
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


        difficulty = 1;
        dataType = 1;
        pressed = false;

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


        leaderboardEntries = new ArrayList<>();


        listView = findViewById(R.id.listViewLeaderboard);
//        listView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LeaderboardAdapter(this, leaderboardEntries);
        listView.setAdapter(adapter);
        show();




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
                difficulty = 1;
                wave = 1;
                waveText.setText("" + wave);
                maxWave = 1 + listView.getAdapter().getCount() / 5;
                listView.smoothScrollToPosition(0);
                leftButton.setBackgroundResource(R.drawable.left_fade);
                leftButton.setClickable(false);
                if (wave < maxWave) {
                    rightButton.setBackgroundResource(R.drawable.right_purple);
                    rightButton.setClickable(true);
                }
                show();
            }
        });


        mediumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                easyButton.setBackgroundColor(getResources().getColor(R.color.white));
                mediumButton.setBackgroundColor(getResources().getColor(R.color.purple));
                hardButton.setBackgroundColor(getResources().getColor(R.color.white));
                difficulty = 2;
                wave = 1;
                waveText.setText("" + wave);
                maxWave = 1 + listView.getAdapter().getCount() / 5;
                listView.smoothScrollToPosition(0);
                leftButton.setBackgroundResource(R.drawable.left_fade);
                leftButton.setClickable(false);
                if (wave < maxWave) {
                    rightButton.setBackgroundResource(R.drawable.right_purple);
                    rightButton.setClickable(true);
                }
                show();
            }
        });

        hardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                easyButton.setBackgroundColor(getResources().getColor(R.color.white));
                mediumButton.setBackgroundColor(getResources().getColor(R.color.white));
                hardButton.setBackgroundColor(getResources().getColor(R.color.purple));
                difficulty = 3;
                wave = 1;
                waveText.setText("" + wave);
                maxWave = 1 + listView.getAdapter().getCount() / 5;
                listView.smoothScrollToPosition(0);
                leftButton.setBackgroundResource(R.drawable.left_fade);
                leftButton.setClickable(false);
                if (wave < maxWave) {
                    rightButton.setBackgroundResource(R.drawable.right_purple);
                    rightButton.setClickable(true);
                }
                show();
            }
        });

        pointsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pointsButton.setBackgroundColor(getResources().getColor(R.color.purple));
                wavesButton.setBackgroundColor(getResources().getColor(R.color.white));
                dataType = 1;
                wave = 1;
                waveText.setText("" + wave);
                maxWave = 1 + listView.getAdapter().getCount() / 5;
                listView.smoothScrollToPosition(0);
                leftButton.setBackgroundResource(R.drawable.left_fade);
                leftButton.setClickable(false);
                if (wave < maxWave) {
                    rightButton.setBackgroundResource(R.drawable.right_purple);
                    rightButton.setClickable(true);
                }
                show();
            }
        });

        wavesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pointsButton.setBackgroundColor(getResources().getColor(R.color.white));
                wavesButton.setBackgroundColor(getResources().getColor(R.color.purple));
                dataType = 2;
                wave = 1;
                waveText.setText("" + wave);
                maxWave = 1 + listView.getAdapter().getCount() / 5;
                listView.smoothScrollToPosition(0);
                leftButton.setBackgroundResource(R.drawable.left_fade);
                leftButton.setClickable(false);
                if (wave < maxWave) {
                    rightButton.setBackgroundResource(R.drawable.right_purple);
                    rightButton.setClickable(true);
                }
                show();
            }
        });


        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wave == 1) {
                    leftButton.setBackgroundResource(R.drawable.left_purple);
                    leftButton.setClickable(true);
                }
                if (wave == maxWave) {
                    rightButton.setBackgroundResource(R.drawable.right_fade);
                    rightButton.setClickable(false);
                }

                wave++;
                listView.smoothScrollToPosition(5 * (wave - 1));
                waveText.setText("" + wave);
            }
        });



        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wave--;
                listView.smoothScrollToPosition(5 * (wave - 1));
                waveText.setText("" + wave);
                if (wave == 1) {
                    leftButton.setBackgroundResource(R.drawable.left_fade);
                    leftButton.setClickable(false);
                }
                if (wave < maxWave) {
                    rightButton.setBackgroundResource(R.drawable.right_purple);
                    rightButton.setClickable(true);
                }

            }
        });




        show();
        adapter.notifyDataSetChanged();

    }



    private void show() {
        leaderboardEntries.clear();
        leaderboardEntries.addAll(generateLeaderboardEntries(difficulty, dataType)); // Replace this with your actual data
        adapter.notifyDataSetChanged();
    }

    private List<LeaderboardEntry> generateLeaderboardEntries(int difficulty, int data) {
        switch (difficulty) {
            case 1:
                switch (data) {
                    case 1:
                        ArrayList<LeaderboardEntry> reversed1 = (ArrayList<LeaderboardEntry>) easyPoints.clone();
                        Collections.reverse(reversed1);
                        return reversed1;
                    case 2:
                        ArrayList<LeaderboardEntry> reversed2 = (ArrayList<LeaderboardEntry>) easyWaves.clone();
                        Collections.reverse(reversed2);
                        return reversed2;
                    default:
                        break;
                }
                break;
            case 2:
                switch (data) {
                    case 1:
                        ArrayList<LeaderboardEntry> reversed3 = (ArrayList<LeaderboardEntry>) mediumPoints.clone();
                        Collections.reverse(reversed3);
                        return reversed3;
                    case 2:
                        ArrayList<LeaderboardEntry> reversed4 = (ArrayList<LeaderboardEntry>) mediumWaves.clone();
                        Collections.reverse(reversed4);
                        return reversed4;
                    default:
                        break;
                }
                break;
            case 3:
                switch (data) {
                    case 1:
                        ArrayList<LeaderboardEntry> reversed5 = (ArrayList<LeaderboardEntry>) hardPoints.clone();
                        Collections.reverse(reversed5);
                        return reversed5;
                    case 2:
                        ArrayList<LeaderboardEntry> reversed6 = (ArrayList<LeaderboardEntry>) hardWaves.clone();
                        Collections.reverse(reversed6);
                        return reversed6;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
        return null;
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

        Query query = base.orderByChild("score");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                Iterable<DataSnapshot> entries = snapshot.getChildren();
                for (DataSnapshot entry : entries) {
                    list.add(entry.getValue(LeaderboardEntry.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("DB", "Failed to read value.", error.toException());
            }
        });

        /*
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

         */
    }


}