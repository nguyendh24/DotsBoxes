package com.example.dotsboxes.Fragments;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.dotsboxes.PrefUtility;
import com.example.dotsboxes.MainActivity;
import com.example.dotsboxes.R;
import com.example.dotsboxes.databinding.FragmentSettingsBinding;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.HashMap;

public class SettingsFragment extends Fragment {

    private RadioGroup radioGrid;
    private RadioGroup radioVerticesA;
    private RadioGroup radioVerticesB;

    private MaterialCardView cvP1;
    private MaterialCardView cvP2;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private View settingsView;

    private static boolean isPlayer2;

    public SettingsFragment(){ }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        com.example.dotsboxes.databinding.FragmentSettingsBinding binding = FragmentSettingsBinding.inflate(getLayoutInflater());
        sharedPreferences = MainActivity.getContext().getSharedPreferences(PrefUtility.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        settingsView = binding.getRoot();

        hideFloatingBtn(true);
        setAvatars();
        setRadioGrid();
        setRadioVertices();

        radioGrid.setOnCheckedChangeListener(getListenerRadioGrid);
        radioVerticesA.setOnCheckedChangeListener(getListenerRadioVerticesA);
        radioVerticesB.setOnCheckedChangeListener(getListenerRadioVerticesB);
        cvP1.setOnClickListener(getListenerCvP1);
        cvP2.setOnClickListener(getListenerCvP2);

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

        return settingsView;
    }

    /** Listeners */
    private final View.OnClickListener getListenerCvP1 = view -> { isPlayer2 = false; avatarColorDialog(); };

    private final View.OnClickListener getListenerCvP2 = view -> { isPlayer2 = true; avatarColorDialog(); };

    private final RadioGroup.OnCheckedChangeListener getListenerRadioGrid = (radioGroup, checkedId) -> {
        int btnID = radioGroup.getCheckedRadioButtonId();
        HashMap<Integer, Integer> boardMap = new HashMap<Integer, Integer>() {{
            put(R.id.rbGrid4, 4);
            put(R.id.rbGrid5, 5);
            put(R.id.rbGrid6, 6);
        }};

        gridChangeDialog(boardMap.get(btnID));

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
    public void setAvatars() {
        ImageView ivP1 = settingsView.findViewById(R.id.ivP1);
        ImageView ivP2 = settingsView.findViewById(R.id.ivP2);

        String playerAvatar1 = sharedPreferences.getString(PrefUtility.PLAYER_AVATAR_1, PrefUtility.DEFAULT_PLAYER_AVATAR_1);
        String playerAvatar2 = sharedPreferences.getString(PrefUtility.PLAYER_AVATAR_2, PrefUtility.DEFAULT_PLAYER_AVATAR_2);
        String playerColor1 = sharedPreferences.getString(PrefUtility.PLAYER_COLOR_1, PrefUtility.DEFAULT_PLAYER_COLOR_1);
        String playerColor2 = sharedPreferences.getString(PrefUtility.PLAYER_COLOR_2, PrefUtility.DEFAULT_PLAYER_COLOR_2);
        int colorP1 = getResources().getColor(PrefUtility.getColor(playerColor1));
        int colorP2 = getResources().getColor(PrefUtility.getColor(playerColor2));

        cvP1 = settingsView.findViewById(R.id.cvP1);
        cvP2 = settingsView.findViewById(R.id.cvP2);

        cvP1.setCardBackgroundColor(colorP1);
        cvP2.setCardBackgroundColor(colorP2);

        ivP1.setImageResource(PrefUtility.getAvatar(playerAvatar1));
        ivP2.setImageResource(PrefUtility.getAvatar(playerAvatar2));
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),  R.style.AlertDialogStyle);
        builder
                .setMessage(getResources().getString(R.string.board_warning))
                .setPositiveButton("Continue", (dialog, id) -> {
                    editor.putInt(PrefUtility.BOARD_SIZE, newBoardSize);
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
            hideFloatingBtn(false);
            fragmentManager.popBackStack();
        }
    };

    /** Helper methods */
    private void hideFloatingBtn(boolean isHide) {
        FloatingActionButton fabHelp = getActivity().findViewById(R.id.btnHelp);
        FloatingActionButton fabSettings = getActivity().findViewById(R.id.btnSettings);
        if (isHide) {
            fabHelp.hide();
            fabSettings.hide();
        } else {
            fabHelp.show();
            fabSettings.show();
        }
    }

    public static boolean isPlayer2() { return isPlayer2; }

}
