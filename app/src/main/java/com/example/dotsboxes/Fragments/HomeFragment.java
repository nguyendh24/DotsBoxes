package com.example.dotsboxes.Fragments;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import com.example.dotsboxes.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;

    public HomeFragment(){ }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        return binding.getRoot();
//        return inflater.inflate(R.layout.fragment_home, container, false);
    }
}