package com.example.kteam;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class findteamrecyclerViewAdapter extends RecyclerView.Adapter<findteamrecyclerViewAdapter.CustomViewHolder> {

    private ArrayList<findteamData> arrayList;
    private View itemView;


    public findteamrecyclerViewAdapter(ArrayList<findteamData> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public findteamrecyclerViewAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerfindteam, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull findteamrecyclerViewAdapter.CustomViewHolder holder, int position) {

        holder.name.setText(arrayList.get(position).getName());
        holder.location.setText(arrayList.get(position).getLocation());
        holder.teamnumber.setText(arrayList.get(position).getTeamnumber());
        itemView= holder.itemView;




    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView name;
        protected TextView teamnumber;
        protected TextView location;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.findteamName);
            this.teamnumber = (TextView) itemView.findViewById(R.id.findteamTeamMembers);
            this.location = (TextView) itemView.findViewById(R.id.findteamlocation);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    findteamData item=null;
                    int pos  =getAdapterPosition();

                    if(pos!= RecyclerView.NO_POSITION){
                         item =arrayList.get(pos);
                    }
                    Intent intent = new Intent(v.getContext(),teampopup.class);
                    intent.putExtra("teamName",item.getName());
                    v.getContext().startActivity(intent);
                }
            });

        }
    }


}

