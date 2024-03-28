package com.example.myapplication.pieces;

public abstract class Piece {

    // Contains the file path for the image associated
    String img_source;

    // Used when changing appearance, and therefore img_source
    abstract void morph(int condition);
}
