package com.example.dotsboxes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class Onboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboard);
        hideActionBar();
        Button btnLetsGo = findViewById(R.id.btnLetsPlay);
        EditText etPlayerName = findViewById(R.id.ptPlayerName);

        btnLetsGo.setOnClickListener(
                view -> {
                    SharedPreferences sh = getApplicationContext().getSharedPreferences(PrefUtility.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sh.edit();
                    editor.putString(PrefUtility.PLAYER_NAME, etPlayerName.getText().toString());
                    editor.apply();
                    finish();
                });
    }

    private void hideActionBar() { getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); }
}