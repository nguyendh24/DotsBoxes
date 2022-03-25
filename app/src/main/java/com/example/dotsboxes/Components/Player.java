package com.example.dotsboxes.Components;

public class Player {

    int pid;
    private String name;
    private int color;
    private int score;
    private boolean goAgain;

    public Player(int pid, String name, int color){
        this.pid = pid;
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

    public int getPid() {
        return pid;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public int getScore() {
        return score;
    }

    public void resetScore() {
        score = 0;
    }
}
