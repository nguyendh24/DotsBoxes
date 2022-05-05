package com.startechies.dotsboxes.Views;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.startechies.dotsboxes.PrefUtility;
import com.startechies.dotsboxes.Components.Dot;
import com.startechies.dotsboxes.Components.Line;
import com.startechies.dotsboxes.Components.Square;
import com.startechies.dotsboxes.GameState;
import com.startechies.dotsboxes.R;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import nl.dionsegijn.konfetti.core.PartyFactory;
import nl.dionsegijn.konfetti.core.Position;
import nl.dionsegijn.konfetti.core.emitter.Emitter;
import nl.dionsegijn.konfetti.core.emitter.EmitterConfig;
import nl.dionsegijn.konfetti.core.models.Shape;
import nl.dionsegijn.konfetti.xml.KonfettiView;

public class GameView extends View {

    private final int UNFILLED_SQUARE_OPACITY = 0;
    private final int FILLED_SQUARE_OPACITY = 140;

    private final int DOT_COLOR = Color.WHITE;
    private final int DOT_OPACITY = 255;
    private final int SELECTED_DOT_OPACITY = 100;
    private final int BITMAP_WIDTH = 30;
    private final int SELECTED_DOT_WIDTH = 2 * BITMAP_WIDTH;

    private final int UNSELECTED_LINE_OPACITY = 50;
    private final int SELECTED_LINE_OPACITY = 255;
    private final int LINE_WIDTH = 12;

    private TextView statusDisplay;
    private TextView p1Score;
    private TextView p2Score;
    private ImageView p1Turn;
    private ImageView p2Turn;
    private ImageView winner;
    private KonfettiView konfettiView;

    private Dot selectedDot1;
    private Dot selectedDot2;
    private float currentX2;
    private float currentY2;

    private Paint paint;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

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
        sharedPreferences = getContext().getSharedPreferences(PrefUtility.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        int size = sharedPreferences.getInt(PrefUtility.BOARD_SIZE, PrefUtility.DEFAULT_BOARD_SIZE);

        boolean useSavedGame = sharedPreferences.getBoolean(PrefUtility.USE_SAVED_GAME, false);
        if (useSavedGame) {
            String json = sharedPreferences.getString(PrefUtility.SAVED_GAME, null);
            gameState = GameState.getInstance(json);
            editor.putBoolean(PrefUtility.USE_SAVED_GAME, false);
            editor.apply();
        } else {
            gameState = GameState.getInstance();
        }

        gameState.setUpBoard(size);

        boolean playComputer = sharedPreferences.getBoolean(PrefUtility.IS_PLAY_COMPUTER, false);
        gameState.setPlayComputer(playComputer);

        String p1Name = sharedPreferences.getString(PrefUtility.PLAYER_NAME_1, PrefUtility.DEFAULT_PLAYER_NAME_1);
        String playerColor1 = sharedPreferences.getString(PrefUtility.PLAYER_COLOR_1, PrefUtility.DEFAULT_PLAYER_COLOR_1);
        gameState.setP1Color(ContextCompat.getColor(getContext(), PrefUtility.getColor(playerColor1)));
        gameState.setP1Name(p1Name);

        if (!playComputer) {
            String p2Name = sharedPreferences.getString(PrefUtility.PLAYER_NAME_2, PrefUtility.DEFAULT_PLAYER_NAME_2);
            String playerColor2 = sharedPreferences.getString(PrefUtility.PLAYER_COLOR_2, PrefUtility.DEFAULT_PLAYER_COLOR_2);
            gameState.setP2Name(p2Name);
            gameState.setP2Color(ContextCompat.getColor(getContext(), PrefUtility.getColor(playerColor2)));
        } else {
            gameState.setP2Color(ContextCompat.getColor(getContext(), PrefUtility.getColor(PrefUtility.COMPUTER_COLOR)));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawSquares(canvas);
        drawLines(canvas);
        drawInProgressMove(canvas);
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

    private void drawInProgressMove(Canvas canvas) {
        paint.setColor(DOT_COLOR);
        paint.setAlpha(SELECTED_DOT_OPACITY);

        if (selectedDot2 != null) {
            canvas.drawCircle(selectedDot2.getX(), selectedDot2.getY(), SELECTED_DOT_WIDTH + BITMAP_WIDTH, paint);
        }

        if (selectedDot1 != null) {
            canvas.drawCircle(selectedDot1.getX(), selectedDot1.getY(), SELECTED_DOT_WIDTH, paint);
            paint.setColor(gameState.getCurrentPlayer().getColor());
            paint.setAlpha(SELECTED_LINE_OPACITY);
            paint.setStrokeWidth(LINE_WIDTH);
            canvas.drawLine(selectedDot1.getX(), selectedDot1.getY(), currentX2, currentY2, paint);
        }
    }

    private void drawDots(Canvas canvas) {
        paint.setColor(DOT_COLOR);
        paint.setAlpha(DOT_OPACITY);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(PrefUtility.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String vertex = sharedPreferences.getString(PrefUtility.VERTEX, PrefUtility.DEFAULT_VERTEX);
        Bitmap bitmap = getBitmapFromVectorDrawable(getContext(), PrefUtility.getVertex(vertex));

        for (Dot[] row : gameState.getDots()) {
            for (Dot dot : row) {
                canvas.drawBitmap(bitmap, dot.getX() - BITMAP_WIDTH, dot.getY() - BITMAP_WIDTH, paint);
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

    private void explode() {
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_star);
        Drawable drawable2 = ContextCompat.getDrawable(getContext(), R.drawable.ic_heart);
        Shape.DrawableShape drawableShape = new Shape.DrawableShape(drawable, true);
        Shape.DrawableShape drawableShape2 = new Shape.DrawableShape(drawable2, true);
        EmitterConfig emitterConfig = new Emitter(100L, TimeUnit.MILLISECONDS).max(100);
        konfettiView.start(
                new PartyFactory(emitterConfig)
                        .spread(360)
                        .shapes(Arrays.asList(Shape.Square.INSTANCE, Shape.Circle.INSTANCE, drawableShape, drawableShape2))
                        .colors(Arrays.asList(0xfce18a, 0xff726d, 0x8aa5fc, 0xb48def, 0xb8fda3))
                        .setSpeedBetween(0f, 30f)
                        .position(new Position.Relative(0.5, 0.39))
                        .build()
        );
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && gameState.isAllowClick() && selectedDot1 == null) {
            double x1 = event.getX();
            double y1 = event.getY();
            Dot dot1 = findDot(x1, y1);
            if (dot1 != null) {
                selectedDot1 = dot1;
                this.postInvalidate();
            }
        } else if (event.getAction() == MotionEvent.ACTION_MOVE && selectedDot1 != null) {
            currentX2 = event.getX();
            currentY2 = event.getY();
            Dot dot2 = findDot(currentX2, currentY2);
            if (dot2 != null && findUnselectedLine(selectedDot1, dot2) != null) {
                selectedDot2 = dot2;
            } else {
                selectedDot2 = null;
            }
            this.postInvalidate();
        } else if (event.getAction() == MotionEvent.ACTION_UP && selectedDot1 != null) {
            currentX2 = event.getX();
            currentY2 = event.getY();
            Dot dot2 = findDot(currentX2, currentY2);
            if (dot2 != null) {
                Line line = findUnselectedLine(selectedDot1, dot2);
                if (line != null) {
                    makeMove(line);
                }
            }
            selectedDot1 = null;
            selectedDot2 = null;
            this.postInvalidate();
        }
        return true;
    }

    private Dot findDot(double x, double y) {
        for (Dot[] row : gameState.getDots()) {
            for (Dot dot : row) {
                double xDist = Math.abs(dot.getX() - x);
                double yDist = Math.abs(dot.getY() - y);
                if (xDist <= SELECTED_DOT_WIDTH && yDist <= SELECTED_DOT_WIDTH && dot.hasOpenLines()) {
                    return dot;
                }
            }
        }
        return null;
    }

    private Line findUnselectedLine(Dot dot1, Dot dot2) {
        int spacing = gameState.getSpacing();
        int row1 = (int) (dot1.getY() / spacing) - 1;
        int row2 = (int) (dot2.getY() / spacing) - 1;
        int col1 = (int) (dot1.getX() / spacing) - 1;
        int col2 = (int) (dot2.getX() / spacing) - 1;
        Line line = null;
        if (Math.abs(row1 - row2) <= 1 && Math.abs(col1 - col2) <= 1) {
            if (row1 == row2 && col1 > col2) {
                line = gameState.getHorizontalLines()[row1][col2];
            } else if (row1 == row2 && col1 < col2) {
                line = gameState.getHorizontalLines()[row1][col1];
            } else if (row1 > row2 && col1 == col2) {
                line = gameState.getVerticalLines()[row2][col1];
            } else if (row1 < row2 && col1 == col2) {
                line = gameState.getVerticalLines()[row1][col1];
            }
            if (line != null && !line.isSelected()) {
                return line;
            }
        }
        return null;
    }

    private void makeMove(Line line) {
        line.selectLine(gameState.getCurrentPlayer());
        gameState.advanceTurn();
        updateDisplays();
        this.postInvalidate(); // forces view to call onDraw
        if (gameState.isComputerTurn()) {
            runComputerTurn();
        } else {
            saveGameState();
        }
    }

    private void saveGameState() {
        if (gameState.gameOver()) {
            editor.remove(PrefUtility.SAVED_GAME);
            editor.putBoolean(PrefUtility.IS_GAME_SAVED, false);
        } else {
            String str = gameState.toString();
            editor.putString(PrefUtility.SAVED_GAME, str);
            editor.putBoolean(PrefUtility.IS_GAME_SAVED, true);
        }
        editor.apply();
    }

    private void runComputerTurn() {
        // Short delay to improve user experience
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            gameState.computerTurn();
            updateDisplays();
            this.postInvalidate(); // forces view to call onDraw
            if (gameState.isComputerTurn()) {
                runComputerTurn();
            } else {
                saveGameState();
            }
        }, 500);
    }

    @SuppressLint("SetTextI18n")
    private void updateDisplays() {
        p1Score.setText(Integer.toString(gameState.getP1Score()));
        p2Score.setText(Integer.toString(gameState.getP2Score()));
        if (gameState.gameOver()) {
            statusDisplay.setText(gameState.getResultsString());
            setWinnerImage(gameState.getWinnerID());
            if (gameState.getP1Score() != gameState.getP2Score()) { explode(); }
            else { winner.setOnClickListener(null); }
        } else {
            if (gameState.getTurn() == 0) { animateTurn(p1Turn, p2Turn); }
            else { animateTurn(p2Turn, p1Turn); }
            statusDisplay.setText(gameState.getCurrentPlayer().getName() + "'s turn");
        }
    }

    private void animateTurn(ImageView visiblePlayer, ImageView invisiblePlayer) {
        visiblePlayer.setVisibility(VISIBLE);
        invisiblePlayer.setVisibility(INVISIBLE);

        Animation mAnimation = new TranslateAnimation(
                TranslateAnimation.ABSOLUTE, 0f,
                TranslateAnimation.ABSOLUTE, 0f,
                TranslateAnimation.RELATIVE_TO_PARENT, -0.1f,
                TranslateAnimation.RELATIVE_TO_PARENT, 0.12f);
        mAnimation.setDuration(1000);
        mAnimation.setRepeatCount(-1);
        mAnimation.setRepeatMode(Animation.REVERSE);
        mAnimation.setInterpolator(new LinearInterpolator());

        invisiblePlayer.clearAnimation();
        visiblePlayer.setAnimation(mAnimation);
    }

    private void setWinnerImage(int winnerID) {

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(PrefUtility.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        boolean playComputer = sharedPreferences.getBoolean(PrefUtility.IS_PLAY_COMPUTER, false);
        String playerAvatar1 = sharedPreferences.getString(PrefUtility.PLAYER_AVATAR_1, PrefUtility.DEFAULT_PLAYER_AVATAR_1);
        String playerAvatar2 = sharedPreferences.getString(PrefUtility.PLAYER_AVATAR_2, PrefUtility.DEFAULT_PLAYER_AVATAR_2);
        int resID;

        if (winnerID == 0) { resID = PrefUtility.getAvatar(playerAvatar1); }
        else if(winnerID == -1) { resID = R.drawable.ic_tie; }
        else { resID = (playComputer) ? R.drawable.ic_robot : PrefUtility.getAvatar(playerAvatar2); }

        winner.setImageResource(resID);
        winner.setVisibility(VISIBLE);
    }

    public void setUpReferences(TextView p1Score,
                                TextView p2Score,
                                TextView p1Name,
                                TextView p2Name,
                                TextView statusDisplay,
                                Button btnResetGame,
                                ImageView p1Turn,
                                ImageView p2Turn,
                                ImageView winner,
                                KonfettiView konfettiView
    ) {
        this.p1Score = p1Score;
        this.p2Score = p2Score;
        p1Name.setText(gameState.getP1Name());
        p2Name.setText(gameState.getP2Name());
        this.statusDisplay = statusDisplay;
        this.p1Turn = p1Turn;
        this.p2Turn = p2Turn;
        this.winner = winner;
        this.konfettiView = konfettiView;
        btnResetGame.setOnClickListener(view -> resetGame());
        winner.setOnClickListener(view -> explode());
        updateDisplays();
    }

    private void resetGame() {
        winner.setOnClickListener(view -> explode());
        winner.setVisibility(View.GONE);
        editor.remove(PrefUtility.SAVED_GAME);
        editor.putBoolean(PrefUtility.IS_GAME_SAVED, false);
        editor.apply();
        gameState.resetGame();
        updateDisplays();
        this.postInvalidate();
    }

}