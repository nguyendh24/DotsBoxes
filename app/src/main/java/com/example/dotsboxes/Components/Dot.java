package com.example.dotsboxes.Components;

import androidx.annotation.NonNull;

import java.util.Objects;

public class Dot {
    private final float x;
    private final float y;

    public Dot(float x, float y) { this.x = x; this.y = y; }

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

    @NonNull
    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
