package com.startechies.dotsboxes.Fragments;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.startechies.dotsboxes.GameState;
import com.startechies.dotsboxes.PrefUtility;
import com.startechies.dotsboxes.R;
import com.startechies.dotsboxes.databinding.FragmentDialogBinding;
import com.google.android.material.card.MaterialCardView;
import java.util.HashMap;
import java.util.Map;

public class DialogFragment extends androidx.fragment.app.DialogFragment {
    private RadioGroup radioColorA;
    private RadioGroup radioColorB;

    private FragmentDialogBinding binding;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private View dialogView;

    private Map<String, MaterialCardView> avatarMap;
    private Map<String, RadioButton> colorMap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDialogBinding.inflate(getLayoutInflater());
        sharedPreferences = requireContext().getSharedPreferences(PrefUtility.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        dialogView = binding.getRoot();

        binding.etPlayerName.addTextChangedListener(getTextWatcher);
        binding.cvAvatarBangs.setOnClickListener(getListenerAvatars);
        binding.cvAvatarFlower.setOnClickListener(getListenerAvatars);
        binding.cvAvatarBuzz.setOnClickListener(getListenerAvatars);
        binding.cvAvatarBrunette.setOnClickListener(getListenerAvatars);
        binding.cvAvatarCurly.setOnClickListener(getListenerAvatars);
        binding.cvAvatarNeon.setOnClickListener(getListenerAvatars);

        setPlayerName();
        setAvatarMap();
        setAvatar();
        setColorMap();
        setRadioPlayerColor();

        radioColorA.setOnCheckedChangeListener(getListenerRadioColorA);
        radioColorB.setOnCheckedChangeListener(getListenerRadioColorB);
        dialogView.findViewById(R.id.close_corner).setOnClickListener(getListenerDismiss);

        getDialog().setOnCancelListener(dialogInterface -> handleFragment());

        return dialogView;
    }

    private void setAvatarMap() {
        avatarMap = new HashMap<String, MaterialCardView>() {{
            put(PrefUtility.BANGS, dialogView.findViewById(R.id.cvAvatarBangs));
            put(PrefUtility.FLOWER, dialogView.findViewById(R.id.cvAvatarFlower));
            put(PrefUtility.BUZZ, dialogView.findViewById(R.id.cvAvatarBuzz));
            put(PrefUtility.BRUNETTE, dialogView.findViewById(R.id.cvAvatarBrunette));
            put(PrefUtility.CURLY, dialogView.findViewById(R.id.cvAvatarCurly));
            put(PrefUtility.NEON, dialogView.findViewById(R.id.cvAvatarNeon));
        }};
    }

    private void setColorMap() {
        colorMap = new HashMap<String, RadioButton>() {{
            put(PrefUtility.BLUE, dialogView.findViewById(R.id.rbColorBlue));
            put(PrefUtility.RED, dialogView.findViewById(R.id.rbColorRed));
            put(PrefUtility.YELLOW, dialogView.findViewById(R.id.rbColorYellow));
            put(PrefUtility.PINK, dialogView.findViewById(R.id.rbColorPink));
            put(PrefUtility.GREEN, dialogView.findViewById(R.id.rbColorGreen));
            put(PrefUtility.PURPLE, dialogView.findViewById(R.id.rbColorPurple));
        }};
    }

    /** Listeners */
    private final TextWatcher getTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

        @Override
        public void afterTextChanged(Editable editable) {
            String player = (SettingsFragment.isPlayer2()) ? PrefUtility.PLAYER_NAME_2 : PrefUtility.PLAYER_NAME_1;
            String updatedName = binding.etPlayerName.getText().toString();
            editor.putString(player, updatedName);
            editor.apply();

            if (SettingsFragment.isPlayer2()) GameState.getInstance().setP2Name(updatedName);
            else GameState.getInstance().setP1Name(updatedName);

        }
    };

    private final View.OnClickListener getListenerDismiss = view -> {
        handleFragment();
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

        String disableColor = (!SettingsFragment.isPlayer2())
                ? sharedPreferences.getString(PrefUtility.PLAYER_COLOR_2, PrefUtility.DEFAULT_PLAYER_COLOR_2)
                : sharedPreferences.getString(PrefUtility.PLAYER_COLOR_1, PrefUtility.DEFAULT_PLAYER_COLOR_1);

        RadioButton rb = colorMap.get(playerColor);
        RadioButton disabledRb = colorMap.get(disableColor);

        updateAvatarColors(ContextCompat.getColor(requireContext(), PrefUtility.getColor(playerColor)));
        rb.setChecked(true);
        disabledRb.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(requireContext(), R.drawable.ic_disable), null, null);
        disabledRb.setEnabled(false);
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

    private void setPlayerName() {
        String PlayerName = (SettingsFragment.isPlayer2())
                ? sharedPreferences.getString(PrefUtility.PLAYER_NAME_2, PrefUtility.DEFAULT_PLAYER_AVATAR_2)
                : sharedPreferences.getString(PrefUtility.PLAYER_NAME_1, PrefUtility.DEFAULT_PLAYER_AVATAR_1);

        binding.etPlayerName.setText(PlayerName);
        binding.etPlayerName.setVisibility(View.VISIBLE);
    }

    /** Helper methods */
    private void updateAvatarColors(int color) {
        for (Map.Entry<String, MaterialCardView> avatar : avatarMap.entrySet()) avatar.getValue().setCardBackgroundColor(color);
    }

    private void handleFragment() {
        FragmentManager fm = getParentFragmentManager();
        fm.popBackStack();
        FragmentTransaction ft = fm.beginTransaction();
        if (SettingsFragment.isSettings())  {
            ft.replace(R.id.frame_layout, new SettingsFragment()).setReorderingAllowed(true).addToBackStack(null).commit();
        } else {
            ft.replace(R.id.frame_layout, new GameFragment()).setReorderingAllowed(true).addToBackStack(null).commit();
        }
    }
}
