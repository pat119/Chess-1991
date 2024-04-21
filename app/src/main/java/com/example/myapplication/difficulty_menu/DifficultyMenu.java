package com.example.myapplication.difficulty_menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;
import com.example.myapplication.User;
import com.example.myapplication.game_board.GameboardActivity;
import com.example.myapplication.main_menu.MainActivity;
import com.example.myapplication.tutorial.Tutorial;

public class DifficultyMenu extends AppCompatActivity {
    private View decorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty_menu);

        User user = (User) getIntent().getSerializableExtra("profile");

        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                decorView.setSystemUiVisibility(hideSystemBars());
            }
        });

        Button backButton = (Button)findViewById(R.id.difficultyBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button easy = (Button)findViewById(R.id.easyButton);
        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DifficultyMenu.this, GameboardActivity.class);
                intent.putExtra("difficulty", 5);
                intent.putExtra("profile", user);
                startActivity(intent);
            }
        });

        Button medium = (Button)findViewById(R.id.mediumButton);
        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DifficultyMenu.this, GameboardActivity.class);
                intent.putExtra("difficulty", 4);
                intent.putExtra("profile", user);
                startActivity(intent);
            }
        });

        Button hard = (Button)findViewById(R.id.hardButton);
        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DifficultyMenu.this, GameboardActivity.class);
                intent.putExtra("difficulty", 3);
                intent.putExtra("profile", user);
                startActivity(intent);
            }
        });

        Button help = (Button)findViewById(R.id.helpButton);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DifficultyMenu.this, Tutorial.class);
                startActivity(intent);
            }
        });
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
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