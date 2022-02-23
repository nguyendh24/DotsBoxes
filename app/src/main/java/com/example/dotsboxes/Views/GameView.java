package com.example.dotsboxes.Views;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;
import com.example.dotsboxes.Components.Direction;
import com.example.dotsboxes.Components.Line;
import com.example.dotsboxes.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GameView extends View {
    /** Data Structures */
    private HashMap<Integer, Line> lines;
    private HashMap<Integer, Line> playedLines;
    /** Paint attributes */
    private Paint paintLine;
    private final int LINE_OPACITY = 255;
    private final int LINE_WIDTH = 12;
    private final int LINE_OFFSET = 15; // Based off DOT_RADIUS
    private int trial = 0;

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
        paintLine = new Paint(Paint.ANTI_ALIAS_FLAG);
        playedLines = new HashMap<>();
        lines = LinesView.getLines();
        setLine();
    }

    private void setLine() {
        paintLine.setAlpha(LINE_OPACITY);
        paintLine.setStrokeWidth(LINE_WIDTH);
    }

    private void setLineColor(int color) {
        paintLine.setColor(color);
    }

    /** 5x5 Grid, 25 dots, 40 edges, 16 boxes */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Line line : playedLines.values()) {
            setLineColor(line.getColor());
            canvas.drawLine(line.getX1(), line.getY1(), line.getX2(), line.getY2(), paintLine);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            double xPos = event.getX();
            double yPos = event.getY();
            Integer key = checkCoordinate(xPos, yPos);
            if (key != -1) {
                Line line = lines.get(key);
                if (trial % 2 == 0) { line.setColor(getResources().getColor(R.color.redPlayer)); }
                else { line.setColor(getResources().getColor(R.color.bluePlayer)); }
                playedLines.put(key, line);
                lines.remove(key);
                trial++;
                this.postInvalidate(); // forces view to call onDraw
            }
        }
        return true;
    }

    private Integer checkCoordinate(double xPos, double yPos){
        for (Map.Entry<Integer, Line> line : lines.entrySet()) {
            if (isLineTapped(line.getValue(), xPos, yPos)) { return line.getKey(); }
        }
        return -1;
    }

    private boolean isLineTapped(Line line, double xPos, double yPos) {
        boolean result;
        if (line.getDir() == Direction.HORIZONTAL) {
            result = (xPos >= line.getX1() + LINE_OFFSET
                            && xPos <= line.getX2() - LINE_OFFSET
                            && yPos >= line.getY1() - LINE_OFFSET
                            && yPos <= line.getY2() + LINE_OFFSET);
        } else {
            result = (yPos >= line.getY1() + LINE_OFFSET
                            && yPos <= line.getY2() - LINE_OFFSET
                            && xPos >= line.getX1() - LINE_OFFSET
                            && xPos <= line.getX2() + LINE_OFFSET);
        }
        return result;
    }

}