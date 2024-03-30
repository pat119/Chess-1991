package com.example.myapplication.game_board;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;
import com.example.myapplication.difficulty_menu.DifficultyMenu;
import com.example.myapplication.main_menu.MainActivity;

public class GameboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameboard);

        Button backButton = (Button)findViewById(R.id.boardBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameboardActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}