package com.example.myapplication.checkmate;

import android.os.Bundle;

import com.example.myapplication.R;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.game_board.GameboardActivity;
import com.example.myapplication.main_menu.MainActivity;

import org.w3c.dom.Text;

public class CheckmateScreen extends AppCompatActivity {

    private View decorView;
    private String difficultyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_checkmate);

        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                decorView.setSystemUiVisibility(hideSystemBars());
            }
        });

        // retrieve data from previous intent
        Bundle extras = getIntent().getExtras();
        int difficultyValue = 0;
        int score = 0;
        int wave = 0;
        if (extras != null) {
            difficultyValue = extras.getInt("difficulty");
            score = extras.getInt("score");
            wave = extras.getInt("wave");
        }

        TextView difficulty = (TextView) findViewById(R.id.difficultyValue);
        switch (difficultyValue) {
            case 3:
                difficultyText = "Hard";
                break;
            case 4:
                difficultyText = "Medium";
                break;
            case 5:
                difficultyText = "Easy";
                break;
        }
        difficulty.setText(difficultyText);

        TextView scoreText = (TextView) findViewById(R.id.pointsValue);
        scoreText.setText("" + score);

        TextView waveText = (TextView) findViewById(R.id.waveValue);
        waveText.setText("" + wave);

        Button homeButton = (Button) findViewById(R.id.home);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckmateScreen.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button playAgain = (Button) findViewById(R.id.again);
        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckmateScreen.this, GameboardActivity.class);
                startActivity(intent);
            }
        });

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
