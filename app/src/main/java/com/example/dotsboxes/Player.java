package com.example.dotsboxes;

public class Player {

    private String name;
    private int score;
    private String color;

    public Player(String name, int score, String color){
        this.name = name;
        this.score = score;
        this.color = color;
    }

    public Player(){
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public String getColor() {
        return color;
    }
}
