package com.example.myapplication.checkmate;

import android.os.Bundle;

import com.example.myapplication.R;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.game_board.GameboardActivity;
import com.example.myapplication.main_menu.MainActivity;

public class CheckmateScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        Button homeButton = (Button)findViewById(R.id.home);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckmateScreen.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button playAgain = (Button)findViewById(R.id.again);
        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckmateScreen.this, GameboardActivity.class);
                startActivity(intent);
            }
        });

    }
}
