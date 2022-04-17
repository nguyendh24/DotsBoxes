package com.example.dotsboxes.Components;

import android.graphics.Color;

public class Square {

    private static float size;
    private static final int DEFAULT_COLOR = Color.TRANSPARENT;

    private final float x, y;

    private int sides;
    private boolean filled;
    private Player player;

    public Square(float x, float y) {
        this.x = x;
        this.y = y;
        sides = 0;
        filled = false;
    }

    public void addSide(Player player) {
        if (sides < 4) {
            sides++;
            if (sides == 4) {
                filled = true;
                this.player = player;
                player.incrementScore();
            }
        }
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isFilled() {
        return filled;
    }

    public float getX() { return x; }

    public float getY() { return y; }

    public static void setSize(float size) { Square.size = size; }

    public static float getSize() { return size; }

    public int getColor() {
        if (filled && player != null) {
            return player.getColor();
        }
        return DEFAULT_COLOR;
    }

    public int getPid() {
        return player.getPid();
    }

    public void reset() {
        sides = 0;
        filled = false;
        player = null;
    }
}
