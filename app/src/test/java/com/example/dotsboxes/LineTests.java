package com.example.dotsboxes;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import android.graphics.Color;

import com.example.dotsboxes.Components.Dot;
import com.example.dotsboxes.Components.Line;
import com.example.dotsboxes.Components.Player;
import com.example.dotsboxes.Components.Square;

public class LineTests {

    private final int PID = 1234;
    private final int PLAYER_COLOR = 5678;

    private Line line;
    private Square adjacentSquare;
    private Dot adjacentDot;
    private Player player;

    @Before
    public void init() {
        line = new Line(0, 0, 0, 1);
        adjacentSquare = new Square(0, 0);
        adjacentDot = new Dot(0, 0, 4);
        player = new Player(PID, "player", PLAYER_COLOR);
        line.addAdjacentSquare(adjacentSquare);
        line.addAdjacentDot(adjacentDot);
        adjacentSquare.addSide(player);
        adjacentSquare.addSide(player);
        adjacentSquare.addSide(player);
        adjacentDot.addLine();
        adjacentDot.addLine();
        adjacentDot.addLine();
    }

    /* This first section of tests represents tests from
     * Test Report, Part 2: Base Choice Coverage */

    // Base case: A1 (player == NULL)
    @Test
    public void testA1() {
        assertFalse(line.isSelected());
        assertEquals(line.getColor(), Color.BLACK);
        assertEquals(line.getPid(), -1);
        assertTrue(adjacentDot.hasOpenLines());
        assertFalse(adjacentSquare.isFilled());
        assertEquals(adjacentSquare.getColor(), Color.TRANSPARENT);
        assertEquals(adjacentSquare.getPid(), -1);
    }

    // A2 (player != NULL)
    @Test
    public void testA2() {
        line.selectLine(player);
        assertTrue(line.isSelected());
        assertEquals(line.getColor(), PLAYER_COLOR);
        assertEquals(line.getPid(), PID);
        assertFalse(adjacentDot.hasOpenLines());
        assertTrue(adjacentSquare.isFilled());
        assertEquals(adjacentSquare.getColor(), PLAYER_COLOR);
        assertEquals(adjacentSquare.getPid(), PID);
    }
}