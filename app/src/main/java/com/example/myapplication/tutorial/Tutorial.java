package com.example.myapplication.tutorial;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.game_board.GameboardActivity;
import com.example.myapplication.main_menu.MainActivity;

public class Tutorial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorialp1);

        Button backNavButton = (Button)findViewById(R.id.backNavButton);

        Button nextButton = (Button)findViewById(R.id.nextButton);

        backNavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Tutorial.this, MainActivity.class);
//                startActivity(intent);
                  finish();

            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tutorial.this, Tutorial2.class);
                startActivity(intent);
                finish();
            }
        });

    }
}