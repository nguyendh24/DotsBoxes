package com.example.dotsboxes.Components;

import com.example.dotsboxes.Player;

import java.util.Objects;

public class Line {

    private static int defaultColor;

    private final float x1, y1, x2, y2;
    private boolean selected;
    private int color;

    public Line(float x1, float y1, float x2, float y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.color = defaultColor;
        selected = false;
    }

    public void selectLine(Player player) {
        selected = true;
        this.color = player.getColor();
    }

    public static void setDefaultColor(int defaultColor) {
        Line.defaultColor = defaultColor;
    }

    /** Getters */
    public float getX1() { return x1; }

    public float getY1() { return y1; }

    public float getX2() { return x2; }

    public float getY2() { return y2; }

    public int getColor() { return color; }

    public boolean isSelected() {
        return selected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Line)) return false;
        Line line = (Line) o;
        return (Float.compare(line.x1, x1) == 0
                && Float.compare(line.y1, y1) == 0
                && Float.compare(line.x2, x2) == 0
                && Float.compare(line.y2, y2) == 0) ||
                Float.compare(line.x1, x2) == 0
                        && Float.compare(line.y1, y2) == 0
                        && Float.compare(line.x2, x1) == 0
                        && Float.compare(line.y2, y1) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x1, y1, x2, y2);
    }
}
