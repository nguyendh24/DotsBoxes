package com.startechies.dotsboxes;

import androidx.annotation.NonNull;

import com.startechies.dotsboxes.Components.Dot;
import com.startechies.dotsboxes.Components.Line;
import com.startechies.dotsboxes.Components.Player;
import com.startechies.dotsboxes.Components.Square;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class GameState {

    private static final String HORIZONTAL_LINE = "H";
    private static final String VERTICAL_LINE = "V";
    private static final String SCORE_SEPARATOR = "#";
    private static final String ENTRY_SEPARATOR = "&";
    private static final String MOVES_SEPARATOR = "%";
    private static final String DATA_SEPARATOR = "<>";

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

    public static GameState getInstance(String str) {
        if (str == null) {
            return getInstance();
        }
        instance = new GameState(str);
        return instance;
    }

    private GameState() {

        int p1Color = PrefUtility.getColor(PrefUtility.DEFAULT_PLAYER_COLOR_1);
        int p2Color = PrefUtility.getColor(PrefUtility.DEFAULT_PLAYER_COLOR_2);

        players = new Player[] {
                new Player(0, "Player 1", p1Color),
                new Player(1, "Player 2", p2Color)
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
                int maxLines = 2;
                if (i > 0 && i < boardHeight) maxLines++;
                if (j > 0 && j < boardHeight) maxLines++;
                dots[i][j] = new Dot(x, y, maxLines);
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
                    vLine.addAdjacentDot(dots[i][j]);
                    vLine.addAdjacentDot(dots[i+1][j]);
                    if (j > 0) {
                        vLine.addAdjacentSquare(squares[i][j-1]);
                    }
                    if (j < boardWidth) {
                        vLine.addAdjacentSquare(squares[i][j]);
                    }
                }
                if (j < boardWidth) {
                    Line hLine = horizontalLines[i][j];
                    hLine.addAdjacentDot(dots[i][j]);
                    hLine.addAdjacentDot(dots[i][j+1]);
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

    public int getSpacing() {
        return spacing;
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
        if (gameOver()) {
            if (getP1Score() > getP2Score()) {
                return getP1Name() + " wins!";
            } else if (getP1Score() < getP2Score()) {
                return getP2Name() + " wins!";
            } else {
                return "Game is a tie!";
            }
        } else {
            return "Game not over yet!";
        }
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
        for (Dot[] row : dots) {
            for (Dot dot : row) {
                dot.reset();
            }
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

    public int getWinnerID() {
        if (gameOver()) {
            if (getP1Score() > getP2Score()) {
                return players[0].getPid();
            } else if (getP1Score() < getP2Score()) {
                return players[1].getPid();
            }
        }
        return -1;
    }

    @NonNull
    public String toString() {

        ArrayList<String> moves = new ArrayList<>();

        for (int row = 0 ; row < horizontalLines.length ; row++) {
            for (int col = 0 ; col < horizontalLines[row].length ; col++) {
                Line line = horizontalLines[row][col];
                if (line.isSelected()) {
                    int player = line.getPid();
                    String[] move = {
                            HORIZONTAL_LINE,
                            Integer.toString(row),
                            Integer.toString(col),
                            Integer.toString(player)
                    };
                    moves.add(Arrays.stream(move).collect(Collectors.joining(ENTRY_SEPARATOR)));
                }
            }
        }

        for (int row = 0 ; row < verticalLines.length ; row++) {
            for (int col = 0 ; col < verticalLines[row].length ; col++) {
                Line line = verticalLines[row][col];
                if (line.isSelected()) {
                    int player = line.getPid();
                    String[] move = {
                            VERTICAL_LINE,
                            Integer.toString(row),
                            Integer.toString(col),
                            Integer.toString(player)
                    };
                    moves.add(Arrays.stream(move).collect(Collectors.joining(ENTRY_SEPARATOR)));
                }
            }
        }

        ArrayList<String> filledSquares = new ArrayList<>();

        for (int row = 0 ; row < squares.length ; row++) {
            for (int col = 0 ; col < squares[row].length ; col++) {
                Square square = squares[row][col];
                if (square.isFilled()) {
                    int player = square.getPid();
                    String[] filledSquare = {
                            Integer.toString(row),
                            Integer.toString(col),
                            Integer.toString(player)
                    };
                    filledSquares.add(Arrays.stream(filledSquare).collect(Collectors.joining(ENTRY_SEPARATOR)));
                }
            }
        }

        String[] data = new String[] {
                Integer.toString(boardWidth),
                Integer.toString(turn),
                moves.stream().collect(Collectors.joining(MOVES_SEPARATOR)),
                filledSquares.stream().collect(Collectors.joining(MOVES_SEPARATOR)),
                players[0].getScore() + SCORE_SEPARATOR + players[1].getScore()
        };

        return Arrays.stream(data).collect(Collectors.joining(DATA_SEPARATOR));
    }

    private GameState(String str) {
        this();

        String[] data = str.split(DATA_SEPARATOR);
        setUpBoard(Integer.parseInt(data[0]));
        turn = Integer.parseInt(data[1]);
        String[] moves = data[2].split(MOVES_SEPARATOR);

        for (String move : moves) {
            String[] moveData = move.split(ENTRY_SEPARATOR);
            String orientation = moveData[0];
            int row = Integer.parseInt(moveData[1]);
            int col = Integer.parseInt(moveData[2]);
            int player = Integer.parseInt(moveData[3]);
            if (HORIZONTAL_LINE.equals(orientation)) {
                horizontalLines[row][col].selectLine(players[player]);
            } else if (VERTICAL_LINE.equals(orientation)) {
                verticalLines[row][col].selectLine(players[player]);
            }
        }

        if (data[3].length() > 0) {
            String[] filledSquares = data[3].split(MOVES_SEPARATOR);
            for (String filledSquare : filledSquares) {
                String[] squareData = filledSquare.split(ENTRY_SEPARATOR);
                int row = Integer.parseInt(squareData[0]);
                int col = Integer.parseInt(squareData[1]);
                int player = Integer.parseInt(squareData[2]);
                squares[row][col].setPlayer(players[player]);
            }
        }

        String[] scores = data[4].split(SCORE_SEPARATOR);
        players[0].setScore(Integer.parseInt(scores[0]));
        players[1].setScore(Integer.parseInt(scores[1]));
    }

    public static void setNull() {
        instance = null;
    }
}
