package com.example.dotsboxes;
import com.example.dotsboxes.Components.Dot;
import com.example.dotsboxes.Components.Line;
import com.example.dotsboxes.Components.Player;
import com.example.dotsboxes.Components.Square;
import com.google.gson.Gson;

import java.util.ArrayList;

public class GameState {

    private static final int COMPUTER_PLAYER = 1;
    private static final int NUM_PLAYERS = 2;
    private static GameState instance;

    private int boardWidth;
    private int boardHeight;
    private int spacing;

    private final Player[] players;
    private Dot[][] dots;
    private Line[][] horizontalLines;
    private Line[][] verticalLines;
    private Square[][] squares;
    private int turn;
    private boolean playComputer;
    private boolean allowClick;

    public static GameState getInstance() {
        if (instance == null) {
            instance = new GameState();
        }
        return instance;
    }

    public static GameState getInstance(String json) {
        if (json == null) {
            return getInstance();
        }
        instance = new Gson().fromJson(json, GameState.class);
        return instance;
    }

    private GameState() {

        int p1Color = PrefUtility.getColor(PrefUtility.DEFAULT_PLAYER_COLOR_1);
        int p2Color = PrefUtility.getColor(PrefUtility.DEFAULT_PLAYER_COLOR_2);

        players = new Player[] {
                new Player("Player 1", p1Color),
                new Player("Player 2", p2Color)
        };

        turn = 0;

        allowClick = true;

        setUpBoard(PrefUtility.DEFAULT_BOARD_SIZE);
    }

    public void setUpBoard(int size) {
        if (boardHeight != size || boardWidth != size) {
            boardWidth = size;
            boardHeight = size;

            spacing = (int) MainActivity.deviceWidth / (boardWidth + 2);
            Square.setSize(spacing);

            dots = new Dot[boardHeight + 1][boardWidth + 1];
            horizontalLines = new Line[boardHeight + 1][boardWidth];
            verticalLines = new Line[boardHeight][boardWidth + 1];
            squares = new Square[boardHeight][boardWidth];

            createGameElements();
            addAdjacencyReferences();
        }
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

    public void advanceTurn() {
        if (players[turn].isGoAgain()) {
            players[turn].resetGoAgain();
        } else {
            turn = (turn + 1) % NUM_PLAYERS;
            allowClick = !playComputer || turn != COMPUTER_PLAYER;
        }
    }

    public boolean isComputerTurn() {
        return playComputer && turn == COMPUTER_PLAYER && !gameOver();
    }

    public void computerTurn() {
        makeRandomMove();
        advanceTurn();
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

    public int getTurn() {
        return turn;
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

    public boolean gameOver() {
        for (Square[] row : squares) {
            for (Square square : row) {
                if (!square.isFilled()) {
                    return false;
                }
            }
        }
        return true;
    }

    public String getResultsString() {
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

    public void setP1Name(String name) {
        players[0].setName(name);
    }

    public void setP2Name(String name) {
        players[1].setName(name);
    }

    public void setP1Color(int color) {
        players[0].setColor(color);
    }

    public void setP2Color(int color) {
        players[1].setColor(color);
    }

    public boolean isAllowClick() {
        return allowClick;
    }

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
    }

    public int getP1Score() {
        return players[0].getScore();
    }

    public int getP2Score() {
        return players[1].getScore();
    }

    public String getP1Name() {
        return players[0].getName();
    }

    public String getP2Name() {
        return players[1].getName();
    }

}
