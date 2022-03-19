package com.example.dotsboxes.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.dotsboxes.MainActivity;
import com.example.dotsboxes.PrefUtility;
import com.example.dotsboxes.R;
import com.example.dotsboxes.databinding.FragmentGameTypeBinding;

public class GameTypeFragment extends Fragment {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public GameTypeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        com.example.dotsboxes.databinding.FragmentGameTypeBinding binding = FragmentGameTypeBinding.inflate(getLayoutInflater());
        sharedPreferences = MainActivity.getContext().getSharedPreferences(PrefUtility.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        View homeView = binding.getRoot();
        Button btnPlayPerson = homeView.findViewById(R.id.btnPlayPerson);
        Button btnPlayComputer = homeView.findViewById(R.id.btnPlayComputer);

        btnPlayPerson.setOnClickListener(view -> {
            newGame(false);
        });

        btnPlayComputer.setOnClickListener(view -> {
            newGame(true);
        });

        return homeView;
    }

    private void newGame(boolean playComputer) {
        editor.putBoolean(PrefUtility.IS_PLAY_COMPUTER, playComputer);
        editor.apply();
        // Create new fragment and transaction
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true);
        // Replace whatever is in the fragment_container view with this fragment
        transaction.replace(R.id.frame_layout, new GameFragment()).addToBackStack(null).commit();
    }
}