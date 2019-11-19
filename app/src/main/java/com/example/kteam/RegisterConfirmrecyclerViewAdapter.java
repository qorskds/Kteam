package com.example.kteam;

import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegisterConfirmrecyclerViewAdapter extends RecyclerView.Adapter<RegisterConfirmrecyclerViewAdapter.CustomViewHolder> {

    private ArrayList<RegisterConfrimData> arrayList;


    public RegisterConfirmrecyclerViewAdapter(ArrayList<RegisterConfrimData> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public RegisterConfirmrecyclerViewAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclermessageconfirm, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view,parent,this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RegisterConfirmrecyclerViewAdapter.CustomViewHolder holder, int position) {
        holder.name.setText(arrayList.get(position).getName());
        holder.text.setText(arrayList.get(position).getText());


    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView name;
        protected TextView text;
        private DatabaseReference mdatabase;
        private FirebaseAuth firebaseAuth;
        private RegisterConfrimData item;
        private AlertDialog dialog;
        private ViewGroup group;

        private Map<String,Object > tmp;


        public CustomViewHolder(@NonNull final View itemView, final ViewGroup group, final RegisterConfirmrecyclerViewAdapter adapter) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.messageconfirmName);
            this.text = (TextView) itemView.findViewById(R.id.messageconfirmresigsterText);
            mdatabase = FirebaseDatabase.getInstance().getReference();
            firebaseAuth = FirebaseAuth.getInstance();

            this.group=group;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    item = null;
                    int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION) {
                        item = arrayList.get(pos);
                        tmp = new HashMap<>();
                        tmp.put("read","true");

                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                        builder.setNegativeButton("삭제", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                mdatabase.child("register").child(item.getName()).child(item.getUid()).updateChildren(tmp);
                                return;
                            }
                        });
                        builder.setMessage("승인하시겠습니까?");
                        builder.setPositiveButton("승인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                mdatabase.child("users").child(item.getUid()).child("myTeamName").setValue(item.getName());
                                mdatabase.child("register").child(item.getName()).child(item.getUid()).updateChildren(tmp);

                                Toast.makeText(v.getContext(), "신청 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                adapter.notifyDataSetChanged();


                            }
                        });
                        dialog = builder.create();
                        dialog.show();


                    }
                }
            });

        }
    }


}

