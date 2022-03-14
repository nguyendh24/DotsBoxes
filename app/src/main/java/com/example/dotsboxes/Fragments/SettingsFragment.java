package com.example.dotsboxes.Fragments;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.dotsboxes.MainActivity;
import com.example.dotsboxes.R;
import com.example.dotsboxes.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {
    private FragmentSettingsBinding binding;

    private RadioGroup radioGrid;
    private RadioGroup radioVertices;
    private RadioGroup radioPlayerColor;

    private RadioButton rbGrid4;
    private RadioButton rbGrid5;
    private RadioButton rbGrid6;

    private RadioButton rbColorRB;
    private RadioButton rbColorPY;
    private RadioButton rbColorPG;

    private RadioButton rbDot;
    private RadioButton rbTriangle;
    private RadioButton rbStar;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SettingsFragment(){ }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(getLayoutInflater());
        sharedPreferences = MainActivity.getContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        View settingsView = binding.getRoot();
        setRadioGrid(settingsView);
        setRadioVertices(settingsView);
        setRadioPlayerColor(settingsView);
        editor.apply();

        binding.radioVertices.setOnCheckedChangeListener((radioGroup, i) -> {
            int btnID = radioGroup.getCheckedRadioButtonId();
            if (btnID == R.id.rbDot) { editor.putString("vertex", "dot"); }
            else if (btnID == R.id.rbTriangle) { editor.putString("vertex", "triangle"); }
            else { editor.putString("vertex", "star"); }
            editor.apply();
        });

        binding.radioPlayerColor.setOnCheckedChangeListener((radioGroup, i) -> {
            int btnID = radioGroup.getCheckedRadioButtonId();
            if (btnID == R.id.rbColorRB) { editor.putString("playerColor", "RB"); }
            else if (btnID == R.id.rbColorPY) { editor.putString("playerColor", "PY"); }
            else { editor.putString("playerColor", "PG"); }
            editor.apply();
        });

        return settingsView;
    }

    private void setRadioGrid(View settingsView) {
        radioGrid = settingsView.findViewById(R.id.radioGrid);
        rbGrid4 = settingsView.findViewById(R.id.rbGrid4);

        int boardSize = sharedPreferences.getInt("boardSize", 0);
        if (boardSize == 4) { rbGrid4.setChecked(true); }
        else if (boardSize == 5) { rbGrid5.setChecked(true); }
        else { rbGrid6.setChecked(true); }
    }

    private void setRadioVertices(View settingsView) {
        radioVertices = settingsView.findViewById(R.id.radioVertices);
        rbDot = settingsView.findViewById(R.id.rbDot);
        rbTriangle = settingsView.findViewById(R.id.rbTriangle);
        rbStar = settingsView.findViewById(R.id.rbStar);

        String vertex = sharedPreferences.getString("vertex", "");
        if (vertex.equals("dot")) { rbDot.setChecked(true); }
        else if (vertex.equals("triangle")) { rbTriangle.setChecked(true); }
        else { rbStar.setChecked(true); }
    }

    private void setRadioPlayerColor(View settingsView) {
        radioPlayerColor = settingsView.findViewById(R.id.radioPlayerColor);
        rbColorRB = settingsView.findViewById(R.id.rbColorRB);
        rbColorPY = settingsView.findViewById(R.id.rbColorPY);
        rbColorPG = settingsView.findViewById(R.id.rbColorPG);

        String playerColor = sharedPreferences.getString("playerColor", "");
        if (playerColor.equals("RB")) { rbColorRB.setChecked(true); }
        else if (playerColor.equals("PG")) { rbColorPG.setChecked(true); }
        else { rbColorPY.setChecked(true); }
    }
}
