package com.example.dotsboxes.Fragments;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.dotsboxes.GameState;
import com.example.dotsboxes.PrefUtility;
import com.example.dotsboxes.R;
import com.example.dotsboxes.databinding.FragmentSettingsBinding;
import com.google.android.material.card.MaterialCardView;
import java.util.HashMap;

public class SettingsFragment extends Fragment {

    private RadioGroup radioGrid;
    private RadioGroup radioVerticesA;
    private RadioGroup radioVerticesB;

    private MaterialCardView cvP1;
    private MaterialCardView cvP2;

    private EditText etP1;
    private EditText etP2;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private View settingsView;

    private static boolean isPlayer2;

    public SettingsFragment(){ }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        com.example.dotsboxes.databinding.FragmentSettingsBinding binding = FragmentSettingsBinding.inflate(getLayoutInflater());
        sharedPreferences = requireContext().getSharedPreferences(PrefUtility.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        settingsView = binding.getRoot();

        setAvatars();
        setNames();
        setRadioGrid();
        setRadioVertices();

        radioGrid.setOnCheckedChangeListener(getListenerRadioGrid);
        radioVerticesA.setOnCheckedChangeListener(getListenerRadioVerticesA);
        radioVerticesB.setOnCheckedChangeListener(getListenerRadioVerticesB);
        cvP1.setOnClickListener(getListenerCvP1);
        cvP2.setOnClickListener(getListenerCvP2);
        etP1.addTextChangedListener(getTextWatcher);
        etP2.addTextChangedListener(getTextWatcher);

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

        binding.btnReturnToGame.setOnClickListener(view -> replaceFragment(new GameFragment()));

        return settingsView;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment).setReorderingAllowed(true).addToBackStack(null).commit();
    }

    /** Listeners */
    private final View.OnClickListener getListenerCvP1 = view -> { isPlayer2 = false; avatarColorDialog(); };

    private final View.OnClickListener getListenerCvP2 = view -> { isPlayer2 = true; avatarColorDialog(); };

    private final TextWatcher getTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

        @Override
        public void afterTextChanged(Editable editable) {
            editor.putString(PrefUtility.PLAYER_NAME_1, etP1.getText().toString());
            editor.putString(PrefUtility.PLAYER_NAME_2, etP2.getText().toString());
            editor.apply();
        }
    };

    private final RadioGroup.OnCheckedChangeListener getListenerRadioGrid = (radioGroup, checkedId) -> {
        int btnID = radioGroup.getCheckedRadioButtonId();
        HashMap<Integer, Integer> boardMap = new HashMap<Integer, Integer>() {{
            put(R.id.rbGrid4, 4);
            put(R.id.rbGrid5, 5);
            put(R.id.rbGrid6, 6);
        }};

        boolean gameSaved = sharedPreferences.getBoolean(PrefUtility.IS_GAME_SAVED, false);

        if (gameSaved) {
            gridChangeDialog(boardMap.get(btnID));
        } else {
            editor.putInt(PrefUtility.BOARD_SIZE, boardMap.get(btnID));
            editor.putBoolean(PrefUtility.IS_GAME_SAVED, false);
            editor.apply();
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

    /** Display Setters */
    private void setAvatars() {
        ImageView ivP1 = settingsView.findViewById(R.id.ivP1);
        ImageView ivP2 = settingsView.findViewById(R.id.ivP2);

        String playerAvatar1 = sharedPreferences.getString(PrefUtility.PLAYER_AVATAR_1, PrefUtility.DEFAULT_PLAYER_AVATAR_1);
        String playerAvatar2 = sharedPreferences.getString(PrefUtility.PLAYER_AVATAR_2, PrefUtility.DEFAULT_PLAYER_AVATAR_2);
        String playerColor1 = sharedPreferences.getString(PrefUtility.PLAYER_COLOR_1, PrefUtility.DEFAULT_PLAYER_COLOR_1);
        String playerColor2 = sharedPreferences.getString(PrefUtility.PLAYER_COLOR_2, PrefUtility.DEFAULT_PLAYER_COLOR_2);
        int colorP1 = ContextCompat.getColor(requireContext(), PrefUtility.getColor(playerColor1));
        int colorP2 = ContextCompat.getColor(requireContext(), PrefUtility.getColor(playerColor2));

        cvP1 = settingsView.findViewById(R.id.cvP1);
        cvP2 = settingsView.findViewById(R.id.cvP2);

        cvP1.setCardBackgroundColor(colorP1);
        cvP2.setCardBackgroundColor(colorP2);

        ivP1.setImageResource(PrefUtility.getAvatar(playerAvatar1));
        ivP2.setImageResource(PrefUtility.getAvatar(playerAvatar2));
    }

    private void setNames() {
        etP1 = settingsView.findViewById(R.id.etP1);
        etP2 = settingsView.findViewById(R.id.etP2);

        String playerName1 = sharedPreferences.getString(PrefUtility.PLAYER_NAME_1, PrefUtility.DEFAULT_PLAYER_NAME_1);
        String playerName2 = sharedPreferences.getString(PrefUtility.PLAYER_NAME_2, PrefUtility.DEFAULT_PLAYER_NAME_2);

        etP1.setText(playerName1);
        etP2.setText(playerName2);
    }

    private void setRadioGrid() {
        radioGrid = settingsView.findViewById(R.id.radioGrid);
        int boardSize = sharedPreferences.getInt(PrefUtility.BOARD_SIZE, PrefUtility.DEFAULT_BOARD_SIZE);

        RadioButton rb;

        switch (boardSize) {
            case 4: rb = settingsView.findViewById(R.id.rbGrid4); break;
            case 5: rb = settingsView.findViewById(R.id.rbGrid5); break;
            default: rb = settingsView.findViewById(R.id.rbGrid6); break;
        }
        rb.setChecked(true);
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

    /** Dialogs */
    private void gridChangeDialog(Integer newBoardSize) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity(),  R.style.AlertDialogStyle);
        builder
                .setMessage(getResources().getString(R.string.board_warning))
                .setPositiveButton("Continue", (dialog, id) -> {
                    GameState.getInstance().resetGame();
                    editor.putInt(PrefUtility.BOARD_SIZE, newBoardSize);
                    editor.putBoolean(PrefUtility.IS_GAME_SAVED, false);
                    editor.apply();
                })
                .setNeutralButton("Cancel", (dialog, id) -> {
                    radioGrid.setOnCheckedChangeListener(null); //
                    setRadioGrid();
                    radioGrid.setOnCheckedChangeListener(getListenerRadioGrid);
                }).setCancelable(false).show();
    }

    private void avatarColorDialog() {
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        DialogFragment dialogFragment = DialogFragment.newInstance();
        dialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFragmentTheme);
        dialogFragment.show(fragmentTransaction, "dialog");
    }

    /** Custom back navigation for showing FAB */
    private final OnBackPressedCallback callback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.popBackStack();
        }
    };


    public static boolean isPlayer2() { return isPlayer2; }

}
