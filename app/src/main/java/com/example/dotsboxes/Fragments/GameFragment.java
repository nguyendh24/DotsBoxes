package com.example.dotsboxes.Fragments;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dotsboxes.R;
import com.example.dotsboxes.Views.GameView;
import com.example.dotsboxes.databinding.FragmentGameBinding;

public class GameFragment extends Fragment {
    private FragmentGameBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentGameBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        GameView gameView = view.findViewById(R.id.gameView);
        TextView p1Score = view.findViewById(R.id.tvP1Score);
        TextView p2Score = view.findViewById(R.id.tvP2Score);
        TextView p1Name = view.findViewById(R.id.tvP1Name);
        TextView p2Name = view.findViewById(R.id.tvP2Name);
        TextView statusDisplay = view.findViewById(R.id.tvCurrentTurn);
        gameView.setUpTextViews(p1Score, p2Score, p1Name, p2Name, statusDisplay);
        return view;
//        return inflater.inflate(R.layout.fragment_game, container, false);
    }

}