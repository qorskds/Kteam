package com.example.kteam;

import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

public class TeamcompetitonrecyclerViewAdapter extends RecyclerView.Adapter<TeamcompetitonrecyclerViewAdapter.CustomViewHolder> {

    private ArrayList<TeamcompetitionData> arrayList;


    public TeamcompetitonrecyclerViewAdapter(ArrayList<TeamcompetitionData> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public TeamcompetitonrecyclerViewAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerteamcompetiton, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TeamcompetitonrecyclerViewAdapter.CustomViewHolder holder, int position) {
        holder.recyclerteamcompetitionteamName.setText(arrayList.get(position).getName());
        holder.recyclerteamcompetitionTime.setText(arrayList.get(position).getTime());
        holder.recyclerteamcompetitionArea.setText(arrayList.get(position).getArea());
        holder.recyclerteamcompetitionLocation.setText(arrayList.get(position).getLocation());
        holder.recyclerteamcompetitionComent.setText(arrayList.get(position).getComment());


    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView recyclerteamcompetitionteamName;
        protected TextView recyclerteamcompetitionTime;
        protected TextView recyclerteamcompetitionArea;
        protected TextView recyclerteamcompetitionLocation;
        protected TextView recyclerteamcompetitionComent;
        private TextView recyclerteamcompetitionRegister;
        private DatabaseReference mdatabase;
        private FirebaseAuth firebaseAuth;
        private AlertDialog dialog;
        private TeamcompetitionData item;
        private String myTeamName;

        public CustomViewHolder(@NonNull final View itemView) {
            super(itemView);

            recyclerteamcompetitionTime = (TextView) itemView.findViewById(R.id.recyclerteamcompetitionTime);
            recyclerteamcompetitionteamName = (TextView) itemView.findViewById(R.id.recyclerteamcompetitionteamName);
            recyclerteamcompetitionArea = (TextView) itemView.findViewById(R.id.recyclerteamcompetitionArea);
            recyclerteamcompetitionLocation = (TextView) itemView.findViewById(R.id.recyclerteamcompetitionLocation);
            recyclerteamcompetitionComent = (TextView) itemView.findViewById(R.id.recyclerteamcompetitionComent);
            recyclerteamcompetitionRegister= (TextView)itemView.findViewById(R.id.recyclerteamcompetitionRegister);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION) {
                        item = arrayList.get(pos);
                    }
                }
            });
            mdatabase = FirebaseDatabase.getInstance().getReference();
            firebaseAuth = FirebaseAuth.getInstance();

            mdatabase.child("users").child(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        if(dataSnapshot1.getKey().equals("myTeamName")){
                            myTeamName=dataSnapshot1.getValue().toString();
                            return;
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            recyclerteamcompetitionRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    itemView.callOnClick();

                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setMessage("정말 신청하시겠습니까?");
                    builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(view.getContext(),"취소 되었습니다.",Toast.LENGTH_SHORT);
                            return;
                        }
                    });
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Map<String,Object> tmp = new HashMap<>();
                            tmp.put("isRead",false);
                            mdatabase.child("teams").child(item.getName()).child("matching").child(myTeamName).setValue(tmp);
                            Toast.makeText(view.getContext(),"신청 완료 되었습니다.",Toast.LENGTH_SHORT);

                        }
                    });
                    dialog = builder.create();
                    dialog.show();

                }
            });

        }
    }


}

