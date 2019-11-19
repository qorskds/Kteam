package com.example.kteam;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class findplayerrecyclerViewAdapter extends RecyclerView.Adapter<findplayerrecyclerViewAdapter.CustomViewHolder> {

    private ArrayList<findplayerData> arrayList;


    public findplayerrecyclerViewAdapter(ArrayList<findplayerData> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public findplayerrecyclerViewAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerfindplayer,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull findplayerrecyclerViewAdapter.CustomViewHolder holder, int position) {

        holder.name.setText(arrayList.get(position).getName());
        holder.position.setText(arrayList.get(position).getPostion());
        holder.bulid.setText(arrayList.get(position).getBulid());
        holder.character.setText(arrayList.get(position).getCharacter());
        holder.location.setText(arrayList.get(position).getLocation());
        holder.height.setText(arrayList.get(position).getHeight());




    }
    @Override
    public int getItemCount() {
        return (null != arrayList? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView name;
        protected TextView position;
        protected TextView height;
        protected TextView bulid;
        protected TextView character;
        protected TextView location;
        private  findplayerData item;
        private Map<String,Object> tmp;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name=(TextView)itemView.findViewById(R.id.findplayerRecyclernickname);
            this.height=(TextView)itemView.findViewById(R.id.findplayerRecyclerHeight);
            this.bulid=(TextView)itemView.findViewById(R.id.findplayerRecyclerBuild);
            this.position=(TextView)itemView.findViewById(R.id.findplayerRecyclerPosition);
            this.character=(TextView)itemView.findViewById(R.id.findplayerRecyclerCharacter);
            this.location=(TextView)itemView.findViewById(R.id.findplayerRecyclerLocation);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    item = null;
                    int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION) {
                        item = arrayList.get(pos);
                        tmp = new HashMap<>();
                        tmp.put("read", "true");
                    }
                    Intent intent= new Intent(v.getContext(),PlayerPopup.class);
                    intent.putExtra("uid",item.getUid());

                    v.getContext().startActivity(intent);



                }
            });
        }
    }
}
