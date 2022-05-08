package com.startechies.dotsboxes.Components;

import android.graphics.Color;

import java.util.HashSet;
import java.util.Set;

public class Line {

    private final int DEFAULT_COLOR = Color.BLACK;;

    private final float x1, y1, x2, y2;
    private final Set<Square> adjacentSquares;
    private final Set<Dot> adjacentDots;
    private Player player;

    public Line(float x1, float y1, float x2, float y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        adjacentSquares = new HashSet<>();
        adjacentDots = new HashSet<>();
    }

    public void selectLine(Player player) {
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
        if (isSelected()) {
            return player.getColor();
        }
        return DEFAULT_COLOR;
    }

    public int getPid() {
        if (isSelected()) {
            return player.getPid();
        }
        return -1;
    }

    public boolean isSelected() {
        return player != null;
    }

    public void addAdjacentSquare(Square square) {
        adjacentSquares.add(square);
    }

    public void addAdjacentDot(Dot dot) {
        adjacentDots.add(dot);
    }

    public void reset() {
        player = null;
    }
}
