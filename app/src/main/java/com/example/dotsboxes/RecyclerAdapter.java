package com.example.dotsboxes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    //Change GameState to whatever we name Game info
    private ArrayList<GameState> games;

    public RecyclerAdapter(ArrayList<GameState> games){
        this.games = games;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView gameStatus;

        public MyViewHolder(@NonNull final View view) {
            super(view);
            gameStatus = view.findViewById(R.id.status);
        }
    }

    @NonNull
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_of_games, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.MyViewHolder holder, int position) {
        /***Need to replace below .getPlayerName with getPlayerStatus (make one)
        String textStatus = games.get(position).getPlayerName
        holder.gameStatus.setText(textStatus); */
    }

    @Override
    public int getItemCount() {
        return games.size();
    }
}
