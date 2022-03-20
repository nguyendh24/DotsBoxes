package com.example.dotsboxes.Fragments;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.dotsboxes.PrefUtility;
import com.example.dotsboxes.R;
import com.example.dotsboxes.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    Button btnStartGame;
    static TextView tvPlayerName;
    static SharedPreferences sharedPreferences;

    public HomeFragment(){ }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        View homeView = binding.getRoot();
        btnStartGame = homeView.findViewById(R.id.btnStartGame);
        tvPlayerName = homeView.findViewById(R.id.tvPlayerName);
        setTvPlayerName();

        btnStartGame.setOnClickListener(view -> {
            // Create new fragment and transaction
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setReorderingAllowed(true);
            // Replace whatever is in the fragment_container view with this fragment
            transaction.replace(R.id.frame_layout, new GameTypeFragment(), null);
            // Commit the transaction
            transaction.commit();
        });
        return homeView;
    }

    private void setTvPlayerName() {
        sharedPreferences = getActivity().getSharedPreferences(PrefUtility.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        tvPlayerName.setText(sharedPreferences.getString(PrefUtility.PLAYER_NAME, PrefUtility.DEFAULT_PLAYER_NAME));
    }

    public static String getTvPlayerName() {
        return sharedPreferences.getString(PrefUtility.PLAYER_NAME, PrefUtility.DEFAULT_PLAYER_NAME);
    }
}