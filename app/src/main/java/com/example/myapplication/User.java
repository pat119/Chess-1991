package com.example.myapplication;

public class User {
    private String username;
    private String password;
    private String key;

    User() {}
    public User(String user, String pass, String key) {
        username = user;
        password = pass;
        this.key = key;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getKey() {
        return key;
    }
}
