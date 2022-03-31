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
        animateLogo();

        boolean gameSaved = sharedPreferences.getBoolean(PrefUtility.IS_GAME_SAVED, false);

        if (gameSaved) { binding.btnResumeGame.setVisibility(View.VISIBLE); }
        else { binding.btnResumeGame.setVisibility(View.INVISIBLE); }

        binding.btnPlayPerson.setOnClickListener(view -> newGame(false, false));
        binding.btnPlayComputer.setOnClickListener(view -> newGame(true, false));
        binding.btnResumeGame.setOnClickListener(resumeGameListener);

        return homeView;
    }

    private final View.OnClickListener resumeGameListener = view -> {
        boolean playComputer = sharedPreferences.getBoolean(PrefUtility.IS_PLAY_COMPUTER, false);
        editor.putBoolean(PrefUtility.USE_SAVED_GAME, true);
        editor.apply();
        newGame(playComputer, true);
    };

    private void newGame(boolean playComputer, boolean isGameSaved) {
        editor.putBoolean(PrefUtility.IS_PLAY_COMPUTER, playComputer);
        editor.putBoolean(PrefUtility.IS_GAME_SAVED, isGameSaved);
        editor.apply();
        GameState.getInstance().resetGame();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.setReorderingAllowed(true);
        transaction.replace(R.id.frame_layout, new GameFragment()).addToBackStack(null).commit();
    }

    private void animateBackground() {
        animateStars1();
        animateStars2();
        animateStars3();
        animateStars4();
    }

    private void animateStars1() {
        Animation twinkle = AnimationUtils.loadAnimation(getActivity(), R.anim.star1anim);

        ImageView[] stars = {
                homeView.findViewById(R.id.star7),
                homeView.findViewById(R.id.star13),
                homeView.findViewById(R.id.star19),
                homeView.findViewById(R.id.star24),
        };

        for (ImageView star : stars) { star.startAnimation(twinkle); }
    }

    private void animateStars2() {
        Animation twinkle = AnimationUtils.loadAnimation(getActivity(), R.anim.star2anim);

        ImageView[] stars = {
//                homeView.findViewById(R.id.star2),
                homeView.findViewById(R.id.star5),
                homeView.findViewById(R.id.star6),
                homeView.findViewById(R.id.star14),
//                homeView.findViewById(R.id.star17),
//                homeView.findViewById(R.id.star20),
                homeView.findViewById(R.id.star25),
        };

        for (ImageView star : stars) { star.startAnimation(twinkle); }
    }

    private void animateStars3() {
        Animation twinkle = AnimationUtils.loadAnimation(getActivity(), R.anim.star3anim);

        ImageView[] stars = {
                homeView.findViewById(R.id.star1),
                homeView.findViewById(R.id.star9),
                homeView.findViewById(R.id.star12),
                homeView.findViewById(R.id.star15),
//                homeView.findViewById(R.id.star23),
        };

        for (ImageView star : stars) { star.startAnimation(twinkle); }
    }

    private void animateStars4() {
        Animation twinkle = AnimationUtils.loadAnimation(getActivity(), R.anim.star4anim);

        ImageView[] stars = {
//                homeView.findViewById(R.id.star4),
//                homeView.findViewById(R.id.star10),
                homeView.findViewById(R.id.star16),
                homeView.findViewById(R.id.star22),
                homeView.findViewById(R.id.star26),
                homeView.findViewById(R.id.star28),
                homeView.findViewById(R.id.star29),
                homeView.findViewById(R.id.star30),
        };

        for (ImageView star : stars) { star.startAnimation(twinkle); }
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