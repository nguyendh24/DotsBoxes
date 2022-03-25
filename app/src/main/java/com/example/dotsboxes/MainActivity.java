package com.example.dotsboxes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.example.dotsboxes.Fragments.GameTypeFragment;
import com.example.dotsboxes.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    public static float deviceHeight;
    public static float deviceWidth;
    private static boolean isFirstTime;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        hideActionBar();
        setDeviceDimensions();
        fetchStoredData();
        setBinding();

        if (isFirstTime) { // Instantiate default player data for new users
            startActivity(new Intent(this, Onboard.class));
            setDefaultPrefs();
        } else { // Temporary, to be removed later
//            setDefaultPrefs();
        }

        replaceFragment(new GameTypeFragment());
    }

    private void setDefaultPrefs() {
        editor.putInt(PrefUtility.BOARD_SIZE, PrefUtility.DEFAULT_BOARD_SIZE);
        editor.putString(PrefUtility.VERTEX, PrefUtility.DEFAULT_VERTEX);
        editor.putString(PrefUtility.PLAYER_COLOR_1, PrefUtility.DEFAULT_PLAYER_COLOR_1);
        editor.putString(PrefUtility.PLAYER_COLOR_2, PrefUtility.DEFAULT_PLAYER_COLOR_2);
        editor.putString(PrefUtility.PLAYER_NAME_1, PrefUtility.DEFAULT_PLAYER_NAME_1);
        editor.putString(PrefUtility.PLAYER_NAME_2, PrefUtility.DEFAULT_PLAYER_NAME_2);
        editor.putBoolean(PrefUtility.IS_FIRST_TIME, false);
        editor.putBoolean(PrefUtility.IS_GAME_SAVED, false);
        editor.commit();
    }
    private void setBinding() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment).setReorderingAllowed(true).addToBackStack(null).commit();
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

    private void fetchStoredData() {
        sharedPreferences = getSharedPreferences(PrefUtility.SHARED_PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        isFirstTime = sharedPreferences.getBoolean(PrefUtility.IS_FIRST_TIME, true);
    }

}