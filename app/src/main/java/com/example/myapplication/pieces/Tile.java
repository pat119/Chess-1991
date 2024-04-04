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
        ButtonID = buttonID;
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
        if (this.column == tile.column && this.row == tile.row) {
            return false;
        }

        if (moveType == 1) {// Check for knight
            if (this.column - tile.column == 2 || this.column - tile.column == -2) {
                return this.row - tile.row == 1 || this.row - tile.row == -1;
            } else if (this.column - tile.column == 1 || this.column - tile.column == -1) {
                return this.row - tile.row == 2 || this.row - tile.row == -2;
            } else {
                return false;
            }
        } else if (moveType == 2) {// Check for bishop
            return (this.column - tile.column) == (this.row - tile.row);
        } else if (moveType == 3) {// Check for rook
            return this.column == tile.column || this.row == tile.row;
        } else {
            return false;
        }

    }

    public Piece piece() {
        return piece;
    }

    public int color() {
        return color;
    }

    public int id() {
        return ButtonID;
    }
}
