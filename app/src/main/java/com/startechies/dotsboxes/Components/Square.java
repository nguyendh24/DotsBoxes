package com.startechies.dotsboxes.Components;

import android.graphics.Color;

public class Square {

    private static float size;
    private static final int DEFAULT_COLOR = Color.TRANSPARENT;

    private final float x, y;

    private int sides;
    private Player player;

    public Square(float x, float y) {
        this.x = x;
        this.y = y;
        this.sides = 0;
    }

    public void addSide(Player player) {
        if (!isFilled()) {
            sides++;
            if (sides == 4) {
                this.player = player;
                player.incrementScore();
            }
        }
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isFilled() {
        return sides >= 4;
    }

    public float getX() { return x; }

    public float getY() { return y; }

    public static void setSize(float size) { Square.size = size; }

    public static float getSize() { return size; }

    public int getSides() { return sides;}

    public int getColor() {
        if (isFilled() && player != null) {
            return player.getColor();
        }
        return DEFAULT_COLOR;
    }

    public int getPid() {
        if (isFilled() && player != null) {
            return player.getPid();
        }
        return -1;
    }

    public void reset() {
        sides = 0;
        player = null;
    }
}
