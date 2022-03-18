package com.example.dotsboxes.Fragments;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.dotsboxes.R;
import com.example.dotsboxes.databinding.FragmentGameTypeBinding;

public class GameTypeFragment extends Fragment {

    FragmentGameTypeBinding binding;
    Button btnPlayPerson;
    Button btnPlayComputer;

    public GameTypeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGameTypeBinding.inflate(getLayoutInflater());
        View homeView = binding.getRoot();

        btnPlayPerson = homeView.findViewById(R.id.btnPlayPerson);
        btnPlayComputer = homeView.findViewById(R.id.btnPlayComputer);
        btnPlayPerson.setOnClickListener(view -> newGame(false));
        btnPlayComputer.setOnClickListener(view -> newGame(true));

        return homeView;
    }

    private void newGame(boolean playComputer) {
        // Create new fragment and transaction
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true);
        // Replace whatever is in the fragment_container view with this fragment
        transaction.replace(R.id.frame_layout, new GameFragment(playComputer)).addToBackStack(null).commit();
    }
}