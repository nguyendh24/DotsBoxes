package com.example.dotsboxes;

//Create a player/user
public class Player {

    private String name;
    private String color;

    public Player(String name, String color){
        this.name = name;
        this.color = color;
    }

    public Player(){
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }
}
