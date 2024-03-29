package com.example.myapplication.pieces;

public class Tile {
    PowerUp powerUp;
    Piece piece;

    int column;
    int row;

    // pos passed as "11", "22", with 1 representing "a" in the first position
    Tile(String pos) {
        column = Character.getNumericValue(pos.charAt(1));
        row = Character.getNumericValue(pos.charAt(1));
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
}
