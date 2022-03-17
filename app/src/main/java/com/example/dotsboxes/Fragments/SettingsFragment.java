package com.example.dotsboxes.Fragments;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.dotsboxes.MainActivity;
import com.example.dotsboxes.R;
import com.example.dotsboxes.databinding.FragmentSettingsBinding;
import java.util.HashMap;

public class SettingsFragment extends Fragment {
    private FragmentSettingsBinding binding;

    private RadioGroup radioGrid;
    private RadioGroup radioVertices;
    private RadioGroup radioColorA;
    private RadioGroup radioColorB;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private View settingsView;

    private boolean isPlayer2;

    private SwitchCompat switchPlayer;

    public SettingsFragment(){ }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(getLayoutInflater());
        sharedPreferences = MainActivity.getContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        settingsView = binding.getRoot();
        Button btnBack = settingsView.findViewById(R.id.btnBack);
        isPlayer2 = ((SwitchCompat) settingsView.findViewById(R.id.switchColor)).isChecked();

        setRadioGrid();
        setRadioVertices();
        setRadioPlayerColor();
        editor.apply();

        radioGrid.setOnCheckedChangeListener(getListenerRadioGrid);
        radioVertices.setOnCheckedChangeListener(getListenerRadioVertices);
        radioColorA.setOnCheckedChangeListener(getListenerRadioColorA);
        radioColorB.setOnCheckedChangeListener(getListenerRadioColorB);
        switchPlayer.setOnCheckedChangeListener(getListenerSwitchPlayer);
        btnBack.setOnClickListener(getListenerBtnBack);

        return settingsView;
    }

    private final View.OnClickListener getListenerBtnBack = view -> {
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.popBackStack();
    };

    private final RadioGroup.OnCheckedChangeListener getListenerRadioGrid = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
            int btnID = radioGroup.getCheckedRadioButtonId();

            HashMap<Integer, Integer> boardMap = new HashMap<Integer, Integer>() {{
                put(R.id.rbGrid4, 4);
                put(R.id.rbGrid5, 5);
                put(R.id.rbGrid6, 6);
            }};

            editor.putInt("boardSize", boardMap.get(btnID));
            editor.apply();
        }
    };


    private final RadioGroup.OnCheckedChangeListener getListenerRadioVertices = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
            int btnID = radioGroup.getCheckedRadioButtonId();

            HashMap<Integer, String> vertexMap = new HashMap<Integer, String>() {{
                put(R.id.rbDot, "dot");
                put(R.id.rbTriangle, "triangle");
                put(R.id.rbStar, "star");
            }};

            editor.putString("vertex", vertexMap.get(btnID));
            editor.apply();
        }
    };

    private final RadioGroup.OnCheckedChangeListener getListenerRadioColorA = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
            if (checkedId != -1) {
                radioColorB.setOnCheckedChangeListener(null); // remove the listener before clearing so we don't throw that stackoverflow exception
                radioColorB.clearCheck(); // clear the second RadioGroup!
                radioColorB.setOnCheckedChangeListener(getListenerRadioColorB); //reset the listener
            }

            String player = (isPlayer2) ? "playerColor2" : "playerColor1";
            int btnID = radioGroup.getCheckedRadioButtonId();

            HashMap<Integer, String> colorMap = new HashMap<Integer, String>() {{
                put(R.id.rbColorBlue, "blue");
                put(R.id.rbColorRed, "red");
                put(R.id.rbColorYellow, "yellow");
            }};

            editor.putString(player, colorMap.get(btnID));
            editor.apply();
            setRadioPlayerColor();
        }
    };

    private final RadioGroup.OnCheckedChangeListener getListenerRadioColorB = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
            if (checkedId != -1) {
                radioColorA.setOnCheckedChangeListener(null);
                radioColorA.clearCheck();
                radioColorA.setOnCheckedChangeListener(getListenerRadioColorA);
            }

            String player = (isPlayer2) ? "playerColor2" : "playerColor1";
            int btnID = radioGroup.getCheckedRadioButtonId();

            HashMap<Integer, String> colorMap = new HashMap<Integer, String>() {{
                put(R.id.rbColorPink, "pink");
                put(R.id.rbColorGreen, "green");
                put(R.id.rbColorPurple, "purple");
            }};

            editor.putString(player, colorMap.get(btnID));
            editor.apply();
            setRadioPlayerColor();
        }
    };

    private final CompoundButton.OnCheckedChangeListener getListenerSwitchPlayer = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            isPlayer2 = b;
            setRadioPlayerColor();
        }
    };

    private void setRadioGrid() {
        radioGrid = settingsView.findViewById(R.id.radioGrid);
        RadioButton rbGrid4 = settingsView.findViewById(R.id.rbGrid4);
        RadioButton rbGrid5 = settingsView.findViewById(R.id.rbGrid5);
        RadioButton rbGrid6 = settingsView.findViewById(R.id.rbGrid6);

        int boardSize = sharedPreferences.getInt("boardSize", 0);
        if (boardSize == 4) { rbGrid4.setChecked(true); }
        else if (boardSize == 5) { rbGrid5.setChecked(true); }
        else { rbGrid6.setChecked(true); }
    }

    private void setRadioVertices() {
        radioVertices = settingsView.findViewById(R.id.radioVertices);
        RadioButton rbDot = settingsView.findViewById(R.id.rbDot);
        RadioButton rbTriangle = settingsView.findViewById(R.id.rbTriangle);
        RadioButton  rbStar = settingsView.findViewById(R.id.rbStar);

        String vertex = sharedPreferences.getString("vertex", "");

        if (vertex.equals("dot")) { rbDot.setChecked(true); }
        else if (vertex.equals("triangle")) { rbTriangle.setChecked(true); }
        else { rbStar.setChecked(true); }
    }

    private void setRadioPlayerColor() {
        setSwitchPlayer();
        radioColorA = settingsView.findViewById(R.id.radioColorA);
        radioColorB = settingsView.findViewById(R.id.radioColorB);
        RadioButton rbColorBlue = settingsView.findViewById(R.id.rbColorBlue);
        RadioButton rbColorRed = settingsView.findViewById(R.id.rbColorRed);
        RadioButton  rbColorYellow = settingsView.findViewById(R.id.rbColorYellow);
        RadioButton  rbColorPink = settingsView.findViewById(R.id.rbColorPink);
        RadioButton  rbColorGreen = settingsView.findViewById(R.id.rbColorGreen);
        RadioButton  rbColorPurple = settingsView.findViewById(R.id.rbColorPurple);

        String player = (isPlayer2) ? "playerColor2" : "playerColor1";
        String playerColor = sharedPreferences.getString(player, "");

        switch (playerColor) {
            case "blue":
                rbColorBlue.setChecked(true);
                break;
            case "red":
                rbColorRed.setChecked(true);
                break;
            case "yellow":
                rbColorYellow.setChecked(true);
                break;
            case "pink":
                rbColorPink.setChecked(true);
                break;
            case "green":
                rbColorGreen.setChecked(true);
                break;
            default:
                rbColorPurple.setChecked(true);
                break;
        }
    }

    private void setSwitchPlayer() {
        switchPlayer = settingsView.findViewById(R.id.switchColor);
        TextView tvPlayerColor = settingsView.findViewById(R.id.tvPlayerColor);
        if (isPlayer2) { tvPlayerColor.setText("Color for Player 2"); } else { tvPlayerColor.setText("Color for Player 1");}
    }
}
