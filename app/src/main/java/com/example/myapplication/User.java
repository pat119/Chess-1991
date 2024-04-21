package com.example.myapplication;

import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String password;
    private String key;

    // Used to hold customization data
    private int piece;
    private int theme;

    // Pac-man unlock
    private boolean unlock1;

    // Jester unlock
    private boolean unlock2;

    // First private unlock
    private boolean unlock3;

    // Second private unlock
    private boolean unlock4;

    // Third private unlock
    private boolean unlock5;

    User() {}
    public User(String user, String pass, String key) {
        username = user;
        password = pass;
        this.key = key;
        piece = 0; // Corresponds to star
        theme = 0; // Corresponds to base board
        unlock1 = false;
        unlock2 = false;
        unlock3 = false;
        unlock4 = false;
        unlock5 = false;
    }

    public User(User copy, String user, String pass, String key) { // Copy constructor used for later
        username = user;
        password = pass;
        this.key = key;
        piece = copy.getPiece(); // Corresponds to star
        theme = copy.getTheme(); // Corresponds to base board
        unlock1 = copy.isUnlock1();
        unlock2 = copy.isUnlock2();
        unlock3 = copy.isUnlock3();
        unlock4 = copy.isUnlock4();
        unlock5 = copy.isUnlock5();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public  String getKey() {
        return key;
    }

    public int getPiece() {return piece;}
    public int getTheme() {return theme;}

    public boolean isUnlock1() {
        return unlock1;
    }

    public boolean isUnlock2() {
        return unlock2;
    }

    public boolean isUnlock3() {
        return unlock3;
    }

    public boolean isUnlock4() {
        return unlock4;
    }

    public boolean isUnlock5() {
        return unlock5;
    }

    public void setPiece(int piece) {
        this.piece = piece;
    }
    public void setTheme(int theme) {
        this.theme = theme;
    }
    public void unlock(int unlock) {
        switch (unlock) {
            case 1:
                unlock1 = true;
                break;
            case 2:
                unlock2 = true;
                break;
            case 3:
                unlock3 = true;
                break;
            case 4:
                unlock4 = true;
                break;
            case 5:
                unlock5 = true;
                break;
            default:
                break;
        }
    }
}
