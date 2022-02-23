package com.example.dotsboxes.Views;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import com.example.dotsboxes.Components.Dot;
import com.example.dotsboxes.MainActivity;
import java.util.HashMap;

public class DotsView extends View {
    /** Data structure */
    private static HashMap<Integer, Dot> dots;
    /** Paint attributes */
    private Paint paintDot;
    private final int DOT_COLOR = Color.BLACK;
    private final int DOT_OPACITY = 200;
    private static final int DOT_RADIUS = 20;

    public DotsView(Context context) {
        super(context);
        init(null);
    }

    public DotsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public DotsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public DotsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    /** If we create a new instance of CustomView and is included in a new layout file
     * for code optimization */
    private void init(@Nullable AttributeSet set) {
        paintDot = new Paint(Paint.ANTI_ALIAS_FLAG);
        dots = new HashMap<>();
        setDots();
        setCoordinates();
    }

    private void setDots() {
        paintDot.setColor(DOT_COLOR);
        paintDot.setAlpha(DOT_OPACITY);
        paintDot.setShadowLayer(10, 4, 4, Color.GRAY);
    }

    private void setCoordinates() {
        int dotCount = 0;
        int spacing = (int) MainActivity.deviceWidth / MainActivity.PARTITIONS;
        for (int x = spacing; x < MainActivity.deviceWidth; x += spacing) {
            for (int y = spacing; y < MainActivity.deviceWidth; y += spacing) {
                dots.put(dotCount++, new Dot(x, y));
            }
        }
    }

    public static int getDotRadius() {
        return DOT_RADIUS;
    }

    /** 5x5 Grid, 25 dots, 40 edges, 16 boxes */
    @Override
    protected void onDraw(Canvas canvas) {
        for (Dot dot : dots.values()) { canvas.drawCircle(dot.getX(), dot.getY(), DOT_RADIUS, paintDot); }
    }

}
