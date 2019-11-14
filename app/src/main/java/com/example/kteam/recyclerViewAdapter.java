package com.example.kteam;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class recyclerViewAdapter extends RecyclerView.Adapter<recyclerViewAdapter.CustomViewHolder> {

    private ArrayList<teamRecyclerData> arrayList;

    public recyclerViewAdapter(ArrayList<teamRecyclerData> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public recyclerViewAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleviewitem,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerViewAdapter.CustomViewHolder holder, int position) {
        holder.name.setText(arrayList.get(position).getName());
        holder.position.setText(arrayList.get(position).getPosition());



    }

    @Override
    public int getItemCount() {
        return (null != arrayList? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView number;
        protected TextView name;
        protected TextView position;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name=(TextView)itemView.findViewById(R.id.teamRecyclerViewName);
            this.position=(TextView)itemView.findViewById(R.id.teamRecyclerViewPosition);
        }
    }
}
