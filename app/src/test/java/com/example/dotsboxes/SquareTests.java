package com.example.dotsboxes;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import android.graphics.Color;

import com.example.dotsboxes.Components.Player;
import com.example.dotsboxes.Components.Square;

public class SquareTests {

    private final int PID = 1234;
    private final int PLAYER_COLOR = 5678;

    private Square square;
    private Player player;

    @Before
    public void init() {
        square = new Square(2, 2);
        player = new Player(PID, "player", PLAYER_COLOR);
    }

    // This first section of tests represents tests from
    // Test Report, Part 2: Base Choice Coverage

    // Base case: A1 (sides < 4), B1 (sides > 0)
    @Test
    public void testA1B1() {
        square.addSide(player);
        assertFalse(square.isFilled());
        assertEquals(square.getColor(), Color.TRANSPARENT);
        assertEquals(square.getPid(), -1);
    }

    // A2 (sides == 4), B1 (sides > 0)
    @Test
    public void testA2B1() {
        square.addSide(player);
        square.addSide(player);
        square.addSide(player);
        square.addSide(player);
        assertTrue(square.isFilled());
        assertEquals(square.getColor(), PLAYER_COLOR);
        assertEquals(square.getPid(), PID);
    }

    // A1 (sides < 4), B2 (sides == 0)
    @Test
    public void testA1B2() {
        assertFalse(square.isFilled());
        assertEquals(square.getColor(), Color.TRANSPARENT);
        assertEquals(square.getPid(), -1);
    }


    // This second section of tests represents tests from
    // Test Report, Part 3: Mutation Coverage

    @Test
    public void testSize() {
        Square.setSize(10);
        assertEquals(Square.getSize(), 10, 0);
    }

    @Test
    public void testGetXGetY() {
        assertEquals(square.getX(), 2, 0);
        assertEquals(square.getY(), 2, 0);
    }

    @Test
    public void testPlayerScore() {
        square.addSide(player);
        square.addSide(player);
        square.addSide(player);
        square.addSide(player);
        assertEquals(player.getScore(), 1);
    }
}