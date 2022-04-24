package com.example.dotsboxes;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.example.dotsboxes.Components.Dot;

public class DotTests {

    private Dot dot;

    @Before
    public void init() {
        int x = 0;
        int y = 0;
        int maxLines = 4;
        dot = new Dot(x, y, maxLines);
    }

    /* This first section of tests represents tests from
     * Test Report, Part 2: Base Choice Coverage */

    // Base case: A1 (numLines < maxLines), B1 (numLines > 0)
    @Test
    public void testA1B1() {
        dot.addLine();
        assertTrue(dot.hasOpenLines());
    }

    // A2 (numLines == maxLines), B1 (numLines > 0)
    @Test
    public void testA2B1() {
        dot.addLine();
        dot.addLine();
        dot.addLine();
        dot.addLine();
        assertFalse(dot.hasOpenLines());
    }

    // A1 (numLines < maxLines), B2 (numLines == 0)
    @Test
    public void testA1B2() {
        assertTrue(dot.hasOpenLines());
    }
}