package com.example.dotsboxes;

import android.graphics.Bitmap;

import java.util.HashMap;
import java.util.Map;

public class PrefUtility {
    public static final String SHARED_PREF_NAME = "MySharedPref";

    public static final String BOARD_SIZE = "boardSize";
    public static final String VERTEX = "vertex";
    public static final String PLAYER_COLOR_1 = "playerColor1";
    public static final String PLAYER_COLOR_2 = "playerColor2";
    public static final String IS_FIRST_TIME = "isFirstTime";
    public static final String IS_GAME_SAVED = "isGameSaved";
    public static final String IS_PLAY_COMPUTER = "isPlayComputer";
    public static final String PLAYER_NAME = "playerName";

    public static final String DOT = "dot";
    public static final String TRIANGLE = "triangle";
    public static final String STAR = "star";
    public static final String SUN = "sun";
    public static final String MOON = "moon";
    public static final String CLOUD = "cloud";

    public static final String BLUE = "blue";
    public static final String RED = "red";
    public static final String YELLOW = "yellow";
    public static final String PINK = "pink";
    public static final String GREEN = "green";
    public static final String PURPLE = "purple";

    public static final int DEFAULT_BOARD_SIZE = 4;
    public static final String DEFAULT_VERTEX = DOT;
    public static final String DEFAULT_PLAYER_COLOR_1 = BLUE;
    public static final String DEFAULT_PLAYER_COLOR_2 = RED;
    public static final String DEFAULT_PLAYER_NAME = "User";

    private static final Map<String, Integer> colorMap = new HashMap<String, Integer>() {{
        put(BLUE, R.color.bluePlayer);
        put(RED, R.color.redPlayer);
        put(YELLOW, R.color.yellowPlayer);
        put(PINK, R.color.pinkPlayer);
        put(GREEN, R.color.greenPlayer);
        put(PURPLE, R.color.purplePlayer);
    }};

    private static final Map<String, Integer> vertexMap = new HashMap<String, Integer>() {{
        put(DOT, R.drawable.ic_circle);
        put(TRIANGLE, R.drawable.ic_triangle);
        put(STAR, R.drawable.ic_star);
        put(SUN, R.drawable.ic_sun);
        put(MOON, R.drawable.ic_moon);
        put(CLOUD, R.drawable.ic_cloud);
    }};

    public static int getVertex(String vertex) {
        if (vertexMap.containsKey(vertex))
            return vertexMap.get(vertex);
        return 0;
    }

    public static int getColor(String color) {
        if (colorMap.containsKey(color)) {
            return colorMap.get(color);
        }
        return 0;
    }
}
