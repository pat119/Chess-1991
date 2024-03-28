package com.example.myapplication.pieces;

public class EnemyPiece extends Piece{

    // True when the piece can move, false when it acts like a sentry.
    private boolean dynamic;

    // What kind of piece it is, 1 for knight, 2 for bishop, 3 for rook, 4 for queen
    private int type;

    // Value piece has in chess, used for score. 3 for bishops/knights, 5 for rooks 9 for queens
    private int value;

    // Used to change a piece appearance. May be used if a piece can switch from a sentry to dynamic
    // or if power ups change appearance of enemies, but if we don't end up using this, remove this
    // function and the abstract in Piece.
    @Override
    void morph(int condition) {

    }
}
