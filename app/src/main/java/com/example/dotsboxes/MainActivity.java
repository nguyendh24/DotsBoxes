package com.example.dotsboxes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.example.dotsboxes.Fragments.GameTypeFragment;
import com.example.dotsboxes.Fragments.HomeFragment;
import com.example.dotsboxes.Fragments.SettingsFragment;
import com.example.dotsboxes.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    public static float deviceHeight;
    public static float deviceWidth;
    private static boolean isFirstTime;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private final HomeFragment homeFragment = new HomeFragment();
    private final SettingsFragment settingsFragment = new SettingsFragment();

    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.context = getApplicationContext();
        hideActionBar();
        setDeviceDimensions();
        fetchStoredData();
        setBinding();

        if (isFirstTime) {
            startActivity(new Intent(this, Onboard.class));
            editor.putInt("boardSize", 4);
            editor.putString("vertex", "dot");
            editor.putString("playerColor1", "blue");
            editor.putString("playerColor2", "red");
            editor.putBoolean("isFirstTime", false);
            editor.commit();
        } else {
            /** DELETE LATER, JUST SO NO ONE HAS TO WIPE DATA ON EMULATOR */
            editor.putInt("boardSize", 4);
            editor.putString("vertex", "dot");
            editor.putString("playerColor1", "blue");
            editor.putString("playerColor2", "red");
            editor.putBoolean("isFirstTime", false);
            editor.putString("playerName", "user");
            editor.commit();
        }
        replaceFragment(new GameTypeFragment());

        binding.btnHelp.setOnClickListener(view -> showHelpDialog());

        binding.btnSettings.setOnClickListener(view -> replaceFragment(settingsFragment));

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

    private void showHelpDialog() {
        Dialog dialog = new Dialog(this);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        dialog.setContentView(R.layout.dialog_help);
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.show(); dialog.getWindow().setAttributes(lp);
        dialog.findViewById(R.id.close_corner).setOnClickListener(view -> dialog.dismiss());
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
        sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        isFirstTime = sharedPreferences.getBoolean("isFirstTime", true);
    }

    public static Context getContext() { return MainActivity.context; }

}