package com.startechies.dotsboxes.Components;

public class Dot {
    private final float x;
    private final float y;

    private final int maxLines;
    private int numLines;

    public Dot(float x, float y, int maxLines) { this.x = x; this.y = y; this.maxLines = maxLines; }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void addLine() {
        if (hasOpenLines()) {
            numLines++;
        }
    }

    public boolean hasOpenLines() {
        return numLines < maxLines;
    }

    public void reset() {
        numLines = 0;
    }

}
