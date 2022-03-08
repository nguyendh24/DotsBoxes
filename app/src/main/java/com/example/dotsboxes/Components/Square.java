package com.example.dotsboxes.Components;

import com.example.dotsboxes.Player;

import java.util.Objects;

public class Square {

    private static float size;
    private static int defaultColor;

    private final float x, y;
    private int color;

    private int sides;
    private boolean filled;

    public Square(float x, float y) {
        this.x = x;
        this.y = y;
        this.color = defaultColor;
        sides = 0;
        filled = false;
    }

    // Returns true if new square created
    public void addSide(Player player) {
        if (sides < 4) {
            sides++;
            if (sides == 4) {
                filled = true;
                this.color = player.getColor();
                player.incrementScore();
            }
        }
    }

    public boolean isFilled() {
        return filled;
    }

    /** Getters */
    public float getX() { return x; }

    public float getY() { return y; }

    public static void setDefaultColor(int defaultColor) {
        Square.defaultColor = defaultColor;
    }

    public static void setSize(float size) { Square.size = size; }

    public static float getSize() { return size; }

    public int getColor() { return color; }

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
        color = defaultColor;
    }
}
