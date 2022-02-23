package com.example.dotsboxes.Components;

import java.util.Objects;

public class Dot {
    private float x;
    private float y;

    public Dot() {}

    public Dot(float x, float y) { this.x = x; this.y = y; }

    public void setCoordinates(float x, float y) { this.x = x; this.y = y; }

    public void setX(float x) { this.x = x; }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dot)) return false;
        Dot dot = (Dot) o;
        return Float.compare(dot.x, x) == 0 && Float.compare(dot.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
