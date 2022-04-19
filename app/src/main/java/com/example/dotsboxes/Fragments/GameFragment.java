package com.example.dotsboxes.Fragments;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.dotsboxes.PrefUtility;
import com.example.dotsboxes.R;
import com.example.dotsboxes.Views.GameView;
import com.example.dotsboxes.databinding.FragmentGameBinding;
import nl.dionsegijn.konfetti.xml.KonfettiView;

public class GameFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentGameBinding binding = FragmentGameBinding.inflate(getLayoutInflater());
        View myView = binding.getRoot();
        GameView gameView = myView.findViewById(R.id.gameView);
        TextView p1Score = myView.findViewById(R.id.tvP1Score);
        TextView p2Score = myView.findViewById(R.id.tvP2Score);
        TextView p1Name = myView.findViewById(R.id.tvP1Name);
        TextView p2Name = myView.findViewById(R.id.tvP2Name);
        TextView statusDisplay = myView.findViewById(R.id.tvCurrentTurn);
        ImageView p1Turn = myView.findViewById(R.id.ivP1Turn);
        ImageView p2Turn = myView.findViewById(R.id.ivP2Turn);
        ImageView ivP1 = myView.findViewById(R.id.ivP1);
        ImageView ivP2 = myView.findViewById(R.id.ivP2);
        ImageView ivWinner = myView.findViewById(R.id.ivWinner);
        KonfettiView konfettiView = myView.findViewById(R.id.konfettiView);

        ivWinner.setVisibility(View.GONE);

        Button btnResetGame = myView.findViewById(R.id.btnResetGame);
        CardView cvP1 = myView.findViewById(R.id.cvP1);
        CardView cvP2 = myView.findViewById(R.id.cvP2);
        setPlayerColors(cvP1, cvP2, p1Name, p2Name);
        setPlayerAvatars(ivP1, ivP2);

        gameView.setUpReferences(
                p1Score,
                p2Score,
                p1Name,
                p2Name,
                statusDisplay,
                btnResetGame,
                p1Turn,
                p2Turn,
                ivWinner,
                konfettiView
        );

        binding.cvP1.setOnClickListener(getListenerCvP1);
        binding.cvP2.setOnClickListener(getListenerCvP2);

        binding.btnHelp.setOnClickListener(view -> showHelpDialog());

        binding.btnSettings.setOnClickListener(view -> replaceFragment(new SettingsFragment()));
        binding.btnExitGame.setOnClickListener(view -> replaceFragment(new GameTypeFragment()));

        return myView;
    }

    /** Listeners */
    private final View.OnClickListener getListenerCvP1 = view -> { SettingsFragment.setIsPlayer2(false); avatarColorDialog(); };

    private final View.OnClickListener getListenerCvP2 = view -> { SettingsFragment.setIsPlayer2(true); avatarColorDialog(); };

    private void setPlayerAvatars(ImageView ivP1, ImageView ivP2) {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(PrefUtility.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        boolean playComputer = sharedPreferences.getBoolean(PrefUtility.IS_PLAY_COMPUTER, false);

        String playerAvatar1 = sharedPreferences.getString(PrefUtility.PLAYER_AVATAR_1, PrefUtility.DEFAULT_PLAYER_AVATAR_1);
        String playerAvatar2 = sharedPreferences.getString(PrefUtility.PLAYER_AVATAR_2, PrefUtility.DEFAULT_PLAYER_AVATAR_2);

        int resID = (playComputer) ? R.drawable.ic_robot : PrefUtility.getAvatar(playerAvatar2);
        ivP1.setImageResource(PrefUtility.getAvatar(playerAvatar1));
        ivP2.setImageResource(resID);
    }

    private void setPlayerColors(CardView cvP1, CardView cvP2, TextView tvP1, TextView tvP2) {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(PrefUtility.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        boolean playComputer = sharedPreferences.getBoolean(PrefUtility.IS_PLAY_COMPUTER, false);

        String playerColor1 = sharedPreferences.getString(PrefUtility.PLAYER_COLOR_1, "");
        String playerColor2 = (playComputer) ? PrefUtility.COMPUTER_COLOR : sharedPreferences.getString(PrefUtility.PLAYER_COLOR_2, "");

        cvP1.setCardBackgroundColor(ContextCompat.getColor(requireContext(), PrefUtility.getColor(playerColor1)));
        if (playComputer) {
            cvP2.setCardBackgroundColor(ContextCompat.getColor(requireContext(), PrefUtility.getColor(PrefUtility.COMPUTER_COLOR_DARK)));
            cvP2.setEnabled(false);
        } else {
            cvP2.setCardBackgroundColor(ContextCompat.getColor(requireContext(), PrefUtility.getColor(playerColor2)));
            cvP2.setEnabled(true);
        }

        tvP1.setTextColor(ContextCompat.getColor(requireContext(), PrefUtility.getColor(playerColor1)));
        tvP2.setTextColor(ContextCompat.getColor(requireContext(), PrefUtility.getColor(playerColor2)));
    }

    private void avatarColorDialog() {
        SettingsFragment.setIsSettings(false);
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        DialogFragment dialogFragment = new DialogFragment();
        dialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFragmentTheme);
        dialogFragment.show(fragmentTransaction, "dialog");
    }

    private void showHelpDialog() {
        Dialog dialog = new Dialog(getContext(), R.style.DialogFragmentTheme);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        dialog.setContentView(R.layout.dialog_help);
        dialog.show();
        dialog.findViewById(R.id.close_corner).setOnClickListener(view -> dialog.dismiss());
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment).setReorderingAllowed(true).addToBackStack(null).commit();
    }

    public static void animateTurn(ImageView visiblePlayer, ImageView invisiblePlayer) {
        invisiblePlayer.setVisibility(View.INVISIBLE);
        visiblePlayer.setVisibility(View.VISIBLE);

        Animation mAnimation = new TranslateAnimation(
                TranslateAnimation.ABSOLUTE, 0f,
                TranslateAnimation.ABSOLUTE, 0f,
                TranslateAnimation.RELATIVE_TO_PARENT, -0.1f,
                TranslateAnimation.RELATIVE_TO_PARENT, 0.05f);
        mAnimation.setDuration(800);
        mAnimation.setRepeatCount(-1);
        mAnimation.setRepeatMode(Animation.REVERSE);
        mAnimation.setInterpolator(new LinearInterpolator());

        invisiblePlayer.clearAnimation();
        visiblePlayer.setAnimation(mAnimation);
    }

}