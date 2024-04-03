package com.example.myapplication.pieces;

import com.example.myapplication.R;

public class PlayerPiece extends Piece{

    // What kind of power the user currently has, 0 corresponds with no power.
    private int power;

    // How many remaining turns of being powered.
    private int power_turns;

    // Types 1, 2, 3 correspond with knight, bishop, rook respectively
    private int type;

    public PlayerPiece(int type) {
        this.type = type;
    }

    // Conditions 0, 1, 2, 3 change piece too look like its default, a knight, a bishop, or a rook
    // respectively.
    void morph(int condition) {
        switch (condition) {
            case 0:
                img_source = R.drawable.player_star;
                type = 0;
                break;
            case 1:
                img_source = R.drawable.player_knight;
                type = 1;
                break;
            case 2:
                img_source = R.drawable.player_bishop;
                type = 2;
                break;
            case 3:
                img_source = R.drawable.player_rook;
                type = 3;
                break;
        }

    }

    public int type() {
        return type;
    }
}
