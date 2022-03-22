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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.example.dotsboxes.GameState;
import com.example.dotsboxes.MainActivity;
import com.example.dotsboxes.PrefUtility;
import com.example.dotsboxes.R;
import com.example.dotsboxes.databinding.FragmentGameTypeBinding;

public class GameTypeFragment extends Fragment {

    private SharedPreferences.Editor editor;

    public GameTypeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        com.example.dotsboxes.databinding.FragmentGameTypeBinding binding = FragmentGameTypeBinding.inflate(getLayoutInflater());
        SharedPreferences sharedPreferences = MainActivity.getContext().getSharedPreferences(PrefUtility.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        View homeView = binding.getRoot();

        Button btnPlayPerson = homeView.findViewById(R.id.btnPlayPerson);
        //add sliding animation to buttons
        btnPlayPerson.setAlpha(0f);
        btnPlayPerson.setTranslationY(50);
        btnPlayPerson.animate().alpha(1f).translationYBy(-50).setDuration(1500);

        Button btnPlayComputer = homeView.findViewById(R.id.btnPlayComputer);
        //add sliding animation to buttons
        btnPlayComputer.setAlpha(0f);
        btnPlayComputer.setTranslationY(50);
        btnPlayComputer.animate().alpha(1f).translationYBy(-50).setDuration(1500);

        btnPlayPerson.setOnClickListener(view -> newGame(false));
        btnPlayComputer.setOnClickListener(view -> newGame(true));

        //animations for star background
        ImageView star1 = homeView.findViewById(R.id.star1);
        ImageView star2 = homeView.findViewById(R.id.star2);
        ImageView star3 = homeView.findViewById(R.id.star3);
        ImageView star4 = homeView.findViewById(R.id.star4);
        ImageView star5 = homeView.findViewById(R.id.star5);
        ImageView star6 = homeView.findViewById(R.id.star6);
        ImageView star7 = homeView.findViewById(R.id.star7);
        ImageView star8 = homeView.findViewById(R.id.star8);
        ImageView star9 = homeView.findViewById(R.id.star9);
        ImageView star10 = homeView.findViewById(R.id.star10);
        ImageView star11 = homeView.findViewById(R.id.star11);
        ImageView star12 = homeView.findViewById(R.id.star12);
        ImageView star13 = homeView.findViewById(R.id.star13);
        ImageView star14 = homeView.findViewById(R.id.star14);
        ImageView star15 = homeView.findViewById(R.id.star15);
        ImageView star16 = homeView.findViewById(R.id.star16);
        ImageView star17 = homeView.findViewById(R.id.star17);
        ImageView star18 = homeView.findViewById(R.id.star18);
        ImageView star19 = homeView.findViewById(R.id.star19);
        ImageView star20 = homeView.findViewById(R.id.star20);
        ImageView star21 = homeView.findViewById(R.id.star21);
        ImageView star22 = homeView.findViewById(R.id.star22);
        ImageView star23 = homeView.findViewById(R.id.star23);
        ImageView star24 = homeView.findViewById(R.id.star24);
        ImageView star25 = homeView.findViewById(R.id.star25);

        Animation twinkle1 = AnimationUtils.loadAnimation(getActivity(), R.anim.star1anim);
        Animation twinkle2 = AnimationUtils.loadAnimation(getActivity(), R.anim.star2anim);
        Animation twinkle3 = AnimationUtils.loadAnimation(getActivity(), R.anim.star3anim);

        star1.startAnimation(twinkle1);
        star2.startAnimation(twinkle2);
        star3.startAnimation(twinkle3);
        star4.startAnimation(twinkle1);
        star5.startAnimation(twinkle2);
        star6.startAnimation(twinkle3);
        star7.startAnimation(twinkle1);
        star8.startAnimation(twinkle2);
        star9.startAnimation(twinkle3);
        star10.startAnimation(twinkle1);
        star11.startAnimation(twinkle2);
        star12.startAnimation(twinkle3);
        star13.startAnimation(twinkle1);
        star14.startAnimation(twinkle2);
        star15.startAnimation(twinkle3);
        star16.startAnimation(twinkle1);
        star17.startAnimation(twinkle2);
        star18.startAnimation(twinkle3);
        star19.startAnimation(twinkle1);
        star20.startAnimation(twinkle2);
        star21.startAnimation(twinkle3);
        star22.startAnimation(twinkle1);
        star23.startAnimation(twinkle2);
        star24.startAnimation(twinkle3);
        star25.startAnimation(twinkle1);

        return homeView;
    }

    private void newGame(boolean playComputer) {
        editor.putBoolean(PrefUtility.IS_PLAY_COMPUTER, playComputer);
        editor.apply();
        GameState.getInstance().resetGame();
        // Create new fragment and transaction
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true);
        // Replace whatever is in the fragment_container view with this fragment
        transaction.replace(R.id.frame_layout, new GameFragment()).addToBackStack(null).commit();
    }
}