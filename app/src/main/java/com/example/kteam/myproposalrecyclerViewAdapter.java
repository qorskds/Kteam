package com.example.kteam;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class myproposalrecyclerViewAdapter extends RecyclerView.Adapter<myproposalrecyclerViewAdapter.CustomViewHolder> {

    private ArrayList<myproposalData> arrayList;


    public myproposalrecyclerViewAdapter(ArrayList<myproposalData> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public myproposalrecyclerViewAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclermyproposal, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view,parent,this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull myproposalrecyclerViewAdapter.CustomViewHolder holder, int position) {
        holder.teamName.setText(arrayList.get(position).getTeamName());
        holder.stadium.setText(arrayList.get(position).getStaditum());


    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView teamName;
        protected TextView stadium;
        protected myproposalData item;

        private Map<String,Object > tmp;


        public CustomViewHolder(@NonNull final View itemView, final ViewGroup group, final myproposalrecyclerViewAdapter adapter) {
            super(itemView);
            this.teamName = (TextView) itemView.findViewById(R.id.myproposalrecyclerTeamName);
            this.stadium = (TextView) itemView.findViewById(R.id.myproposalrecyclerstadium);

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
                    Intent intent= new Intent(v.getContext(),ProPosePopup.class);
                    intent.putExtra("teamName",item.getTeamName());

                    v.getContext().startActivity(intent);



                }
            });

        }
    }


}

