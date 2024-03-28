package com.example.myapplication.pieces;

public class PlayerPiece extends Piece{

    //
    private int power;

    // Conditions 0, 1, 2, 3 change piece too look like its default, a knight, a bishop, or a rook
    // respectively.
    @Override
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
