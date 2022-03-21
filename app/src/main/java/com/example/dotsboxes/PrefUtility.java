package com.example.dotsboxes;

import android.graphics.drawable.Drawable;

import java.util.HashMap;
import java.util.Map;

public class PrefUtility {
    public static final String SHARED_PREF_NAME = "MySharedPref";

    public static final String BOARD_SIZE = "boardSize";
    public static final String VERTEX = "vertex";
    public static final String PLAYER_COLOR_1 = "playerColor1";
    public static final String PLAYER_COLOR_2 = "playerColor2";
    public static final String PLAYER_AVATAR_1 = "playerAvatar1";
    public static final String PLAYER_AVATAR_2 = "playerAvatar2";
    public static final String IS_FIRST_TIME = "isFirstTime";
    public static final String IS_GAME_SAVED = "isGameSaved";
    public static final String IS_PLAY_COMPUTER = "isPlayComputer";
    public static final String PLAYER_NAME = "playerName";

    public static final String BOY = "boy";
    public static final String GIRL = "girl";
    public static final String GPA = "gpa";
    public static final String GMA = "gma";
    public static final String CURLY = "curly";
    public static final String BABY = "baby";

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
    public static final String DEFAULT_PLAYER_AVATAR_1 = BOY;
    public static final String DEFAULT_PLAYER_AVATAR_2 = GIRL;


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

    private static final Map<String, Integer> avatarMap = new HashMap<String, Integer>() {{
        put(BOY, R.drawable.ic_boy_with_bangs);
        put(GIRL, R.drawable.ic_neon_hair_girl);
        put(GPA, R.drawable.ic_gpa);
        put(GMA, R.drawable.ic_gma);
        put(CURLY, R.drawable.ic_curly_hair);
        put(BABY, R.drawable.ic_baby);
    }};

    public static int getAvatar(String avatar) {
        if (avatarMap.containsKey(avatar))
            return avatarMap.get(avatar);
        return 0;
    }

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
