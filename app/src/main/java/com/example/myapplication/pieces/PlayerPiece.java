package com.example.myapplication.pieces;

public class PlayerPiece extends Piece{

    // What kind of power the user currently has, 0 corresponds with no power.
    private int power;

    // How many remaining turns of being powered.
    private int power_turns;

    // Conditions 0, 1, 2, 3 change piece too look like its default, a knight, a bishop, or a rook
    // respectively.
    void morph(int condition) {
        switch (condition) {
            case 0:
                img_source = "INSERT_DEFAULT_IMG_SOURCE";
                break;
            case 1:
                img_source = "INSERT_PLAYER_KNIGHT_IMG_SOURCE";
                break;
            case 2:
                img_source = "INSERT_PLAYER_BISHOP_IMG_SOURCE";
                break;
            case 3:
                img_source = "INSERT_PLAYER_ROOK_IMG_SOURCE";
                break;
        }

    }
}
