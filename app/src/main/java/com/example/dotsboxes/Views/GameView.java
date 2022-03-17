package com.example.dotsboxes.Views;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.example.dotsboxes.Components.Dot;
import com.example.dotsboxes.Components.Line;
import com.example.dotsboxes.Components.Square;
import com.example.dotsboxes.GameState;
import com.example.dotsboxes.MainActivity;
import com.example.dotsboxes.Components.Player;
import com.example.dotsboxes.R;
import java.util.HashMap;

public class GameView extends View {

    private static final int UNFILLED_SQUARE_COLOR = Color.TRANSPARENT;
    private static final int UNFILLED_SQUARE_OPACITY = 0;
    private static final int FILLED_SQUARE_OPACITY = 100;

    private static final int DOT_COLOR = Color.WHITE;
    private static final int DOT_OPACITY = 255;
    private static final int DOT_RADIUS = 20;
    private static final int BITMAP_WIDTH = 30;

    private static final int UNSELECTED_LINE_COLOR = Color.BLACK;
    private static final int UNSELECTED_LINE_OPACITY = 50;
    private static final int SELECTED_LINE_OPACITY = 255;
    private static final int LINE_WIDTH = 12;
    private static final int LINE_OFFSET = 50;

    private static final int NUM_PLAYERS = 2;
    private static final int BOARD_WIDTH = 4;
    private static final int BOARD_HEIGHT = 4;

    private Paint paint;

    private GameState gameState;

    public GameView(Context context) {
        super(context);
        init(null);
    }

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public GameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public GameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    /** If we create a new instance of CustomView and is included in a new layout file for code optimization */
    private void init(@Nullable AttributeSet set) {

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        Square.setDefaultColor(UNFILLED_SQUARE_COLOR);
        Line.setDefaultColor(UNSELECTED_LINE_COLOR);

        Player[] players = new Player[NUM_PLAYERS];

        /** Temporarily replacing commented out code below to update line colors according settings (since only 2 player implemented) */
        HashMap<String, Integer> colorMap = new HashMap<String, Integer>() {{
            put("blue", getResources().getColor(R.color.bluePlayer));
            put("red", getResources().getColor(R.color.redPlayer));
            put("yellow", getResources().getColor(R.color.yellowPlayer));
            put("pink", getResources().getColor(R.color.pinkPlayer));
            put("green", getResources().getColor(R.color.greenPlayer));
            put("purple", getResources().getColor(R.color.purplePlayer));
        }};

        SharedPreferences sharedPreferences = MainActivity.getContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        String playerColor1 = sharedPreferences.getString("playerColor1", "");
        String playerColor2 = sharedPreferences.getString("playerColor2", "");
        players[0] = new Player("Player " + 1, colorMap.get(playerColor1));
        players[1] = new Player("Player " + 2, colorMap.get(playerColor2));
        /*******************************************************************************************/
//        int[] colors = new int[] {
//                getResources().getColor(R.color.redPlayer),
//                getResources().getColor(R.color.bluePlayer),
//                getResources().getColor(R.color.greenPlayer),
//                getResources().getColor(R.color.yellowPlayer),
//                getResources().getColor(R.color.purplePlayer),
//                getResources().getColor(R.color.orangePlayer),
//                getResources().getColor(R.color.pinkPlayer)
//        };
//        for (int i = 0 ; i < NUM_PLAYERS ; i++) {
//            players[i] = new Player("Player " + (i + 1), colors[i % (colors.length - 1)]);
//        }

        gameState = new GameState(players, BOARD_WIDTH, BOARD_HEIGHT);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawSquares(canvas);
        drawLines(canvas);
        drawDots(canvas);
    }

    private void drawSquares(Canvas canvas) {
        for (Square[] row : gameState.getSquares()) {
            for (Square square : row) {
                paint.setColor(square.getColor());
                if (square.isFilled()) {
                    paint.setAlpha(FILLED_SQUARE_OPACITY);
                } else {
                    paint.setAlpha(UNFILLED_SQUARE_OPACITY);
                }
                canvas.drawRect(
                        square.getX(),
                        square.getY(),
                        square.getX() + Square.getSize(),
                        square.getY() + Square.getSize(),
                        paint
                );
            }
        }
    }

    private void drawLines(Canvas canvas) {

        paint.setStrokeWidth(LINE_WIDTH);

        for (Line[] row : gameState.getHorizontalLines()) {
            for (Line line : row) {
                drawLine(line, canvas);
            }
        }

        for (Line[] row : gameState.getVerticalLines()) {
            for (Line line : row) {
                drawLine(line, canvas);
            }
        }
    }

    private void drawLine(Line line, Canvas canvas) {
        paint.setColor(line.getColor());
        if (line.isSelected()) {
            paint.setAlpha(SELECTED_LINE_OPACITY);
        } else {
            paint.setAlpha(UNSELECTED_LINE_OPACITY);
        }
        canvas.drawLine(line.getX1(), line.getY1(), line.getX2(), line.getY2(), paint);
    }

    private void drawDots(Canvas canvas) {
        paint.setColor(DOT_COLOR);
        paint.setAlpha(DOT_OPACITY);

        SharedPreferences sharedPreferences = MainActivity.getContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        String vertex = sharedPreferences.getString("vertex", "");
        Bitmap triangle = getBitmapFromVectorDrawable(MainActivity.getContext(), R.drawable.ic_triangle);
        Bitmap star = getBitmapFromVectorDrawable(MainActivity.getContext(), R.drawable.ic_star);
//        Bitmap cloud = getBitmapFromVectorDrawable(MainActivity.getContext(), R.drawable.ic_cloud);
//        Bitmap moon = getBitmapFromVectorDrawable(MainActivity.getContext(), R.drawable.ic_moon);
//        Bitmap sun = getBitmapFromVectorDrawable(MainActivity.getContext(), R.drawable.ic_sun);

        for (Dot[] row : gameState.getDots()) {
            for (Dot dot : row) {
                if (vertex.equals("dot")) {
                    canvas.drawCircle(dot.getX(), dot.getY(), DOT_RADIUS, paint);
                } else if (vertex.equals("triangle")) {
                    canvas.drawBitmap(triangle, dot.getX() - BITMAP_WIDTH, dot.getY() - BITMAP_WIDTH, paint);
                } else {
                    canvas.drawBitmap(star, dot.getX() - BITMAP_WIDTH, dot.getY() - BITMAP_WIDTH, paint);
                }
//              else if (vertex.equals("cloud")) {
//                  canvas.drawBitmap(cloud, dot.getX() - BITMAP_WIDTH, dot.getY() - BITMAP_WIDTH, paint);
//              } else if (vertex.equals("moon")) {
//                  canvas.drawBitmap(moon, dot.getX() - BITMAP_WIDTH, dot.getY() - BITMAP_WIDTH, paint);
//              } else (vertex.equals("sun")) {
//                  canvas.drawBitmap(sun, dot.getX() - BITMAP_WIDTH, dot.getY() - BITMAP_WIDTH, paint);
//              }
            }
        }
        paint.clearShadowLayer();
    }

    private Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, BITMAP_WIDTH * 2, BITMAP_WIDTH * 2, true);
        Canvas canvas = new Canvas(resizedBitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        bitmap.recycle(); // to avoid memory leaks
        return resizedBitmap;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && gameState.isAllowClick()) {
            double xPos = event.getX();
            double yPos = event.getY();
            if (findTappedLine(xPos, yPos)) {
                gameState.advanceTurn();
                this.postInvalidate(); // forces view to call onDraw
            }
        }
        return true;
    }

    private boolean findTappedLine(double xPos, double yPos) {

        Line[][] horizontalLines = gameState.getHorizontalLines();
        Line[][] verticalLines = gameState.getVerticalLines();

        for (int i = 0 ; i <= BOARD_HEIGHT ; i++) {
            for (int j = 0 ; j <= BOARD_WIDTH ; j++) {
                if (i < BOARD_HEIGHT) {
                    Line verticalLine = verticalLines[i][j];
                    boolean lineTapped = yPos >= verticalLine.getY1() + LINE_OFFSET
                            && yPos <= verticalLine.getY2() - LINE_OFFSET
                            && xPos >= verticalLine.getX1() - LINE_OFFSET
                            && xPos <= verticalLine.getX2() + LINE_OFFSET;
                    if (lineTapped) {
                        if (verticalLine.isSelected()) {
                            return false;
                        }
                        verticalLine.selectLine(gameState.getCurrentPlayer());
                        return true;
                    }
                }
                if (j < BOARD_WIDTH) {
                    Line horizontalLine = horizontalLines[i][j];
                    boolean lineTapped = xPos >= horizontalLine.getX1() + LINE_OFFSET
                            && xPos <= horizontalLine.getX2() - LINE_OFFSET
                            && yPos >= horizontalLine.getY1() - LINE_OFFSET
                            && yPos <= horizontalLine.getY2() + LINE_OFFSET;
                    if (lineTapped) {
                        if (horizontalLine.isSelected()) {
                            return false;
                        }
                        horizontalLine.selectLine(gameState.getCurrentPlayer());
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void setUpReferences(TextView p1Score,
                                TextView p2Score,
                                TextView p1Name,
                                TextView p2Name,
                                TextView statusDisplay,
                                Button btnPlayAgain) {
        gameState.setUpReferences(
                this,
                p1Score,
                p2Score,
                p1Name,
                p2Name,
                statusDisplay,
                btnPlayAgain
        );
    }

    public void setPlayComputer(boolean playComputer) {
        gameState.setPlayComputer(playComputer);
    }

    public void resetGame() {
        gameState.resetGame();
        this.postInvalidate();
    }

}