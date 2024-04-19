package com.example.myapplication.leaderboard;

public class LeaderboardEntry {
    private String name;
    private int score;

    private int place;

    public  LeaderboardEntry() {
        this.name = "error";
        this.score = 0;
        this.place = 0;
    }

    public LeaderboardEntry(String name, int score, int place) {
        this.name = name;
        this.score = score;
        this.place = place;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public int getPlace() {
        return place;
    }

    // Getters and setters (optional)
}
