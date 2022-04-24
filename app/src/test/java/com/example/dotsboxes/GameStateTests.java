package com.example.dotsboxes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.dotsboxes.Components.Dot;
import com.example.dotsboxes.Components.Line;
import com.example.dotsboxes.Components.Square;

import org.junit.Before;
import org.junit.Test;

public class GameStateTests {

    private final String IN_PROGRESS_GAME = "4<>1<>H&0&1&1%H&1&1&0%H&1&3&1%H&2&1&0%H&2&2&1%H&2&3&1%H&3&2&0%H&3&3&0%V&0&0&0%V&1&1&0%V&1&2&1%V&1&3&1%V&1&4&0%V&2&2&0%V&2&3&1%V&3&1&1%V&3&3&1%V&3&4&0<>1&1&0%1&3&1%2&2&1<>1#2";
    private final String COMPLETED_GAME_TIE = "4<>0<>H&0&0&1%H&0&1&0%H&0&2&0%H&0&3&0%H&1&0&0%H&1&1&1%H&1&2&1%H&1&3&1%H&2&0&0%H&2&1&1%H&2&2&1%H&2&3&1%H&3&0&0%H&3&1&0%H&3&2&1%H&3&3&0%H&4&0&1%H&4&1&1%H&4&2&1%H&4&3&0%V&0&0&0%V&0&1&1%V&0&2&0%V&0&3&1%V&0&4&1%V&1&0&1%V&1&1&0%V&1&2&1%V&1&3&0%V&1&4&1%V&2&0&0%V&2&1&1%V&2&2&1%V&2&3&1%V&2&4&0%V&3&0&0%V&3&1&0%V&3&2&0%V&3&3&0%V&3&4&0<>0&0&0%0&1&1%0&2&1%0&3&1%1&0&0%1&1&1%1&2&1%1&3&1%2&0&0%2&1&1%2&2&1%2&3&0%3&0&0%3&1&0%3&2&0%3&3&0<>8#8";
    private final String IN_PROGRESS_TIE = "4<>0<>H&0&3&1%H&1&1&1%H&1&2&1%H&2&0&1%H&2&1&1%H&2&2&0%H&2&3&0%H&3&1&0%H&3&2&0%H&4&0&0%V&0&0&0%V&0&1&1%V&0&2&1%V&0&3&0%V&0&4&1%V&1&0&1%V&1&1&0%V&1&2&0%V&1&3&1%V&1&4&1%V&2&0&1%V&2&1&0%V&2&2&0%V&2&3&1%V&2&4&0%V&3&0&0%V&3&1&1%V&3&2&1%V&3&3&0%V&3&4&0<>1&1&1%1&2&0%2&1&1%2&2&0<>2#2";
    private final String COMPLETED_P1_WINNING = "4<>0<>H&0&0&1%H&0&1&1%H&0&2&1%H&0&3&0%H&1&0&0%H&1&1&1%H&1&2&1%H&1&3&0%H&2&0&0%H&2&1&1%H&2&2&0%H&2&3&0%H&3&0&0%H&3&1&0%H&3&2&1%H&3&3&1%H&4&0&0%H&4&1&1%H&4&2&1%H&4&3&0%V&0&0&0%V&0&1&1%V&0&2&0%V&0&3&0%V&0&4&1%V&1&0&1%V&1&1&0%V&1&2&1%V&1&3&0%V&1&4&0%V&2&0&0%V&2&1&0%V&2&2&0%V&2&3&0%V&2&4&1%V&3&0&1%V&3&1&0%V&3&2&1%V&3&3&0%V&3&4&1<>0&0&0%0&1&1%0&2&1%0&3&0%1&0&0%1&1&1%1&2&1%1&3&0%2&0&0%2&1&0%2&2&0%2&3&0%3&0&0%3&1&1%3&2&1%3&3&0<>10#6";
    private final String COMPLETED_P2_WINNING = "4<>1<>H&0&0&0%H&0&1&0%H&0&2&0%H&0&3&1%H&1&0&0%H&1&1&1%H&1&2&1%H&1&3&1%H&2&0&0%H&2&1&1%H&2&2&1%H&2&3&1%H&3&0&0%H&3&1&1%H&3&2&0%H&3&3&1%H&4&0&1%H&4&1&1%H&4&2&1%H&4&3&0%V&0&0&1%V&0&1&1%V&0&2&0%V&0&3&0%V&0&4&0%V&1&0&1%V&1&1&1%V&1&2&1%V&1&3&0%V&1&4&0%V&2&0&1%V&2&1&1%V&2&2&0%V&2&3&1%V&2&4&1%V&3&0&0%V&3&1&1%V&3&2&1%V&3&3&0%V&3&4&1<>0&0&1%0&1&0%0&2&0%0&3&1%1&0&1%1&1&1%1&2&1%1&3&1%2&0&1%2&1&1%2&2&1%2&3&1%3&0&1%3&1&1%3&2&0%3&3&0<>4#12";

    private GameState gameState;
    Dot[][] dots;
    Line[][] horizontalLines;
    Line[][] verticalLines;
    Square[][] squares;

    @Before
    public void init() {
        GameState.setNull();
        gameState = GameState.getInstance();
        saveMatrices();
    }

    // Helper method
    private void saveMatrices() {
        dots = gameState.getDots();
        horizontalLines = gameState.getHorizontalLines();
        verticalLines = gameState.getVerticalLines();
        squares = gameState.getSquares();
    }

    // Helper method
    private void playGame(Line[] linesToSelect) {
        for (Line line : linesToSelect) {
            line.selectLine(gameState.getCurrentPlayer());
            gameState.advanceTurn();
        }
    }


    // This first section of tests represents tests from
    // Test Report, Part 2: Base Choice Coverage


    // GAME CREATION TESTS (Characteristics A - C)
    // Each test checks on the size and state of the matrices
    // which store information about Dots, Lines, and Squares.

    // Helper method
    private void testSizes(int size) {
        assertEquals(dots.length, size + 1);
        assertEquals(horizontalLines.length, size + 1);
        assertEquals(verticalLines.length, size);
        assertEquals(squares.length, size);
        assertEquals(dots[0].length, size + 1);
        assertEquals(horizontalLines[0].length, size);
        assertEquals(verticalLines[0].length, size + 1);
        assertEquals(squares[0].length, size);
    }

    // Helper method
    private void testElements(int expectedSaturatedDots,
                              int expectedSelectedLines,
                              int expectedFilledSquares) {
        int actualSaturatedDots = 0;
        int actualSelectedLines = 0;
        int actualFilledSquares = 0;
        for (Dot[] row : dots) {
            for (Dot dot : row) {
                if (!dot.hasOpenLines()) {
                    actualSaturatedDots++;
                }
            }
        }
        for (Line[] row : horizontalLines) {
            for (Line line : row) {
                if (line.isSelected()) {
                    actualSelectedLines++;
                }
            }
        }
        for (Line[] row : verticalLines) {
            for (Line line : row) {
                if (line.isSelected()) {
                    actualSelectedLines++;
                }
            }
        }
        for (Square[] row : squares) {
            for (Square square : row) {
                if (square.isFilled()) {
                    actualFilledSquares++;
                }
            }
        }
        assertEquals(expectedSaturatedDots, actualSaturatedDots);
        assertEquals(expectedSelectedLines, actualSelectedLines);
        assertEquals(expectedFilledSquares, actualFilledSquares);
    }

    // Base case: A1 (No arg constructor), B1 (size == 4), C1 (empty board)
    @Test
    public void testA1B1C1() {
        testSizes(4);
        testElements(0, 0, 0);
    }

    // A2 (resetGame() called, B1 (size == 4), C1 (empty board)
    @Test
    public void testA2B1C1() {
        gameState = GameState.getInstance(IN_PROGRESS_GAME);
        gameState.resetGame();
        saveMatrices();
        testSizes(4);
        testElements(0, 0, 0);
    }

    // A3 (setUpBoard() given new size), B1 (size == 4), C1 (empty board)
    @Test
    public void testA3B1C1() {
        gameState.setUpBoard(6);
        gameState.setUpBoard(4);
        saveMatrices();
        testSizes(4);
        testElements(0, 0, 0);
    }

    // A4 (String constructor), B1 (size == 4), C2 (game started)
    @Test
    public void testA4B1C2() {
        gameState = GameState.getInstance(IN_PROGRESS_GAME);
        saveMatrices();
        testSizes(4);
        testElements(3, 18, 3);
    }

    // A3 (setUpBoard() given new size), B2 (size == 5), C1 (empty board)
    @Test
    public void testA3B2C1() {
        gameState.setUpBoard(5);
        saveMatrices();
        testSizes(5);
        testElements(0, 0, 0);
    }

    // A3 (setUpBoard() given new size), B3 (size == 6), C1 (empty board)
    @Test
    public void testA3B3C1() {
        gameState.setUpBoard(6);
        saveMatrices();
        testSizes(6);
        testElements(0, 0, 0);
    }

    // A1 (No arg constructor), B1 (size == 4), C2 (gameStarted)
    @Test
    public void testA1B1C2() {
        playGame(new Line[] {
                verticalLines[1][1],
                horizontalLines[2][1],
                verticalLines[2][1],
                horizontalLines[2][0],
                horizontalLines[1][1],
                verticalLines[1][2]
        });
        testSizes(4);
        testElements(1, 6, 1);
    }


    // GAMEPLAY TESTS (Characteristics D - G)
    // Each test checks on whether it's the computer's turn,
    // whether the user is allowed to make a move, whether
    // scoring has been recorded, and who is listed as the current
    // player and current turn.

    // Base case: D1 (playComputer == FALSE), E1 (some squares drawn),
    // F1 (Player 1 went last), G1 (no square created last turn)
    @Test
    public void testD1E1F1G1() {
        playGame(new Line[] {
                verticalLines[1][1],
                horizontalLines[2][1],
                verticalLines[2][1],
                horizontalLines[2][0],
                horizontalLines[1][1],
                verticalLines[1][2],
                horizontalLines[1][2],
                verticalLines[1][3]
        });
        assertFalse(gameState.isComputerTurn());
        assertTrue(gameState.isAllowClick());
        assertTrue(gameState.getP1Score() > 0 || gameState.getP2Score() > 0);
        assertEquals(gameState.getCurrentPlayer().getPid(), 1);
        assertEquals(gameState.getTurn(), 1);
    }

    // D2 (playComputer == TRUE), E1 (some squares drawn),
    // F1 (Player 1 went last), G1 (no square created last turn)
    @Test
    public void testD2E1F1G1() {
        gameState.setPlayComputer(true);
        playGame(new Line[] {
                verticalLines[1][1],
                horizontalLines[2][1],
                verticalLines[2][1],
                horizontalLines[2][0],
                horizontalLines[1][1],
                verticalLines[1][2],
                horizontalLines[1][2],
                verticalLines[1][3]
        });
        assertTrue(gameState.isComputerTurn());
        assertFalse(gameState.isAllowClick());
        assertTrue(gameState.getP1Score() > 0 || gameState.getP2Score() > 0);
        assertEquals(gameState.getCurrentPlayer().getPid(), 1);
        assertEquals(gameState.getTurn(), 1);
    }

    // D1 (playComputer == FALSE), E2 (no squares yet),
    // F1 (Player 1 went last), G1 (no square created last turn)
    @Test
    public void testD1E2F1G1() {
        playGame(new Line[] {
                horizontalLines[3][0],
                horizontalLines[0][3],
                horizontalLines[4][3],
                verticalLines[1][3],
                verticalLines[1][4]
        });
        assertFalse(gameState.isComputerTurn());
        assertTrue(gameState.isAllowClick());
        assertFalse(gameState.getP1Score() > 0 || gameState.getP2Score() > 0);
        assertEquals(gameState.getCurrentPlayer().getPid(), 1);
        assertEquals(gameState.getTurn(), 1);
    }

    // D1 (playComputer == FALSE), E1 (some squares drawn),
    // F2 (Player 2 went last), G1 (no square created last turn)
    @Test
    public void testD1E1F2G1() {
        playGame(new Line[] {
                verticalLines[1][2],
                horizontalLines[1][2],
                verticalLines[1][3],
                horizontalLines[2][3],
                horizontalLines[2][2],
                verticalLines[2][2],
                horizontalLines[4][3]
        });
        assertFalse(gameState.isComputerTurn());
        assertTrue(gameState.isAllowClick());
        assertTrue(gameState.getP1Score() > 0 || gameState.getP2Score() > 0);
        assertEquals(gameState.getCurrentPlayer().getPid(), 0);
        assertEquals(gameState.getTurn(), 0);
    }

    // D1 (playComputer == FALSE), E2 (no squares yet),
    // F3 (no turn yet), G1 (no square created last turn)
    @Test
    public void testD1E2F3G1() {
        assertFalse(gameState.isComputerTurn());
        assertTrue(gameState.isAllowClick());
        assertFalse(gameState.getP1Score() > 0 || gameState.getP2Score() > 0);
        assertEquals(gameState.getCurrentPlayer().getPid(), 0);
        assertEquals(gameState.getTurn(), 0);
    }

    // D1 (playComputer == FALSE), E1 (some squares drawn),
    // F1 (Player 1 went last), G2 (square created last turn)
    @Test
    public void testD1E1F1G2() {
        playGame(new Line[] {
                verticalLines[1][1],
                horizontalLines[2][1],
                horizontalLines[2][2],
                verticalLines[1][3],
                verticalLines[1][2],
                verticalLines[1][4],
                horizontalLines[1][1],
                horizontalLines[1][2]
        });
        assertFalse(gameState.isComputerTurn());
        assertTrue(gameState.isAllowClick());
        assertTrue(gameState.getP1Score() > 0 || gameState.getP2Score() > 0);
        assertEquals(gameState.getCurrentPlayer().getPid(), 0);
        assertEquals(gameState.getTurn(), 0);
    }


    // ENDGAME TESTS (Characteristics H & I)
    // Each test checks whether gameState recognizes that the
    // game is over, how the two player scores relate to each other,
    // what the resultsString says, and what the winner ID is.

    // Base case: H1 (game completed), I1 (tie)
    @Test
    public void testH1I1() {
        gameState = GameState.getInstance(COMPLETED_GAME_TIE);
        assertTrue(gameState.gameOver());
        assertEquals(gameState.getP1Score(), gameState.getP2Score());
        assertEquals(gameState.getResultsString(), "Game is a tie!");
        assertEquals(gameState.getWinnerID(), -1);
    }

    // Base case: H2 (not completed), I1 (tie)
    @Test
    public void testH2I1() {
        gameState = GameState.getInstance(IN_PROGRESS_TIE);
        assertFalse(gameState.gameOver());
        assertEquals(gameState.getP1Score(), gameState.getP2Score());
        assertEquals(gameState.getResultsString(), "Game not over yet!");
        assertEquals(gameState.getWinnerID(), -1);
    }

    // H1 (game completed), I2 (P1 winning)
    @Test
    public void testH1I2() {
        gameState = GameState.getInstance(COMPLETED_P1_WINNING);
        assertTrue(gameState.gameOver());
        assertTrue(gameState.getP1Score() > gameState.getP2Score());
        assertEquals(gameState.getResultsString(), "Player 1 wins!");
        assertEquals(gameState.getWinnerID(), 0);
    }

    // H1 (game completed), I3 (P2 winning)
    @Test
    public void testH1I3() {
        gameState = GameState.getInstance(COMPLETED_P2_WINNING);
        assertTrue(gameState.gameOver());
        assertTrue(gameState.getP1Score() < gameState.getP2Score());
        assertEquals(gameState.getResultsString(), "Player 2 wins!");
        assertEquals(gameState.getWinnerID(), 1);
    }

}
