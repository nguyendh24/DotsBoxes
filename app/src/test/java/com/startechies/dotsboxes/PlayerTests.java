package com.startechies.dotsboxes;

import com.startechies.dotsboxes.Components.Player;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTests {
    // This entire class represents tests from
    // Test Report, Part 3: Mutation Coverage

    private final int PID = 1234;
    private final String PLAYER_NAME = "player";
    private final int PLAYER_COLOR = 5678;

    private Player player;

    @Before
    public void init() {
        player = new Player(PID, PLAYER_NAME, PLAYER_COLOR);
    }

    @Test
    public void testInitialValues() {
        assertEquals(player.getPid(), PID);
        assertEquals(player.getName(), PLAYER_NAME);
        assertEquals(player.getColor(), PLAYER_COLOR);
    }

    @Test
    public void testIncrementScore() {
        assertEquals(player.getScore(), 0);
        assertFalse(player.isGoAgain());
        player.incrementScore();
        assertEquals(player.getScore(), 1);
        assertTrue(player.isGoAgain());
    }
}
