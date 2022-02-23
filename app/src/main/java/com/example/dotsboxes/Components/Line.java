package com.example.dotsboxes.Components;

import android.graphics.Color;
import android.graphics.Paint;

import java.util.Objects;

public class Line {
    private float x1, y1, x2, y2;
    private Direction dir;
    private int playerIdx;
    private int color;

    public Line() {}

    public Line(float x1, float y1, float x2, float y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public Line(float x1, float y1, float x2, float y2, Direction dir) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.dir = dir;
    }

    /** Setters */
    public void setX1(float x1) { this.x1 = x1; }

    public void setY1(float y1) { this.y1 = y1; }

    public void setX2(float x2) { this.x2 = x2; }

    public void setY2(float y2) { this.y2 = y2; }

    public void setDir(Direction dir) { this.dir = dir; }

    public void setPlayerIdx(int playerIdx) { this.playerIdx = playerIdx; }

    public void setColor(int color) { this.color = color; }

    /** Getters */
    public float getX1() { return x1; }

    public float getY1() { return y1; }

    public float getX2() { return x2; }

    public float getY2() { return y2; }

    public Direction getDir() { return dir; }

    public int getPlayerIdx() { return playerIdx; }

    public int getColor() { return color; }

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
