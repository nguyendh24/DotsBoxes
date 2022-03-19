package com.example.dotsboxes.Fragments;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.example.dotsboxes.PrefUtility;
import com.example.dotsboxes.MainActivity;
import com.example.dotsboxes.R;
import com.example.dotsboxes.databinding.FragmentSettingsBinding;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.HashMap;

public class SettingsFragment extends Fragment {

    private RadioGroup radioGrid;
    private RadioGroup radioVerticesA;
    private RadioGroup radioVerticesB;
    private RadioGroup radioColorA;
    private RadioGroup radioColorB;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private View settingsView;

    private boolean isPlayer2;

    public SettingsFragment(){ }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        com.example.dotsboxes.databinding.FragmentSettingsBinding binding = FragmentSettingsBinding.inflate(getLayoutInflater());
        sharedPreferences = MainActivity.getContext().getSharedPreferences(PrefUtility.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        settingsView = binding.getRoot();
        MaterialButtonToggleGroup btnToggle = settingsView.findViewById(R.id.btnToggle);

        hideFloatingBtn(true);
        setRadioGrid();
        setRadioVertices();
        setRadioPlayerColor();

        radioGrid.setOnCheckedChangeListener(getListenerRadioGrid);
        radioVerticesA.setOnCheckedChangeListener(getListenerRadioVerticesA);
        radioVerticesB.setOnCheckedChangeListener(getListenerRadioVerticesB);
        radioColorA.setOnCheckedChangeListener(getListenerRadioColorA);
        radioColorB.setOnCheckedChangeListener(getListenerRadioColorB);
        btnToggle.addOnButtonCheckedListener(getListenerBtnToggle);
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

        return settingsView;
    }

    private final RadioGroup.OnCheckedChangeListener getListenerRadioGrid = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
            int btnID = radioGroup.getCheckedRadioButtonId();

            HashMap<Integer, Integer> boardMap = new HashMap<Integer, Integer>() {{
                put(R.id.rbGrid4, 4);
                put(R.id.rbGrid5, 5);
                put(R.id.rbGrid6, 6);
            }};

            editor.putInt(PrefUtility.BOARD_SIZE, boardMap.get(btnID));
            editor.apply();
        }
    };

    /** Custom back navigation for showing FAB */
    private final OnBackPressedCallback callback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            FragmentManager fragmentManager = getParentFragmentManager();
            hideFloatingBtn(false);
            fragmentManager.popBackStack();
        }
    };

    private final RadioGroup.OnCheckedChangeListener getListenerRadioVerticesA = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
            if (checkedId != -1) {
                radioVerticesB.setOnCheckedChangeListener(null);
                radioVerticesB.clearCheck();
                radioVerticesB.setOnCheckedChangeListener(getListenerRadioVerticesB);
            }

            int btnID = radioGroup.getCheckedRadioButtonId();

            HashMap<Integer, String> vertexMap = new HashMap<Integer, String>() {{
                put(R.id.rbDot, PrefUtility.DOT);
                put(R.id.rbTriangle, PrefUtility.TRIANGLE);
                put(R.id.rbStar, PrefUtility.STAR);
            }};

            editor.putString(PrefUtility.VERTEX, vertexMap.get(btnID));
            editor.apply();
            setRadioVertices();
        }
    };

    private final RadioGroup.OnCheckedChangeListener getListenerRadioVerticesB = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
            if (checkedId != -1) {
                radioVerticesA.setOnCheckedChangeListener(null);
                radioVerticesA.clearCheck();
                radioVerticesA.setOnCheckedChangeListener(getListenerRadioVerticesA);
            }

            int btnID = radioGroup.getCheckedRadioButtonId();

            HashMap<Integer, String> vertexMap = new HashMap<Integer, String>() {{
                put(R.id.rbSun, PrefUtility.SUN);
                put(R.id.rbMoon, PrefUtility.MOON);
                put(R.id.rbCloud, PrefUtility.CLOUD);
            }};

            editor.putString(PrefUtility.VERTEX, vertexMap.get(btnID));
            editor.apply();
            setRadioVertices();
        }
    };

    /** Color */
    private final RadioGroup.OnCheckedChangeListener getListenerRadioColorA = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
            if (checkedId != -1) {
                radioColorB.setOnCheckedChangeListener(null); // remove the listener before clearing so we don't throw that stackoverflow exception
                radioColorB.clearCheck(); // clear the second RadioGroup!
                radioColorB.setOnCheckedChangeListener(getListenerRadioColorB); //reset the listener
            }

            String player = (isPlayer2) ? PrefUtility.PLAYER_COLOR_2 : PrefUtility.PLAYER_COLOR_1;
            int btnID = radioGroup.getCheckedRadioButtonId();

            HashMap<Integer, String> colorMap = new HashMap<Integer, String>() {{
                put(R.id.rbColorBlue, PrefUtility.BLUE);
                put(R.id.rbColorRed, PrefUtility.RED);
                put(R.id.rbColorYellow, PrefUtility.YELLOW);
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

            String player = (isPlayer2) ? PrefUtility.PLAYER_COLOR_2 : PrefUtility.PLAYER_COLOR_1;
            int btnID = radioGroup.getCheckedRadioButtonId();

            HashMap<Integer, String> colorMap = new HashMap<Integer, String>() {{
                put(R.id.rbColorPink, PrefUtility.PINK);
                put(R.id.rbColorGreen, PrefUtility.GREEN);
                put(R.id.rbColorPurple, PrefUtility.PURPLE);
            }};

            editor.putString(player, colorMap.get(btnID));
            editor.apply();
            setRadioPlayerColor();
        }
    };

    private final MaterialButtonToggleGroup.OnButtonCheckedListener getListenerBtnToggle = new MaterialButtonToggleGroup.OnButtonCheckedListener() {
        @Override
        public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
            int btnID = group.getCheckedButtonId();
            isPlayer2 = btnID == R.id.btnPlayer2;
            setRadioPlayerColor();
        }
    };

    private void setRadioGrid() {
        radioGrid = settingsView.findViewById(R.id.radioGrid);
        RadioButton rbGrid4 = settingsView.findViewById(R.id.rbGrid4);
        RadioButton rbGrid5 = settingsView.findViewById(R.id.rbGrid5);
        RadioButton rbGrid6 = settingsView.findViewById(R.id.rbGrid6);

        int boardSize = sharedPreferences.getInt(PrefUtility.BOARD_SIZE, PrefUtility.DEFAULT_BOARD_SIZE);
        if (boardSize == 4) { rbGrid4.setChecked(true); }
        else if (boardSize == 5) { rbGrid5.setChecked(true); }
        else { rbGrid6.setChecked(true); }
    }

    private void setRadioVertices() {
        radioVerticesA = settingsView.findViewById(R.id.radioVerticesA);
        radioVerticesB = settingsView.findViewById(R.id.radioVerticesB);

        String vertex = sharedPreferences.getString(PrefUtility.VERTEX, PrefUtility.DEFAULT_VERTEX);

        RadioButton rb;

        switch (vertex) {
            case PrefUtility.DOT: rb = settingsView.findViewById(R.id.rbDot); break;
            case PrefUtility.TRIANGLE: rb = settingsView.findViewById(R.id.rbTriangle); break;
            case PrefUtility.STAR: rb = settingsView.findViewById(R.id.rbStar); break;
            case PrefUtility.SUN: rb = settingsView.findViewById(R.id.rbSun); break;
            case PrefUtility.MOON: rb = settingsView.findViewById(R.id.rbMoon); break;
            default: rb = settingsView.findViewById(R.id.rbCloud); break;
        }

        rb.setChecked(true);
    }

    private void setRadioPlayerColor() {
        setToggle();
        radioColorA = settingsView.findViewById(R.id.radioColorA);
        radioColorB = settingsView.findViewById(R.id.radioColorB);

        String playerColor = (isPlayer2)
                ? sharedPreferences.getString(PrefUtility.PLAYER_COLOR_2, PrefUtility.DEFAULT_PLAYER_COLOR_2)
                : sharedPreferences.getString(PrefUtility.PLAYER_COLOR_1, PrefUtility.DEFAULT_PLAYER_COLOR_1);

        RadioButton rb;

        switch (playerColor) {
            case PrefUtility.BLUE: rb = settingsView.findViewById(R.id.rbColorBlue); break;
            case PrefUtility.RED: rb = settingsView.findViewById(R.id.rbColorRed); break;
            case PrefUtility.YELLOW: rb = settingsView.findViewById(R.id.rbColorYellow); break;
            case PrefUtility.PINK: rb = settingsView.findViewById(R.id.rbColorPink); break;
            case PrefUtility.GREEN: rb = settingsView.findViewById(R.id.rbColorGreen); break;
            default: rb = settingsView.findViewById(R.id.rbColorPurple); break;
        }

        rb.setChecked(true);
    }

    private void setToggle() {
        MaterialButton btnPlayer1 = settingsView.findViewById(R.id.btnPlayer1);
        MaterialButton btnPlayer2 = settingsView.findViewById(R.id.btnPlayer2);
        if (isPlayer2) { btnPlayer2.setChecked(true); } else { btnPlayer1.setChecked(true); }
    }

    private void hideFloatingBtn(boolean isHide) {
        FloatingActionButton fabHelp = (FloatingActionButton)getActivity().findViewById(R.id.btnHelp);
        FloatingActionButton fabSettings = (FloatingActionButton)getActivity().findViewById(R.id.btnSettings);
        if (isHide) {
            fabHelp.hide();
            fabSettings.hide();
        } else {
            fabHelp.show();
            fabSettings.show();
        }

    }
}
