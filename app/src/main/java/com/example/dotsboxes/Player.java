package com.example.dotsboxes;

//Create a player/user
public class Player {

    private String name;
    private final int color;
    private int score;
    private boolean goAgain;

    public Player(String name, int color){
        this.name = name;
        this.color = color;
        this.score = 0;
        this.goAgain = false;
    }

    public void incrementScore() {
        score++;
        goAgain = true;
    }

    public boolean isGoAgain() {
        return goAgain;
    }

    public void resetGoAgain() {
        goAgain = false;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getColor() {
        return color;
    }

    public int getScore() {
        return score;
    }
}
