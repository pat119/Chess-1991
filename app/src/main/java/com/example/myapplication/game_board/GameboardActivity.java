package com.example.myapplication.game_board;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.checkmate.CheckmateScreen;
import com.example.myapplication.checkmate.FragmentCheckmate;
import com.example.myapplication.difficulty_menu.DifficultyMenu;
import com.example.myapplication.main_menu.MainActivity;
import com.example.myapplication.pieces.PlayerPiece;
import com.example.myapplication.pieces.Tile;
import com.example.myapplication.tutorial.Tutorial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GameboardActivity extends AppCompatActivity {
    // Player state list
    // 0: idle
    // 1: selecting knight move
    // 2: selecting bishop move
    // 3: selecting rook move
    int playerState;
    Tile playerTile;
    Tile selectedTile;
    int selectedButtonID;
    Map<Integer, Tile> tiles;

    // Dictionary of how many points are rewarded for each enemy piece
    Map<Drawable, Integer> pointList;
    int enemyCounter;

    // Used to display info top of screen
    int waves;
    int lives;
    int score;

    // How many turns have passed
    int turns;

    // How many turns until next wave
    int difficultyScale;

    Drawable redKnight;
    Drawable redBishop;
    Drawable redRook;
    Drawable redQueen;
    Drawable greyKnight;
    Drawable greyBishop;
    Drawable greyRook;
    Drawable greyQueen;
    Drawable greyAlert;
    Drawable redAlert;
    Drawable playerStar;
    Drawable transparent;
    private View decorView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameboard);

        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                decorView.setSystemUiVisibility(hideSystemBars());
            }
        });

        playerState = 0;
        waves = 0;
        lives = 3;
        score = 0;
        difficultyScale = 3;

        TextView waveText = findViewById(R.id.waveText);
        TextView lifeText = findViewById(R.id.livesText);
        TextView scoreText = findViewById(R.id.scoreText);
        waveText.setText("" + waves);
        lifeText.setText("" + lives);
        scoreText.setText("" + score);

        redKnight = getResources().getDrawable(R.drawable.knight_red, getTheme());
        redBishop = getResources().getDrawable(R.drawable.bishop_red, getTheme());
        redRook = getResources().getDrawable(R.drawable.rook_red, getTheme());
        redQueen = getResources().getDrawable(R.drawable.queen_red, getTheme());
        greyKnight = getResources().getDrawable(R.drawable.knight_grey, getTheme());
        greyBishop = getResources().getDrawable(R.drawable.bishop_grey, getTheme());
        greyRook = getResources().getDrawable(R.drawable.rook_grey, getTheme());
        greyQueen = getResources().getDrawable(R.drawable.queen_grey, getTheme());
        greyAlert = getResources().getDrawable(R.drawable.grey_alert, getTheme());
        redAlert = getResources().getDrawable(R.drawable.red_alert, getTheme());
        playerStar = getResources().getDrawable(R.drawable.player_star, getTheme());
        transparent = getResources().getDrawable(R.drawable.transparent, getTheme());

        // Set up tutorial button in the upper-right corner
        Button tutorialButton = (Button) findViewById(R.id.tutorialButtonGameboard);
        tutorialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameboardActivity.this, Tutorial.class);
                startActivity(intent);
            }
        });

        // Set up back button in the upper-left corner
        Button backButton = (Button) findViewById(R.id.boardBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameboardActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        tiles = new HashMap<>();
        initializeMap(tiles);// Fills the map
        playerTile = tiles.get(R.id.D5Button);  // Player always starts on D5, so set playerTile
        playerTile.setPiece(new PlayerPiece(0));    // Put a player piece in playerTile
        ImageButton playerButton = findViewById(playerTile.id());
        playerButton.setForeground(playerStar);

        pointList = new HashMap<>();
        initializePointList(pointList);

        Toast confirm = Toast.makeText(this /* MyActivity */,
                "You must select a tile before commiting!", Toast.LENGTH_SHORT);


        ImageButton leftButton = (ImageButton) findViewById(R.id.knightButton);
        ImageButton middleButton = (ImageButton) findViewById(R.id.bishopButton);
        ImageButton rightButton = (ImageButton) findViewById(R.id.rookButton);

        // This is the button with the knight icon
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playerState == 0) { // No move type currently selected
                    playerState = 1;
                    showCompatible(1);
                    middleButton.setBackgroundResource(R.drawable.game_back);
                    rightButton.setBackgroundResource(R.drawable.confirm_bad);
                }   // If a move type has been selected, left button should do nothing
            }
        });

        // This is the button that can either have the bishop icon, or back arrow icon
        middleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playerState == 0) {   // No move type currently selected
                    playerState = 2;
                    showCompatible(2);
                    leftButton.setBackgroundResource(R.drawable.bishop);
                    middleButton.setBackgroundResource(R.drawable.game_back);
                    rightButton.setBackgroundResource(R.drawable.confirm_bad);
                } else {    // If a move type is currently selected, middle button is back.
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

        // This is the button that can have either the rook icon, or checkmark icon
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playerState == 0) {     // No move type currently selected
                    playerState = 3;
                    showCompatible(3);
                    leftButton.setBackgroundResource(R.drawable.rook);
                    middleButton.setBackgroundResource(R.drawable.game_back);
                    rightButton.setBackgroundResource(R.drawable.confirm_bad);
                } else {    // If a move type is currently selected, right button is confirm.
                    if (selectedTile != null) {     // Currently have a tile selected
                        hideCompatible(playerState);
                        ImageButton selectedButton = (ImageButton) findViewById(selectedButtonID);

                        // If enemy piece is captured, reward points
                        Drawable enemyPiece = tileHasEnemy(selectedButton);
                        if (enemyPiece != null) {
                            score += pointList.get(enemyPiece);
                            scoreText.setText(String.valueOf(score));
                        }

                        // Move player piece to selected tile
                        selectedButton.setForeground(playerStar);
                        ImageButton playerButton = findViewById(playerTile.id());

                        // Change appearance of the tile the player piece was previously on
                        playerButton.setForeground(transparent);

                        playerTile = selectedTile;
//                        playerTile.setPiece();

                        // Reset the three buttons
                        playerState = 0;
                        selectedTile = null;
                        selectedButtonID = 0;
                        leftButton.setBackgroundResource(R.drawable.knight);
                        middleButton.setBackgroundResource(R.drawable.bishop);
                        rightButton.setBackgroundResource(R.drawable.rook);

                        // Proceed to enemy and spawning actions
                        enemyMove();

                    } else confirm.show();  // A tile has not been selected, warn the user
                }
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


    // This method activates when the user clicks on a tile
    // It will change the appearance of the selected tile appropriately
    public void onTileClick(View view) {
        ImageButton rightButton = (ImageButton) findViewById(R.id.rookButton);
        Tile clicked = tiles.get(view.getId());
        // The button that was previously selected by the player
        ImageButton selectedButton = (ImageButton) findViewById(selectedButtonID);

        if (playerState != 0) {     // Checks to make sure we are selecting a move
            assert clicked != null;
            if (clicked.compatible(playerTile, playerState) && inPath(playerTile, clicked, playerState)) {  // Check if selected tile is compatible with move

                if (selectedButton != null) {   // Do we already have a tile selected
                    if (selectedTile.color() == 0)   // Makes the previously selected square unselected
                        selectedButton.setImageResource(R.drawable.purple_selectable);
                    else selectedButton.setImageResource(R.drawable.black_selectable);
                }

                // Makes the new square selected
                if (clicked.color() == 0) {
                    ImageButton btn = (ImageButton) view;
                    btn.setImageResource(R.drawable.purple_selected);
                }
                else {
                    ImageButton btn = (ImageButton) view;
                    btn.setImageResource(R.drawable.black_selected);
                }

                rightButton.setBackgroundResource(R.drawable.confirm_good); // Makes the right button colored
                selectedTile = clicked;     // Update selectedTile
                selectedButtonID = view.getId();        // Update selectedButtonID

            } else {
                Toast toast = Toast.makeText(this, "The tile you selected is not compatible!", Toast.LENGTH_SHORT);
                toast.show();
            }
        } else {
            Toast toast = Toast.makeText(this, "You must select a move type!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    // Move enemy pieces, begin to spawn new enemies and power ups
    public void enemyMove() {
        // TODO: move enemy pieces
        // First, get list of tiles that have enemy pieces
        ArrayList<Tile> enemies = getTilesWithEnemies();

        for (Tile enemy : enemies) {
            if (enemy.compatible(playerTile, getMove(findViewById(enemy.id())))) { // Checks if piece can capture player
                if (inPath(enemy, playerTile, getMove(findViewById(enemy.id())))) { // Ensure tile is not blocked
                    lives--;
                    TextView lifeText = findViewById(R.id.livesText);
                    lifeText.setText("" + lives);
                    Toast toast = Toast.makeText(this, "Ouch!", Toast.LENGTH_SHORT);
                    toast.show();
                    if (lives == 0) {
                        Intent intent = new Intent(GameboardActivity.this, CheckmateScreen.class);
                        intent.putExtra("difficulty", difficultyScale);
                        intent.putExtra("score",score);
                        intent.putExtra("wave", waves);
                        startActivity(intent);
                    }
                    break;
                    // We should maybe throw a toast or snackbar to alert the player has been captured
                }
            }
            if (!isGrey(enemy)) {
                Object[] values = tiles.values().toArray();
                Tile closest = enemy;
                for (Object possible : values) {
                    Tile tile = (Tile) possible;
                    if (enemy.compatible(tile, getMove(findViewById(enemy.id()))) && tileHasEnemy(findViewById(tile.id())) == null) {
                        if (playerTile.distance(closest) > playerTile.distance(tile) && inPath(enemy, tile, getMove(findViewById(enemy.id())))) {
                            closest = tile;
                        }
                    }
                }
                findViewById(closest.id()).setForeground(tileHasEnemy(findViewById(enemy.id())));
                findViewById(enemy.id()).setForeground(transparent);
            }

        }

        // Current logic: begin to spawn one enemy every few turns
        // To do this, we can add a counter variable to keep track of
        // when an enemy was last spawned

        // TODO: Turn enemy spawn icons into enemies
        // Select tiles with enemy spawn icons
        ArrayList<Tile> enemySpawns = getTilesWithEnemySpawns();

        for (Tile enemySpawn : enemySpawns) {
            ImageButton tileButton = findViewById(enemySpawn.id());

            Drawable spawnColor = tileButton.getForeground();
            Drawable enemyToSpawn = null;
            if (spawnColor.equals(greyAlert))
                enemyToSpawn = getRandomGreyEnemy();
            if (spawnColor.equals(redAlert))
                enemyToSpawn = getRandomRedEnemy();
            tileButton.setForeground(enemyToSpawn);
        }

        // TODO: Place enemy spawn icons
        // Randomly select a tile
        if (turns % difficultyScale == 0) {
            Random generator = new Random();
            Object[] values = tiles.values().toArray();

            Tile randomTile = (Tile) values[generator.nextInt(values.length)];
            ImageButton tileButton = findViewById(randomTile.id());
            for (int i = 0; i <= waves; i++) {
                // Check if tile is already occupied, if it is, then get another one
                while (!tileIsEmpty(tileButton)) {
                    randomTile = (Tile) values[generator.nextInt(values.length)];
                    tileButton = findViewById(randomTile.id());
                }

                int flip = generator.nextInt(2);
                Drawable enemySpawnIcon = (flip == 0) ? redAlert : greyAlert;
                tileButton.setForeground(enemySpawnIcon);
            }
            waves++;
        }
        turns++;

        TextView waveText = findViewById(R.id.waveText);
        TextView lifeText = findViewById(R.id.livesText);
        waveText.setText("" + waves);
        lifeText.setText("" + lives);
    }

    public boolean isGrey(Tile tile) {
        Drawable piece = tileHasEnemy(findViewById(tile.id()));
        return piece == greyBishop || piece == greyKnight || piece == greyQueen || piece == greyRook;
    }

    // Checks if the path is blocked from first tile to second
    public boolean inPath(Tile from, Tile to, int move) {
        Object[] values = tiles.values().toArray();

        if (move == 1) // knights can jump
            return true;

        for (Object value : values) {
            Tile tile = (Tile) value;
            if (from.compatible(tile, move)) { // Only compatible tiles can block
                if (from.line(to) == from.line(tile) && from.distance(tile) < from.distance(to)) { // Tile is in path
                    if (tileHasEnemy(findViewById(tile.id())) != null) { // Tile has piece
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public Drawable getRandomGreyEnemy() {
        Random generator = new Random();
        Object[] values = {greyKnight, greyBishop, greyRook, greyQueen};
        return (Drawable) values[generator.nextInt(values.length)];
    }

    public Drawable getRandomRedEnemy() {
        Random generator = new Random();
        Object[] values = {redKnight, redBishop, redRook, redQueen};
        return (Drawable) values[generator.nextInt(values.length)];
    }

    public ArrayList<Tile> getTilesWithEnemies() {
        ArrayList<Tile> toReturn = new ArrayList<>();
        Object[] values = tiles.values().toArray();

        for (Object value : values) {
            Tile tile = (Tile) value;
            ImageButton tileButton = findViewById((tile.id()));
            if (tileHasEnemy(tileButton) != null)
                toReturn.add(tile);
        }
        return toReturn;
    }

    public ArrayList<Tile> getTilesWithEnemySpawns() {
        ArrayList<Tile> toReturn = new ArrayList<>();
        Object[] values = tiles.values().toArray();

        for (Object value : values) {
            Tile tile = (Tile) value;
            ImageButton tileButton = findViewById((tile.id()));
            if (tileHasEnemySpawn(tileButton) != null)
                toReturn.add(tile);
        }
        return toReturn;
    }

    public Drawable tileHasEnemy(ImageButton tileButton) {
        Drawable potentialPiece = tileButton.getForeground();
        if (potentialPiece == null) return null;

        if (potentialPiece.equals(redKnight)) return potentialPiece;
        if (potentialPiece.equals(redBishop)) return potentialPiece;
        if (potentialPiece.equals(redRook)) return potentialPiece;
        if (potentialPiece.equals(redQueen)) return potentialPiece;
        if (potentialPiece.equals(greyKnight)) return potentialPiece;
        if (potentialPiece.equals(greyBishop)) return potentialPiece;
        if (potentialPiece.equals(greyRook)) return potentialPiece;
        if (potentialPiece.equals(greyQueen)) return potentialPiece;
        return null;
    }

    public Drawable tileHasEnemySpawn(ImageButton tileButton) {
        Drawable potentialPiece = tileButton.getForeground();
        if (potentialPiece == null) return null;

        if (potentialPiece.equals(greyAlert)) return potentialPiece;
        if (potentialPiece.equals(redAlert)) return potentialPiece;
        return null;
    }

    public boolean tileIsEmpty(ImageButton tileButton) {
        Drawable potentialPiece = tileButton.getForeground();
        return potentialPiece == null || potentialPiece.equals(transparent);
    }

    public int getMove(ImageButton tileButton) {
        Drawable potentialPiece = tileButton.getForeground();

        if (potentialPiece.equals(redKnight)) return 1;
        if (potentialPiece.equals(redBishop)) return 2;
        if (potentialPiece.equals(redRook)) return 3;
        if (potentialPiece.equals(redQueen)) return 4;
        if (potentialPiece.equals(greyKnight)) return 1;
        if (potentialPiece.equals(greyBishop)) return 2;
        if (potentialPiece.equals(greyRook)) return 3;
        if (potentialPiece.equals(greyQueen)) return 4;
        return 0;
    }

    // Used to highlight compatible tiles
    public void showCompatible(int type) {
        for (Tile tile : tiles.values()) {
            ImageButton tileButton = (ImageButton) findViewById(tile.id());
            if (tile.compatible(playerTile, type) && inPath(playerTile, tile, playerState)) {
                if (tile.color() == 0)
                    tileButton.setImageResource(R.drawable.purple_selectable);
                if (tile.color() == 1)
                    tileButton.setImageResource(R.drawable.black_selectable);
            }
        }
    }

    // Used to unhighlight tiles
    public void hideCompatible(int type) {
        for (Tile tile : tiles.values()) {
            ImageButton tileButton = (ImageButton) findViewById(tile.id());

            if (tile.color() == 0)
                tileButton.setImageResource(R.drawable.purplesquare);
            if (tile.color() == 1)
                tileButton.setImageResource(R.drawable.blacksquare);
        }
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

    public void initializePointList(Map<Drawable, Integer> pointList) {
        pointList.put(redKnight, 5);
        pointList.put(redBishop, 5);
        pointList.put(redRook, 7);
        pointList.put(redQueen, 11);
        pointList.put(greyKnight, 3);
        pointList.put(greyBishop, 3);
        pointList.put(greyRook, 5);
        pointList.put(greyQueen, 9);
    }
}