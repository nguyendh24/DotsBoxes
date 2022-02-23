package com.example.dotsboxes.Views;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import com.example.dotsboxes.Components.Direction;
import com.example.dotsboxes.Components.Line;
import com.example.dotsboxes.MainActivity;
import java.util.HashMap;

public class LinesView extends View {
    /** Data structures */
    private static HashMap<Integer, Line> lines;
    /** Paint attributes */
    private Paint paintLine;
    private final int LINE_COLOR = Color.BLACK;
    private final int LINE_OPACITY = 50;
    private final int LINE_WIDTH = 12;

    public LinesView(Context context) {
        super(context);
        init(null);
    }

    public LinesView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public LinesView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public LinesView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    public static HashMap<Integer, Line> getLines() {
        return lines;
    }

    /** If we create a new instance of CustomView and is included in a new layout file
     * for code optimization */
    private void init(@Nullable AttributeSet set) {
        paintLine = new Paint(Paint.ANTI_ALIAS_FLAG);
        lines = new HashMap<>();
        setLine();
        setCoordinates();
    }

    private void setLine() {
        paintLine.setColor(LINE_COLOR);
        paintLine.setAlpha(LINE_OPACITY);
        paintLine.setStrokeWidth(LINE_WIDTH);
    }

    private void setCoordinates() {
        int lineCount = 0;
        int spacing = (int) MainActivity.deviceWidth / MainActivity.PARTITIONS;
        for (int x = spacing; x < MainActivity.deviceWidth; x += spacing) {
            for (int y = spacing; y < MainActivity.deviceWidth; y += spacing) {
                if (y + spacing < MainActivity.deviceWidth) // VERTICAL LINE
                    lines.put(lineCount++, new Line(x, y, x, y + spacing, Direction.VERTICAL));
                if (x + spacing < MainActivity.deviceWidth) // HORIZONTAL LINE
                    lines.put(lineCount++, new Line(x, y, x + spacing, y, Direction.HORIZONTAL));
            }
        }
    }

    /** 5x5 Grid, 25 dots, 40 edges, 16 boxes */
    @Override
    protected void onDraw(Canvas canvas) {
        for (Line line : lines.values()) { canvas.drawLine( line.getX1(),  line.getY1(),  line.getX2(),  line.getY2(), paintLine); }
    }

}
