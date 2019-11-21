package com.example.kteam;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class teamMatchingConfirmrecyclerViewAdapter extends RecyclerView.Adapter<teamMatchingConfirmrecyclerViewAdapter.CustomViewHolder> {

    private ArrayList<teamMatchingConfrimData> arrayList;


    public teamMatchingConfirmrecyclerViewAdapter(ArrayList<teamMatchingConfrimData> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public teamMatchingConfirmrecyclerViewAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerconfirmmatchingview, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull teamMatchingConfirmrecyclerViewAdapter.CustomViewHolder holder, int position) {
        holder.recyclerconfirmmatchingviewName.setText(arrayList.get(position).getName());
        holder.recyclerconfirmmatchingviewLocation.setText(arrayList.get(position).getStadium());


    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView recyclerconfirmmatchingviewName;
        protected TextView recyclerconfirmmatchingviewLocation;
        private teamMatchingConfrimData item;
        private Map<String,Object> tmp;


        public CustomViewHolder(@NonNull final View itemView) {
            super(itemView);
            this.recyclerconfirmmatchingviewName = (TextView) itemView.findViewById(R.id.recyclerconfirmmatchingviewName);
            this.recyclerconfirmmatchingviewLocation = (TextView) itemView.findViewById(R.id.recyclerconfirmmatchingviewLocation);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    item = null;
                    int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION) {
                        item = arrayList.get(pos);
                        Intent intent = new Intent(view.getContext(),MatchingTeamInformation.class);
                        intent.putExtra("name",item.name);
                        intent.putExtra("stadium",item.stadium);
                        intent.putExtra("information",item.teamInformation);

                        view.getContext().startActivity(intent);


                    }
                }
            });
        }
    }
}

