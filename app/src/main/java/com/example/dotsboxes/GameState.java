package com.example.dotsboxes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.dotsboxes.Components.Dot;
import com.example.dotsboxes.Components.Line;
import com.example.dotsboxes.Components.Square;
import com.example.dotsboxes.Fragments.HomeFragment;
import com.example.dotsboxes.Fragments.SettingsFragment;
import com.example.dotsboxes.Views.GameView;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameState {

    private static final int COMPUTER_PLAYER = 1;

    private GameView view;

    private TextView statusDisplay;
    private TextView p1Score;
    private TextView p2Score;
    private Button btnPlayAgain;

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
    private boolean allowClick;

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

        allowClick = true;

        createGameElements();
        addAdjacencyReferences();
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

    private void addAdjacencyReferences() {
        for (int i = 0 ; i <= boardHeight ; i++) {
            for (int j = 0 ; j <= boardWidth ; j++) {
                if (i < boardHeight) {
                    Line vLine = verticalLines[i][j];
                    if (j > 0) {
                        vLine.addAdjacentSquare(squares[i][j-1]);
                    }
                    if (j < boardWidth) {
                        vLine.addAdjacentSquare(squares[i][j]);
                    }
                }
                if (j < boardWidth) {
                    Line hLine = horizontalLines[i][j];
                    if (i > 0) {
                        hLine.addAdjacentSquare(squares[i-1][j]);
                    }
                    if (i < boardHeight) {
                        hLine.addAdjacentSquare(squares[i][j]);
                    }
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
            allowClick = !playComputer || turn != COMPUTER_PLAYER;
        }
        if (p1Score != null && p2Score != null) {
            p1Score.setText(Integer.toString(players[0].getScore()));
            p2Score.setText(Integer.toString(players[1].getScore()));
        }
        if (statusDisplay != null && gameOver()) {
            statusDisplay.setText(getResultsString());
            btnPlayAgain.setVisibility(View.VISIBLE);
        } else if (statusDisplay != null) {
            statusDisplay.setText(players[turn].getName() + "'s Turn");
        }
        if (playComputer && turn == COMPUTER_PLAYER && !gameOver()) {
            computerTurn();
        }
    }

    private void computerTurn() {
        // Short delay to improve user experience
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            makeRandomMove();
            view.postInvalidate();
            advanceTurn();
        }, 500);
    }

    private void makeRandomMove() {
        ArrayList<Line> unfilledLines = new ArrayList<>();
        for (Line[] row : horizontalLines) {
            for (Line line : row) {
                if (!line.isSelected()) {
                    unfilledLines.add(line);
                }
            }
        }
        for (Line[] row : verticalLines) {
            for (Line line : row) {
                if (!line.isSelected()) {
                    unfilledLines.add(line);
                }
            }
        }
        int move = (int) (Math.random() * unfilledLines.size());
        unfilledLines.get(move).selectLine(players[COMPUTER_PLAYER]);
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
    public void setUpReferences(GameView view,
                                TextView p1Score,
                                TextView p2Score,
                                TextView p1Name,
                                TextView p2Name,
                                TextView statusDisplay,
                                Button btnPlayAgain) {
        this.view = view;
        statusDisplay.setText(players[0].getName() + "'s Turn");
        this.statusDisplay = statusDisplay;
        this.p1Score = p1Score;
        this.p2Score = p2Score;
        this.btnPlayAgain = btnPlayAgain;
        p1Name.setText(HomeFragment.getTvPlayerName());
        p2Name.setText(HomeFragment.getTvPlayerName() + "'s Friend");
        setTvPlayerNames(p1Name, p2Name);
    }

    private void setTvPlayerNames(TextView p1Name, TextView p2Name) {
        SharedPreferences sharedPreferences = MainActivity.getContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        String playerColor = sharedPreferences.getString("playerColor", "");
        if (playerColor.equals("RB")) {
            p1Name.setTextColor(ContextCompat.getColor(MainActivity.getContext() , R.color.redPlayer));
            p2Name.setTextColor(ContextCompat.getColor(MainActivity.getContext() , R.color.bluePlayer));
        } else if (playerColor.equals("PG")) {
            p1Name.setTextColor(ContextCompat.getColor(MainActivity.getContext() , R.color.purplePlayer));
            p2Name.setTextColor(ContextCompat.getColor(MainActivity.getContext() , R.color.greenPlayer));
        } else {
            p1Name.setTextColor(ContextCompat.getColor(MainActivity.getContext() , R.color.pinkPlayer));
            p2Name.setTextColor(ContextCompat.getColor(MainActivity.getContext() , R.color.yellowPlayer));
        }
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
            players[COMPUTER_PLAYER].setName("Computer");
        }
    }

    public boolean isAllowClick() {
        return allowClick;
    }

    @SuppressLint("SetTextI18n")
    public void resetGame() {
        turn = 0;
        allowClick = true;
        for (Player player : players) {
            player.resetGoAgain();
            player.resetScore();
        }
        for (Square[] row : squares) {
            for (Square square : row) {
                square.reset();
            }
        }
        for (Line[] row : horizontalLines) {
            for (Line line : row) {
                line.reset();
            }
        }
        for (Line[] row : verticalLines) {
            for (Line line : row) {
                line.reset();
            }
        }
        p1Score.setText(Integer.toString(players[0].getScore()));
        p2Score.setText(Integer.toString(players[1].getScore()));
        statusDisplay.setText(players[turn].getName() + "'s Turn");
        btnPlayAgain.setVisibility(View.INVISIBLE);
    }
}
