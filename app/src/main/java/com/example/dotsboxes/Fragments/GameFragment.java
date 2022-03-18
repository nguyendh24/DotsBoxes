package com.example.dotsboxes.Fragments;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.dotsboxes.PrefUtility;
import com.example.dotsboxes.MainActivity;
import com.example.dotsboxes.R;
import com.example.dotsboxes.Views.GameView;
import com.example.dotsboxes.databinding.FragmentGameBinding;

public class GameFragment extends Fragment {
    private boolean playComputer;
    private GameView gameView;

    public GameFragment() {
        this(false);
    }

    public GameFragment(boolean playComputer) {
        this.playComputer = playComputer;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        com.example.dotsboxes.databinding.FragmentGameBinding binding = FragmentGameBinding.inflate(getLayoutInflater());
        View myView = binding.getRoot();
        gameView = myView.findViewById(R.id.gameView);
        TextView p1Score = myView.findViewById(R.id.tvP1Score);
        TextView p2Score = myView.findViewById(R.id.tvP2Score);
        TextView p1Name = myView.findViewById(R.id.tvP1Name);
        TextView p2Name = myView.findViewById(R.id.tvP2Name);
        TextView statusDisplay = myView.findViewById(R.id.tvCurrentTurn);
        Button btnPlayAgain = myView.findViewById(R.id.btnPlayAgain);
        CardView cvP1 = myView.findViewById(R.id.cvP1);
        CardView cvP2 = myView.findViewById(R.id.cvP2);
        setCardViews(cvP1, cvP2);

        btnPlayAgain.setOnClickListener(view -> gameView.resetGame());

        if (playComputer) { ImageView ivP2 = myView.findViewById(R.id.ivP2); ivP2.setImageResource(R.drawable.ic_robot); }
        gameView.setPlayComputer(playComputer);
        gameView.setUpReferences(p1Score, p2Score, p1Name, p2Name, statusDisplay, btnPlayAgain);
        return myView;
    }

    private void setCardViews(CardView cvP1, CardView cvP2) {
        SharedPreferences sharedPreferences = MainActivity.getContext().getSharedPreferences(PrefUtility.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String playerColor1 = sharedPreferences.getString(PrefUtility.PLAYER_COLOR_1, PrefUtility.DEFAULT_PLAYER_COLOR_1);
        String playerColor2 = sharedPreferences.getString(PrefUtility.PLAYER_COLOR_2, PrefUtility.DEFAULT_PLAYER_COLOR_2);

        cvP1.setCardBackgroundColor(getResources().getColor(PrefUtility.getColor(playerColor1)));
        cvP2.setCardBackgroundColor(getResources().getColor(PrefUtility.getColor(playerColor2)));

    }

}