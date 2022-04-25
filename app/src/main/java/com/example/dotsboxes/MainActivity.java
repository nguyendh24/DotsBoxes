package com.example.dotsboxes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import com.example.dotsboxes.Fragments.GameTypeFragment;
import com.example.dotsboxes.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    public static float deviceHeight;
    public static float deviceWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideActionBar();
        setModes();
        setDeviceDimensions();
        setBinding();
        SharedPreferences sharedPreferences = getSharedPreferences(PrefUtility.SHARED_PREF_NAME, MODE_PRIVATE);
        boolean isFirstTime = sharedPreferences.getBoolean(PrefUtility.IS_FIRST_TIME, true);

        if (isFirstTime) { setDefaultPrefs(); } // Instantiate default player data for new users

        replaceFragment(new GameTypeFragment());
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (!hasFocus) hideSystemUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideSystemUI();
    }

    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }

    private void setModes() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // locks portrait mode, landscape not implemented
    }

    private void setDefaultPrefs() {
        SharedPreferences sharedPreferences = getSharedPreferences(PrefUtility.SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(PrefUtility.BOARD_SIZE, PrefUtility.DEFAULT_BOARD_SIZE);
        editor.putString(PrefUtility.VERTEX, PrefUtility.DEFAULT_VERTEX);
        editor.putString(PrefUtility.PLAYER_COLOR_1, PrefUtility.DEFAULT_PLAYER_COLOR_1);
        editor.putString(PrefUtility.PLAYER_COLOR_2, PrefUtility.DEFAULT_PLAYER_COLOR_2);
        editor.putString(PrefUtility.PLAYER_NAME_1, PrefUtility.DEFAULT_PLAYER_NAME_1);
        editor.putString(PrefUtility.PLAYER_NAME_2, PrefUtility.DEFAULT_PLAYER_NAME_2);
        editor.putBoolean(PrefUtility.IS_GAME_SAVED, false);
        editor.apply();
    }

    private void setBinding() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment).setReorderingAllowed(true).commit();
    }

    private void setDeviceDimensions() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        deviceHeight = displayMetrics.heightPixels;
        deviceWidth = displayMetrics.widthPixels;
    }

    private void hideActionBar() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

}