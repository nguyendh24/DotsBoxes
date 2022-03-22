package com.example.dotsboxes.Fragments;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.dotsboxes.MainActivity;
import com.example.dotsboxes.PrefUtility;
import com.example.dotsboxes.R;
import com.example.dotsboxes.databinding.FragmentDialogBinding;
import com.google.android.material.card.MaterialCardView;
import java.util.HashMap;
import java.util.Map;

public class DialogFragment extends androidx.fragment.app.DialogFragment {
    private RadioGroup radioColorA;
    private RadioGroup radioColorB;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private View dialogView;

    private Map<String, MaterialCardView> avatarMap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        com.example.dotsboxes.databinding.FragmentDialogBinding binding = FragmentDialogBinding.inflate(getLayoutInflater());
        sharedPreferences = MainActivity.getContext().getSharedPreferences(PrefUtility.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        dialogView = binding.getRoot();

        MaterialCardView cvAvatarBoy = dialogView.findViewById(R.id.cvAvatarBoy);
        MaterialCardView cvAvatarGirl = dialogView.findViewById(R.id.cvAvatarGirl);
        MaterialCardView cvAvatarGpa = dialogView.findViewById(R.id.cvAvatarGpa);
        MaterialCardView cvAvatarGma = dialogView.findViewById(R.id.cvAvatarGma);
        MaterialCardView cvAvatarCurly = dialogView.findViewById(R.id.cvAvatarCurly);
        MaterialCardView cvAvatarBaby = dialogView.findViewById(R.id.cvAvatarBaby);

        cvAvatarBoy.setOnClickListener(getListenerAvatars);
        cvAvatarGirl.setOnClickListener(getListenerAvatars);
        cvAvatarGpa.setOnClickListener(getListenerAvatars);
        cvAvatarGma.setOnClickListener(getListenerAvatars);
        cvAvatarCurly.setOnClickListener(getListenerAvatars);
        cvAvatarBaby.setOnClickListener(getListenerAvatars);

        setAvatarMap();
        setAvatar();
        setRadioPlayerColor();

        radioColorA.setOnCheckedChangeListener(getListenerRadioColorA);
        radioColorB.setOnCheckedChangeListener(getListenerRadioColorB);
        dialogView.findViewById(R.id.close_corner).setOnClickListener(getListenerDismiss);

        return dialogView;
    }

    private void setAvatarMap() {
        avatarMap = new HashMap<String, MaterialCardView>() {{
            put(PrefUtility.BOY, dialogView.findViewById(R.id.cvAvatarBoy));
            put(PrefUtility.GIRL, dialogView.findViewById(R.id.cvAvatarGirl));
            put(PrefUtility.GPA, dialogView.findViewById(R.id.cvAvatarGpa));
            put(PrefUtility.GMA, dialogView.findViewById(R.id.cvAvatarGma));
            put(PrefUtility.CURLY, dialogView.findViewById(R.id.cvAvatarCurly));
            put(PrefUtility.BABY, dialogView.findViewById(R.id.cvAvatarBaby));
        }};
    }

    /** Listeners */
    private final View.OnClickListener getListenerDismiss = view -> {
        FragmentManager fm = getParentFragmentManager();
        fm.popBackStack();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame_layout, new SettingsFragment()).setReorderingAllowed(true).addToBackStack(null).commit();
        getDialog().dismiss();
    };

    private final View.OnClickListener getListenerAvatars = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String player = (SettingsFragment.isPlayer2()) ? PrefUtility.PLAYER_AVATAR_2 : PrefUtility.PLAYER_AVATAR_1;

            int avatarID = view.getId();

            for (Map.Entry<String, MaterialCardView> avatar : avatarMap.entrySet()) {
                if (avatarID == avatar.getValue().getId()) {
                    editor.putString(player, avatar.getKey());
                    editor.apply();
                    break;
                }
            }

            setAvatar();
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

            String player = (SettingsFragment.isPlayer2()) ? PrefUtility.PLAYER_COLOR_2 : PrefUtility.PLAYER_COLOR_1;
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

            String player = (SettingsFragment.isPlayer2()) ? PrefUtility.PLAYER_COLOR_2 : PrefUtility.PLAYER_COLOR_1;
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


    /** Display Setters */
    private void setRadioPlayerColor() {
        radioColorA = dialogView.findViewById(R.id.radioColorA);
        radioColorB = dialogView.findViewById(R.id.radioColorB);

        String playerColor = (SettingsFragment.isPlayer2())
                ? sharedPreferences.getString(PrefUtility.PLAYER_COLOR_2, PrefUtility.DEFAULT_PLAYER_COLOR_2)
                : sharedPreferences.getString(PrefUtility.PLAYER_COLOR_1, PrefUtility.DEFAULT_PLAYER_COLOR_1);

        RadioButton rb;

        switch (playerColor) {
            case PrefUtility.BLUE: rb = dialogView.findViewById(R.id.rbColorBlue); break;
            case PrefUtility.RED: rb = dialogView.findViewById(R.id.rbColorRed); break;
            case PrefUtility.YELLOW: rb = dialogView.findViewById(R.id.rbColorYellow); break;
            case PrefUtility.PINK: rb = dialogView.findViewById(R.id.rbColorPink); break;
            case PrefUtility.GREEN: rb = dialogView.findViewById(R.id.rbColorGreen); break;
            default: rb = dialogView.findViewById(R.id.rbColorPurple); break;
        }

        updateAvatarColors(getResources().getColor(PrefUtility.getColor(playerColor)));
        rb.setChecked(true);
    }

    private void setAvatar() {
        String playerAvatar = (SettingsFragment.isPlayer2())
                ? sharedPreferences.getString(PrefUtility.PLAYER_AVATAR_2, PrefUtility.DEFAULT_PLAYER_AVATAR_2)
                : sharedPreferences.getString(PrefUtility.PLAYER_AVATAR_1, PrefUtility.DEFAULT_PLAYER_AVATAR_1);

        for (Map.Entry<String, MaterialCardView> avatar : avatarMap.entrySet()) {
            if (!playerAvatar.equals(avatar.getKey())) {
                avatar.getValue().setStrokeWidth(0);
            } else {
                avatar.getValue().setStrokeWidth(7);
                avatar.getValue().setStrokeColor(Color.WHITE);
            }
        }
    }

    /** Helper methods */
    private void updateAvatarColors(int color) {
        for (Map.Entry<String, MaterialCardView> avatar : avatarMap.entrySet()) avatar.getValue().setCardBackgroundColor(color);
    }

    public static DialogFragment newInstance() { return new DialogFragment(); }
}
