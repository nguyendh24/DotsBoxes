package com.example.dotsboxes.Components;

import android.graphics.Color;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Line {

    private static final int DEFAULT_COLOR = Color.BLACK;;

    private final float x1, y1, x2, y2;
    private final Set<Square> adjacentSquares;
    private final Set<Dot> adjacentDots;
    private boolean selected;
    private Player player;

    public Line(float x1, float y1, float x2, float y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        adjacentSquares = new HashSet<>();
        adjacentDots = new HashSet<>();
        selected = false;
    }

    public void selectLine(Player player) {
        selected = true;
        this.player = player;
        for (Square square : adjacentSquares) {
            square.addSide(player);
        }
        for (Dot dot : adjacentDots) {
            dot.addLine();
        }
    }

    /** Getters */
    public float getX1() { return x1; }

    public float getY1() { return y1; }

    public float getX2() { return x2; }

    public float getY2() { return y2; }

    public int getColor() {
        if (selected && player != null) {
            return player.getColor();
        }
        return DEFAULT_COLOR;
    }

    public int getPid() {
        return player.getPid();
    }

    public boolean isSelected() {
        return selected;
    }

    public void addAdjacentSquare(Square square) {
        adjacentSquares.add(square);
    }

    public void addAdjacentDot(Dot dot) {
        adjacentDots.add(dot);
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

    public void reset() {
        selected = false;
        player = null;
    }
}
