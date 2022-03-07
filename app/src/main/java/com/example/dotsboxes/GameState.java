package com.example.dotsboxes;

import android.annotation.SuppressLint;
import android.widget.TextView;

import com.example.dotsboxes.Components.Dot;
import com.example.dotsboxes.Components.Line;
import com.example.dotsboxes.Components.Square;

public class GameState {

    private TextView statusDisplay;
    private TextView p1Score;
    private TextView p2Score;

    private final int numPlayers;
    private final int boardWidth;
    private final int boardHeight;
    private final int spacing;

    private final Player[] players;
    private final Dot[][] dots;
    private final Line[][] horizontalLines;
    private final Line[][] verticalLines;
    private final Square[][] squares;
    private int turn;
    private boolean playComputer;

    public GameState(Player[] players, int boardWidth, int boardHeight) {
        this.players = players;
        numPlayers = players.length;
        turn = 0;

        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        spacing = (int) MainActivity.deviceWidth / (boardWidth + 2);
        Square.setSize(spacing);

        dots = new Dot[boardHeight + 1][boardWidth + 1];
        horizontalLines = new Line[boardHeight + 1][boardWidth];
        verticalLines = new Line[boardHeight][boardWidth + 1];
        squares = new Square[boardHeight][boardWidth];

        createGameElements();
    }

    private void createGameElements() {
        for (int i = 0 ; i <= boardHeight ; i++) {
            for (int j = 0 ; j <= boardWidth ; j++) {
                int x = spacing * (j + 1);
                int y = spacing * (i + 1);
                dots[i][j] = new Dot(x, y);
                if (i < boardHeight) {
                    verticalLines[i][j] = new Line(x, y, x, y + spacing);
                }
                if (j < boardWidth) {
                    horizontalLines[i][j] = new Line(x, y, x + spacing, y);
                }
                if (i < boardHeight && j < boardWidth) {
                    squares[i][j] = new Square(x, y);
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    public void advanceTurn() {
        if (players[turn].isGoAgain()) {
            players[turn].resetGoAgain();
        } else {
            turn = (turn + 1) % numPlayers;
        }
        if (p1Score != null && p2Score != null) {
            p1Score.setText(Integer.toString(players[0].getScore()));
            p2Score.setText(Integer.toString(players[1].getScore()));
        }
        if (statusDisplay != null && gameOver()) {
            statusDisplay.setText(getResultsString());
        } else if (statusDisplay != null) {
            statusDisplay.setText(players[turn].getName() + "'s Turn");
        }
    }

    public Player getCurrentPlayer() {
        return players[turn];
    }


    public Dot[][] getDots() {
        return dots;
    }

    public Line[][] getHorizontalLines() {
        return horizontalLines;
    }

    public Line[][] getVerticalLines() {
        return verticalLines;
    }

    public Square[][] getSquares() {
        return squares;
    }

    @SuppressLint("SetTextI18n")
    public void setUpTextViews(TextView p1Score,
                               TextView p2Score,
                               TextView p1Name,
                               TextView p2Name,
                               TextView statusDisplay) {
        statusDisplay.setText(players[0].getName() + "'s Turn");
        this.statusDisplay = statusDisplay;
        this.p1Score = p1Score;
        this.p2Score = p2Score;
        p1Name.setText(players[0].getName());
        p1Name.setTextColor(players[0].getColor());
        p2Name.setText(players[1].getName());
        p2Name.setTextColor(players[1].getColor());
    }

    private boolean gameOver() {
        for (Square[] row : squares) {
            for (Square square : row) {
                if (!square.isFilled()) {
                    return false;
                }
            }
        }
        return true;
    }

    private String getResultsString() {
        int highScore = 0;
        int numWinners = 0;
        for (Player player : players) {
            if (player.getScore() > highScore) {
                highScore = player.getScore();
            }
        }
        for (Player player : players) {
            if (player.getScore() == highScore) {
                numWinners++;
            }
        }
        if (numWinners == 1) {
            for (Player player : players) {
                if (player.getScore() == highScore) {
                    return player.getName() + " wins!";
                }
            }
        }
        return "Game is a tie!";
    }

    public void setPlayComputer(boolean playComputer) {
        this.playComputer = playComputer;
        if (playComputer) {
            players[1].setName("Computer");
        }
    }
}
