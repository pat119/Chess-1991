package com.example.myapplication.pieces;

public class Tile {
    PowerUp powerUp;
    Piece piece;

    int ButtonID;

    // color 0 corresponds with black, color 1 corresponds with white
    int color;

    int column;
    int row;

    // pos passed as "11", "22", with 1 representing "a" in the first position, //18 is the bottom left
    public Tile(String pos, int buttonID) {
        column = Character.getNumericValue(pos.charAt(0));
        row = Character.getNumericValue(pos.charAt(1));

        color = (column + row) % 2;
    }

    public void setPiece(Piece toSet) {
        piece = toSet;
    }

    public boolean hasPowerUp() {
        return powerUp != null;
    }

    public Piece returnPiece() {
        if (hasPiece()) {
            return piece;
        }

        return null;
    }

    public boolean hasPiece() {
        return piece != null;
    }

    // Checks if two tiles are compatible; Move type: {0 = knight, 1 = bishop, 2 = rook}
    public boolean compatible(Tile tile, int moveType) {
        return true;
    }

    public Piece piece() {
        return piece;
    }

    public int color() {
        return color;
    }
}
