package com.example.myapplication.game_board;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.difficulty_menu.DifficultyMenu;
import com.example.myapplication.main_menu.MainActivity;
import com.example.myapplication.pieces.PlayerPiece;
import com.example.myapplication.pieces.Tile;

import java.util.HashMap;
import java.util.Map;

public class GameboardActivity extends AppCompatActivity {

    //Player state of 0 corresponds with idle, 1 with selecting knight move, 2 w/ bishop, 3 w/ rook
    int playerState;
    Tile playerTile;

    Tile selectedTile;

    int selectedButtonID;

    Map<Integer, Tile> tiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameboard);
        playerState = 0;

        Button backButton = (Button)findViewById(R.id.boardBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameboardActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        tiles = new HashMap<>();
        initializeMap(tiles);// Fills the map
        playerTile = tiles.get(R.id.D5Button);// Player always starts on D5, so set playerTile
        playerTile.setPiece(new PlayerPiece(0));// Put a player piece in playerTile
        Toast confirm = Toast.makeText(this /* MyActivity */, "You must select a tile before commiting!", Toast.LENGTH_SHORT);

        ImageButton leftButton = (ImageButton) findViewById(R.id.knightButton);
        ImageButton middleButton = (ImageButton) findViewById(R.id.bishopButton);
        ImageButton rightButton = (ImageButton) findViewById(R.id.rookButton);


        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playerState == 0) {// No move type currently selected
                    playerState = 1;
                    showCompatible(1);
                    middleButton.setBackgroundResource(R.drawable.game_back);
                    rightButton.setBackgroundResource(R.drawable.confirm_bad);

                }// If a move is being selected, left button should do nothing
            }
        });

        middleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playerState == 0) {// No move type currently selected
                    playerState = 2;
                    showCompatible(2);
                    leftButton.setBackgroundResource(R.drawable.bishop);
                    middleButton.setBackgroundResource(R.drawable.game_back);
                    rightButton.setBackgroundResource(R.drawable.confirm_bad);
                } else {// If a move type is currently selected, middle button is back.
                    hideCompatible(playerState);
                    playerState = 0;
                    selectedTile = null;
                    selectedButtonID = 0;
                    leftButton.setBackgroundResource(R.drawable.knight);
                    middleButton.setBackgroundResource(R.drawable.bishop);
                    rightButton.setBackgroundResource(R.drawable.rook);
                }
            }
        });

        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playerState == 0) {// No move type currently selected
                    playerState = 3;
                    showCompatible(3);
                    leftButton.setBackgroundResource(R.drawable.rook);
                    middleButton.setBackgroundResource(R.drawable.game_back);
                    rightButton.setBackgroundResource(R.drawable.confirm_bad);
                } else {// If a move type is currently selected, right button is confirm.
                    if (selectedTile != null) {// Currently have a tile selected
                        // TODO swap positions.
                    } else {
                        confirm.show();
                    }
                }
            }
        });

    }


    // This method activates when the user clicks on a tile
    // For now, it simply moves the player piece to that tile
    public void onTileClick(View view)
    {
        /*
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
        // Use button id to get corresponding tile.

         */

        ImageButton rightButton = (ImageButton) findViewById(R.id.rookButton);
        Tile clicked = tiles.get(view.getId());
        ImageButton selectedButton = (ImageButton) findViewById(selectedButtonID);


        if (playerState != 0) {// Checks to make sure we are selecting a move
            assert clicked != null;
            if (clicked.compatible(playerTile, playerState)) {// Check if selected tile is compatible with move

                if (selectedButton != null) {// Do we already have a tile selected
                    if (selectedTile.color() == 0) {// Makes the previously selected square unselected
                        selectedButton.setBackgroundResource(R.drawable.black_selectable);
                    } else {
                        selectedButton.setBackgroundResource(R.drawable.purple_selectable);
                    }
                }


                if (clicked.color() == 0) {// Makes the new square selected
                    view.setBackgroundResource(R.drawable.black_selected);
                } else {
                    view.setBackgroundResource(R.drawable.purple_selected);
                }


                rightButton.setBackgroundResource(R.drawable.confirm_good);// Makes the right button colored
                selectedTile = clicked;// Update selectedTile
                selectedButtonID = view.getId();// Update selectedButtonID

            } else {


                Toast toast = Toast.makeText(this, "The tile you selected is not compatible!", Toast.LENGTH_SHORT);
                toast.show();

            }
        } else {
            Toast toast = Toast.makeText(this, "You must select a move type!", Toast.LENGTH_SHORT);
            toast.show();
        }


    }

    // Used to highlight compatible tiles
    public void showCompatible(int type) {
        //TODO
    }

    // Used to unhighlight tiles
    public void hideCompatible(int type) {
        //TODO
    }

    public void initializeMap(Map<Integer, Tile> tiles) {

        tiles.put(R.id.A1Button, new Tile("18", R.id.A1Button));
        tiles.put(R.id.A2Button, new Tile("17", R.id.A2Button));
        tiles.put(R.id.A3Button, new Tile("16", R.id.A3Button));
        tiles.put(R.id.A4Button, new Tile("15", R.id.A4Button));
        tiles.put(R.id.A5Button, new Tile("14", R.id.A5Button));
        tiles.put(R.id.A6Button, new Tile("13", R.id.A6Button));
        tiles.put(R.id.A7Button, new Tile("12", R.id.A7Button));
        tiles.put(R.id.A8Button, new Tile("11", R.id.A8Button));


        tiles.put(R.id.B1Button, new Tile("28", R.id.B1Button));
        tiles.put(R.id.B2Button, new Tile("27", R.id.B2Button));
        tiles.put(R.id.B3Button, new Tile("26", R.id.B3Button));
        tiles.put(R.id.B4Button, new Tile("25", R.id.B4Button));
        tiles.put(R.id.B5Button, new Tile("24", R.id.B5Button));
        tiles.put(R.id.B6Button, new Tile("23", R.id.B6Button));
        tiles.put(R.id.B7Button, new Tile("22", R.id.B7Button));
        tiles.put(R.id.B8Button, new Tile("21", R.id.B8Button));




        tiles.put(R.id.C1Button, new Tile("38", R.id.C1Button));
        tiles.put(R.id.C2Button, new Tile("37", R.id.C2Button));
        tiles.put(R.id.C3Button, new Tile("36", R.id.C3Button));
        tiles.put(R.id.C4Button, new Tile("35", R.id.C4Button));
        tiles.put(R.id.C5Button, new Tile("34", R.id.C5Button));
        tiles.put(R.id.C6Button, new Tile("33", R.id.C6Button));
        tiles.put(R.id.C7Button, new Tile("32", R.id.C7Button));
        tiles.put(R.id.C8Button, new Tile("31", R.id.C8Button));


        tiles.put(R.id.D1Button, new Tile("48", R.id.D1Button));
        tiles.put(R.id.D2Button, new Tile("47", R.id.D2Button));
        tiles.put(R.id.D3Button, new Tile("46", R.id.D3Button));
        tiles.put(R.id.D4Button, new Tile("45", R.id.D4Button));
        tiles.put(R.id.D5Button, new Tile("44", R.id.D5Button));
        tiles.put(R.id.D6Button, new Tile("43", R.id.D6Button));
        tiles.put(R.id.D7Button, new Tile("42", R.id.D7Button));
        tiles.put(R.id.D8Button, new Tile("41", R.id.D8Button));


        tiles.put(R.id.E1Button, new Tile("58", R.id.E1Button));
        tiles.put(R.id.E2Button, new Tile("57", R.id.E2Button));
        tiles.put(R.id.E3Button, new Tile("56", R.id.E3Button));
        tiles.put(R.id.E4Button, new Tile("55", R.id.E4Button));
        tiles.put(R.id.E5Button, new Tile("54", R.id.E5Button));
        tiles.put(R.id.E6Button, new Tile("53", R.id.E6Button));
        tiles.put(R.id.E7Button, new Tile("52", R.id.E7Button));
        tiles.put(R.id.E8Button, new Tile("51", R.id.E8Button));


        tiles.put(R.id.F1Button, new Tile("68", R.id.F1Button));
        tiles.put(R.id.F2Button, new Tile("67", R.id.F2Button));
        tiles.put(R.id.F3Button, new Tile("66", R.id.F3Button));
        tiles.put(R.id.F4Button, new Tile("65", R.id.F4Button));
        tiles.put(R.id.F5Button, new Tile("64", R.id.F5Button));
        tiles.put(R.id.F6Button, new Tile("63", R.id.F6Button));
        tiles.put(R.id.F7Button, new Tile("62", R.id.F7Button));
        tiles.put(R.id.F8Button, new Tile("61", R.id.F8Button));


        tiles.put(R.id.G1Button, new Tile("78", R.id.G1Button));
        tiles.put(R.id.G2Button, new Tile("77", R.id.G2Button));
        tiles.put(R.id.G3Button, new Tile("76", R.id.G3Button));
        tiles.put(R.id.G4Button, new Tile("75", R.id.G4Button));
        tiles.put(R.id.G5Button, new Tile("74", R.id.G5Button));
        tiles.put(R.id.G6Button, new Tile("73", R.id.G6Button));
        tiles.put(R.id.G7Button, new Tile("72", R.id.G7Button));
        tiles.put(R.id.G8Button, new Tile("71", R.id.G8Button));


        tiles.put(R.id.H1Button, new Tile("88", R.id.H1Button));
        tiles.put(R.id.H2Button, new Tile("87", R.id.H2Button));
        tiles.put(R.id.H3Button, new Tile("86", R.id.H3Button));
        tiles.put(R.id.H4Button, new Tile("85", R.id.H4Button));
        tiles.put(R.id.H5Button, new Tile("84", R.id.H5Button));
        tiles.put(R.id.H6Button, new Tile("83", R.id.H6Button));
        tiles.put(R.id.H7Button, new Tile("82", R.id.H7Button));
        tiles.put(R.id.H8Button, new Tile("81", R.id.H8Button));

    }
}