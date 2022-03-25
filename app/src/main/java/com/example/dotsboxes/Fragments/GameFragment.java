package com.example.dotsboxes.Fragments;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.dotsboxes.PrefUtility;
import com.example.dotsboxes.MainActivity;
import com.example.dotsboxes.R;
import com.example.dotsboxes.Views.GameView;
import com.example.dotsboxes.databinding.FragmentGameBinding;

public class GameFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        com.example.dotsboxes.databinding.FragmentGameBinding binding = FragmentGameBinding.inflate(getLayoutInflater());
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
        Button btnPlayAgain = myView.findViewById(R.id.btnPlayAgain);
        Button btnQuitGame = myView.findViewById(R.id.btnQuitGame);
        CardView cvP1 = myView.findViewById(R.id.cvP1);
        CardView cvP2 = myView.findViewById(R.id.cvP2);
        setPlayerColors(cvP1, cvP2, p1Name, p2Name);
        setPlayerAvatars(ivP1, ivP2);

        gameView.setUpReferences(
                this,
                p1Score,
                p2Score,
                p1Name,
                p2Name,
                statusDisplay,
                btnPlayAgain,
                btnQuitGame,
                p1Turn,
                p2Turn
        );
        return myView;
    }

    private void setPlayerAvatars(ImageView ivP1, ImageView ivP2) {
        SharedPreferences sharedPreferences = MainActivity.getContext().getSharedPreferences(PrefUtility.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        boolean playComputer = sharedPreferences.getBoolean(PrefUtility.IS_PLAY_COMPUTER, false);

        String playerAvatar1 = sharedPreferences.getString(PrefUtility.PLAYER_AVATAR_1, PrefUtility.DEFAULT_PLAYER_AVATAR_1);
        String playerAvatar2 = sharedPreferences.getString(PrefUtility.PLAYER_AVATAR_2, PrefUtility.DEFAULT_PLAYER_AVATAR_2);

        int resID = (playComputer) ? R.drawable.ic_robot : PrefUtility.getAvatar(playerAvatar2);
        ivP1.setImageResource(PrefUtility.getAvatar(playerAvatar1));
        ivP2.setImageResource(resID);
    }

    private void setPlayerColors(CardView cvP1, CardView cvP2, TextView tvP1, TextView tvP2) {
        SharedPreferences sharedPreferences = MainActivity.getContext().getSharedPreferences(PrefUtility.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String playerColor1 = sharedPreferences.getString(PrefUtility.PLAYER_COLOR_1, "");
        String playerColor2 = sharedPreferences.getString(PrefUtility.PLAYER_COLOR_2, "");

        cvP1.setCardBackgroundColor(getResources().getColor(PrefUtility.getColor(playerColor1)));
        cvP2.setCardBackgroundColor(getResources().getColor(PrefUtility.getColor(playerColor2)));
        tvP1.setTextColor(getResources().getColor(PrefUtility.getColor(playerColor1)));
        tvP2.setTextColor(getResources().getColor(PrefUtility.getColor(playerColor2)));
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