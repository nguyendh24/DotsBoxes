package com.example.dotsboxes.Components;

import android.graphics.Color;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Square)) return false;
        Square square = (Square) o;
        return (Float.compare(square.x, x) == 0
                && Float.compare(square.y, y) == 0);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public void reset() {
        sides = 0;
        filled = false;
        player = null;
    }
}
