package com.example.kteam;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class teamMatchrecyclerViewAdapter extends RecyclerView.Adapter<teamMatchrecyclerViewAdapter.CustomViewHolder> {

    private ArrayList<teamMatchData> arrayList;


    public teamMatchrecyclerViewAdapter(ArrayList<teamMatchData> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public teamMatchrecyclerViewAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerviewmatchview, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull teamMatchrecyclerViewAdapter.CustomViewHolder holder, int position) {
        holder.myTeam.setText(arrayList.get(position).getMyTeam());
        holder.Opposingteam.setText(arrayList.get(position).getOpposingteam());


    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView myTeam;
        protected TextView Opposingteam;

        public CustomViewHolder(@NonNull final View itemView) {
            super(itemView);
            this.myTeam = (TextView) itemView.findViewById(R.id.myTeam);
            this.Opposingteam = (TextView) itemView.findViewById(R.id.Opposingteam);


        }
    }
}

