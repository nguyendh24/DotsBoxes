package com.example.dotsboxes;

import java.util.HashMap;
import java.util.Map;

public class PrefUtility {
    public static final String SHARED_PREF_NAME = "MySharedPref";

    public static final String SAVED_GAME = "savedGame";
    public static final String USE_SAVED_GAME = "useSavedGame";
    public static final String BOARD_SIZE = "boardSize";
    public static final String VERTEX = "vertex";
    public static final String PLAYER_NAME_1 = "playerName1";
    public static final String PLAYER_NAME_2 = "playerName2";
    public static final String PLAYER_COLOR_1 = "playerColor1";
    public static final String PLAYER_COLOR_2 = "playerColor2";
    public static final String PLAYER_AVATAR_1 = "playerAvatar1";
    public static final String PLAYER_AVATAR_2 = "playerAvatar2";
    public static final String IS_FIRST_TIME = "isFirstTime";
    public static final String IS_GAME_SAVED = "isGameSaved";
    public static final String IS_PLAY_COMPUTER = "isPlayComputer";

    public static final String BANGS = "bangs";
    public static final String FLOWER = "flower";
    public static final String BUZZ = "gpa";
    public static final String BRUNETTE = "gma";
    public static final String CURLY = "curly";
    public static final String NEON = "neon";
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
    public static final String COMPUTER_COLOR = "gray";
    public static final String COMPUTER_COLOR_DARK = "darkGray";
    public static final String DARKGROUND = "darkGround";

    public static final int DEFAULT_BOARD_SIZE = 4;
    public static final String DEFAULT_VERTEX = DOT;
    public static final String DEFAULT_PLAYER_COLOR_1 = BLUE;
    public static final String DEFAULT_PLAYER_COLOR_2 = RED;
    public static final String DEFAULT_PLAYER_NAME_1 = "Player 1";
    public static final String DEFAULT_PLAYER_NAME_2 = "Player 2";
    public static final String DEFAULT_PLAYER_AVATAR_1 = BANGS;
    public static final String DEFAULT_PLAYER_AVATAR_2 = FLOWER;


    private static final Map<String, Integer> colorMap = new HashMap<String, Integer>() {{
        put(BLUE, R.color.bluePlayer);
        put(RED, R.color.redPlayer);
        put(YELLOW, R.color.yellowPlayer);
        put(PINK, R.color.pinkPlayer);
        put(GREEN, R.color.greenPlayer);
        put(PURPLE, R.color.purplePlayer);
        put(COMPUTER_COLOR, R.color.computerPlayer);
        put(COMPUTER_COLOR_DARK, R.color.computerPlayerDark);
        put(DARKGROUND, R.color.darkGround);
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
        put(BANGS, R.drawable.ic_a_bangs);
        put(FLOWER, R.drawable.ic_a_flower);
        put(BUZZ, R.drawable.ic_a_buzz);
        put(BRUNETTE, R.drawable.ic_a_brunette);
        put(CURLY, R.drawable.ic_a_curly);
        put(NEON, R.drawable.ic_a_neon);
        put(BABY, R.drawable.ic_a_baby);
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
