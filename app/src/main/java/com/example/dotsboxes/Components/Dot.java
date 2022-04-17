package com.example.dotsboxes.Components;

public class Dot {
    private final float x;
    private final float y;

    private final int maxLines;
    private int numLines;
    private boolean saturated;

    public Dot(float x, float y, int maxLines) { this.x = x; this.y = y; this.maxLines = maxLines; }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void addLine() {
        if (!saturated) {
            numLines++;
            saturated = numLines >= maxLines;
        }
    }

    public boolean isSaturated() {
        return saturated;
    }

    public void reset() {
        saturated = false;
        numLines = 0;
    }

}
