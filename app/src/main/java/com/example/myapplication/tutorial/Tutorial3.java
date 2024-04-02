package com.example.myapplication.tutorial;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.game_board.GameboardActivity;
import com.example.myapplication.main_menu.MainActivity;

public class Tutorial3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorialp3);

        Button backNavButton = (Button)findViewById(R.id.backNavButton);

        Button backButton = (Button)findViewById(R.id.backButton);

        Button nextButton = (Button)findViewById(R.id.nextButton);

        backNavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Tutorial3.this, MainActivity.class);
//                startActivity(intent);
                finish();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tutorial3.this, Tutorial4.class);
                startActivity(intent);
                finish();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tutorial3.this, Tutorial2.class);
                startActivity(intent);
                finish();
            }
        });

    }
}