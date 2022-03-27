package com.example.dotsboxes.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import com.example.dotsboxes.GameState;
import com.example.dotsboxes.PrefUtility;
import com.example.dotsboxes.R;
import com.example.dotsboxes.databinding.FragmentGameTypeBinding;

public class GameTypeFragment extends Fragment {

    private com.example.dotsboxes.databinding.FragmentGameTypeBinding binding;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private View homeView;

    public GameTypeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGameTypeBinding.inflate(getLayoutInflater());
        sharedPreferences = getContext().getSharedPreferences(PrefUtility.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        homeView = binding.getRoot();
        animateBackground();
//        animateLogo();

        boolean gameSaved = sharedPreferences.getBoolean(PrefUtility.IS_GAME_SAVED, false);

        if (gameSaved) {
            binding.btnPlayPerson.setVisibility(View.GONE);
            binding.btnPlayComputer.setVisibility(View.GONE);
            binding.btnResumeGame.setVisibility(View.VISIBLE);
            binding.btnNewGame.setVisibility(View.VISIBLE);
        }

        binding.btnPlayPerson.setOnClickListener(view -> newGame(false));
        binding.btnPlayComputer.setOnClickListener(view -> newGame(true));
        binding.btnResumeGame.setOnClickListener(resumeGameListener);
        binding.btnNewGame.setOnClickListener(newGameListener);

        return homeView;
    }

    private final View.OnClickListener resumeGameListener = view -> {
        boolean playComputer = sharedPreferences.getBoolean(PrefUtility.IS_PLAY_COMPUTER, false);
        editor.putBoolean(PrefUtility.USE_SAVED_GAME, true);
        editor.apply();
        newGame(playComputer);
    };

    private final View.OnClickListener newGameListener = view -> {
        editor.remove(PrefUtility.SAVED_GAME);
        editor.putBoolean(PrefUtility.IS_GAME_SAVED, false);
        editor.apply();
        binding.btnPlayPerson.setVisibility(View.VISIBLE);
        binding.btnPlayComputer.setVisibility(View.VISIBLE);
        binding.btnNewGame.setVisibility(View.GONE);
        binding.btnResumeGame.setVisibility(View.GONE);
    };

    private void newGame(boolean playComputer) {
        editor.putBoolean(PrefUtility.IS_PLAY_COMPUTER, playComputer);
        editor.apply();
        GameState.getInstance().resetGame();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.setReorderingAllowed(true);
        transaction.replace(R.id.frame_layout, new GameFragment()).addToBackStack(null).commit();
    }

    private void animateBackground() {
        //animations for star background
        ImageView star1 = homeView.findViewById(R.id.star1);
        ImageView star2 = homeView.findViewById(R.id.star2);
//        ImageView star3 = homeView.findViewById(R.id.star3);
        ImageView star4 = homeView.findViewById(R.id.star4);
        ImageView star5 = homeView.findViewById(R.id.star5);
        ImageView star6 = homeView.findViewById(R.id.star6);
        ImageView star7 = homeView.findViewById(R.id.star7);
//        ImageView star8 = homeView.findViewById(R.id.star8);
        ImageView star9 = homeView.findViewById(R.id.star9);
        ImageView star10 = homeView.findViewById(R.id.star10);
//        ImageView star11 = homeView.findViewById(R.id.star11);
//        ImageView star12 = homeView.findViewById(R.id.star12);
        ImageView star13 = homeView.findViewById(R.id.star13);
        ImageView star14 = homeView.findViewById(R.id.star14);
        ImageView star15 = homeView.findViewById(R.id.star15);
        ImageView star16 = homeView.findViewById(R.id.star16);
        ImageView star17 = homeView.findViewById(R.id.star17);
//        ImageView star18 = homeView.findViewById(R.id.star18);
        ImageView star19 = homeView.findViewById(R.id.star19);
        ImageView star20 = homeView.findViewById(R.id.star20);
//        ImageView star21 = homeView.findViewById(R.id.star21);
        ImageView star22 = homeView.findViewById(R.id.star22);
        ImageView star23 = homeView.findViewById(R.id.star23);
        ImageView star24 = homeView.findViewById(R.id.star24);
        ImageView star25 = homeView.findViewById(R.id.star25);
        ImageView star26 = homeView.findViewById(R.id.star26);
//        ImageView star27 = homeView.findViewById(R.id.star27);
        ImageView star28 = homeView.findViewById(R.id.star28);
        ImageView star29 = homeView.findViewById(R.id.star29);
        ImageView star30 = homeView.findViewById(R.id.star30);

        //actual animation variable
        Animation twinkle1 = AnimationUtils.loadAnimation(getActivity(), R.anim.star1anim);
        Animation twinkle2 = AnimationUtils.loadAnimation(getActivity(), R.anim.star2anim);
        Animation twinkle3 = AnimationUtils.loadAnimation(getActivity(), R.anim.star3anim);
        Animation twinkle4 = AnimationUtils.loadAnimation(getActivity(), R.anim.star4anim);

        //invoke animation on start of view
        star1.startAnimation(twinkle3);
        star2.startAnimation(twinkle2);
//        star3.startAnimation(twinkle3);
        star4.startAnimation(twinkle4);
        star5.startAnimation(twinkle2);
        star6.startAnimation(twinkle2);
        star7.startAnimation(twinkle1);
//        star8.startAnimation(twinkle2);
        star9.startAnimation(twinkle3);
        star10.startAnimation(twinkle4);
//        star11.startAnimation(twinkle2);
//        star12.startAnimation(twinkle3);
        star13.startAnimation(twinkle1);
        star14.startAnimation(twinkle2);
        star15.startAnimation(twinkle3);
        star16.startAnimation(twinkle4);
        star17.startAnimation(twinkle2);
//        star18.startAnimation(twinkle3);
        star19.startAnimation(twinkle1);
        star20.startAnimation(twinkle2);
//        star21.startAnimation(twinkle3);
        star22.startAnimation(twinkle4);
        star23.startAnimation(twinkle2);
        star24.startAnimation(twinkle3);
        star25.startAnimation(twinkle1);
        star26.startAnimation(twinkle4);
//        star27.startAnimation(twinkle4);
        star28.startAnimation(twinkle4);
        star29.startAnimation(twinkle4);
        star30.startAnimation(twinkle4);
    }

    private void animateLogo() {
        //logo animation
        AnimationDrawable logoAnim;
        ImageView logoImageView = homeView.findViewById(R.id.logo);
        logoImageView.setBackgroundResource(R.drawable.logo_anim);
        logoAnim = (AnimationDrawable) logoImageView.getBackground();
        logoAnim.start();
    }
}