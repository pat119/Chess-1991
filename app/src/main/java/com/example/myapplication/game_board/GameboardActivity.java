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
import com.example.myapplication.pieces.Tile;

import java.util.HashMap;
import java.util.Map;

public class GameboardActivity extends AppCompatActivity {

    Tile playerTile;

    Map<Integer, Tile> tiles;

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
        tiles = new HashMap<>();
        initializeMap(tiles);
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

    public void initializeMap(Map<Integer, Tile> tiles) {
        tiles.put(R.id.A1Button, new Tile("18"));
        tiles.put(R.id.A2Button, new Tile("17"));
        tiles.put(R.id.A3Button, new Tile("16"));
        tiles.put(R.id.A4Button, new Tile("15"));
        tiles.put(R.id.A5Button, new Tile("14"));
        tiles.put(R.id.A6Button, new Tile("13"));
        tiles.put(R.id.A7Button, new Tile("12"));
        tiles.put(R.id.A8Button, new Tile("11"));

        tiles.put(R.id.B1Button, new Tile("28"));
        tiles.put(R.id.B2Button, new Tile("27"));
        tiles.put(R.id.B3Button, new Tile("26"));
        tiles.put(R.id.B4Button, new Tile("25"));
        tiles.put(R.id.B5Button, new Tile("24"));
        tiles.put(R.id.B6Button, new Tile("23"));
        tiles.put(R.id.B7Button, new Tile("22"));
        tiles.put(R.id.B8Button, new Tile("21"));

        tiles.put(R.id.C1Button, new Tile("38"));
        tiles.put(R.id.C2Button, new Tile("37"));
        tiles.put(R.id.C3Button, new Tile("36"));
        tiles.put(R.id.C4Button, new Tile("35"));
        tiles.put(R.id.C5Button, new Tile("34"));
        tiles.put(R.id.C6Button, new Tile("33"));
        tiles.put(R.id.C7Button, new Tile("32"));
        tiles.put(R.id.C8Button, new Tile("31"));

        tiles.put(R.id.D1Button, new Tile("48"));
        tiles.put(R.id.D2Button, new Tile("47"));
        tiles.put(R.id.D3Button, new Tile("46"));
        tiles.put(R.id.D4Button, new Tile("45"));
        tiles.put(R.id.D5Button, new Tile("44"));
        tiles.put(R.id.D6Button, new Tile("43"));
        tiles.put(R.id.D7Button, new Tile("42"));
        tiles.put(R.id.D8Button, new Tile("41"));

        tiles.put(R.id.E1Button, new Tile("58"));
        tiles.put(R.id.E2Button, new Tile("57"));
        tiles.put(R.id.E3Button, new Tile("56"));
        tiles.put(R.id.E4Button, new Tile("55"));
        tiles.put(R.id.E5Button, new Tile("54"));
        tiles.put(R.id.E6Button, new Tile("53"));
        tiles.put(R.id.E7Button, new Tile("52"));
        tiles.put(R.id.E8Button, new Tile("51"));

        tiles.put(R.id.F1Button, new Tile("68"));
        tiles.put(R.id.F2Button, new Tile("67"));
        tiles.put(R.id.F3Button, new Tile("66"));
        tiles.put(R.id.F4Button, new Tile("65"));
        tiles.put(R.id.F5Button, new Tile("64"));
        tiles.put(R.id.F6Button, new Tile("63"));
        tiles.put(R.id.F7Button, new Tile("62"));
        tiles.put(R.id.F8Button, new Tile("61"));

        tiles.put(R.id.G1Button, new Tile("78"));
        tiles.put(R.id.G2Button, new Tile("77"));
        tiles.put(R.id.G3Button, new Tile("76"));
        tiles.put(R.id.G4Button, new Tile("75"));
        tiles.put(R.id.G5Button, new Tile("74"));
        tiles.put(R.id.G6Button, new Tile("73"));
        tiles.put(R.id.G7Button, new Tile("72"));
        tiles.put(R.id.G8Button, new Tile("71"));

        tiles.put(R.id.H1Button, new Tile("88"));
        tiles.put(R.id.H2Button, new Tile("87"));
        tiles.put(R.id.H3Button, new Tile("86"));
        tiles.put(R.id.H4Button, new Tile("85"));
        tiles.put(R.id.H5Button, new Tile("84"));
        tiles.put(R.id.H6Button, new Tile("83"));
        tiles.put(R.id.H7Button, new Tile("82"));
        tiles.put(R.id.H8Button, new Tile("81"));

    }
}