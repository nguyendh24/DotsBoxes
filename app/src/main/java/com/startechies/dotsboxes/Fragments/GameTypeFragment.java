package com.startechies.dotsboxes.Fragments;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import com.startechies.dotsboxes.GameState;
import com.startechies.dotsboxes.PrefUtility;
import com.startechies.dotsboxes.R;
import com.startechies.dotsboxes.databinding.FragmentGameTypeBinding;

public class GameTypeFragment extends Fragment {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private View homeView;

    private ImageView rocket;

    public GameTypeFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        com.startechies.dotsboxes.databinding.FragmentGameTypeBinding binding = FragmentGameTypeBinding.inflate(getLayoutInflater());
        sharedPreferences = requireContext().getSharedPreferences(PrefUtility.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        homeView = binding.getRoot();
        animateBackground();
        animateLogo();
        rocketShip();

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
                homeView.findViewById(R.id.star5),
                homeView.findViewById(R.id.star6),
                homeView.findViewById(R.id.star14),
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
        };

        for (ImageView star : stars) { star.startAnimation(twinkle); }
    }

    private void animateStars4() {
        Animation twinkle = AnimationUtils.loadAnimation(getActivity(), R.anim.star4anim);

        ImageView[] stars = {
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
        AnimationDrawable logoAnim;
        ImageView logoImageView = homeView.findViewById(R.id.logo);
        logoImageView.setBackgroundResource(R.drawable.logo_anim);
        logoAnim = (AnimationDrawable) logoImageView.getBackground();
        logoAnim.start();
    }

    private void rocketShip(){
        AnimatorSet animSetXY = new AnimatorSet();
        rocket = homeView.findViewById(R.id.rocket);
        ObjectAnimator x = ObjectAnimator.ofFloat(rocket, "translationX", 1500f);
        ObjectAnimator y = ObjectAnimator.ofFloat(rocket, "translationY", -1500f);
        animSetXY.playTogether(x,y);
        animSetXY.setDuration(5500);
        animSetXY.start();
    }
}