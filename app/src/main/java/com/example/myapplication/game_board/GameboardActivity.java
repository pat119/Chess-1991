package com.example.myapplication.game_board;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

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


    // This method activates when the user clicks on a tile
    // For now, it simply moves the player piece to that tile
    public void onTileClick(View view)
    {
        switch (view.getId()) {
//            case R.id.button_send:
                // Do something
        }
//        int[] location = new int[2];
//        view.getLocationInWindow(location);

        // First get name of button
        Resources res = getResources();
        String fullName = getResources().getResourceName(view.getId());
        String name = fullName.substring(fullName.lastIndexOf("/") + 1);
        // Use button name to get name of corresponding image
        String imageName = name.substring(0, 2).concat("Image");
        int id = res.getIdentifier(imageName, "id", getApplicationContext().getPackageName());
        ImageView image = findViewById(id);
        image.setImageResource(R.drawable.player_star);
        //image.setVisibility(View.VISIBLE);

//        view.setVisibility(View.INVISIBLE);

    }
}