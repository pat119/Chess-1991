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
            return (this.column - tile.column) == (this.row - tile.row) || (this.column - tile.column) == -(this.row - tile.row);
        } else if (moveType == 3) {// Check for rook
            return this.column == tile.column || this.row == tile.row;
        } else if (moveType == 4) {// Check for queen
            return compatible(tile, 2) || compatible(tile, 3);
        } else {
            return false;
        }

    }

    // Used to check what line connects two tiles for bishops and rooks.
    public int line(Tile to) {
        if (this.column - to.column > 0 && this.row - to.row == 0) { // to is to the left
            return 1;
        } else if (this.column - to.column == 0 && this.row - to.row < 0) { // to is above
            return 2;
        } else if (this.column - to.column < 0 && this.row - to.row == 0) { // to is to the right
            return 3;
        } else if (this.column - to.column == 0 && this.row - to.row > 0) { // to is below
            return 4;
        } else if (this.column - to.column > 0 && this.row - to.row > 0) { // to is to the top right
            return 5;
        } else if (this.column - to.column > 0 && this.row - to.row < 0) { // to is to the bottom right
            return 6;
        } else if (this.column - to.column < 0 && this.row - to.row < 0) { // to is to the bottom left
            return 7;
        } else if (this.column - to.column < 0 && this.row - to.row > 0) {// to is to the top left
            return 8;
        } else {
            return 0;
        }
    }

    public int distance(Tile to) {
        return ((this.column - to.column) * (this.column - to.column)) + ((this.row - to.row) * (this.row - to.row));
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
