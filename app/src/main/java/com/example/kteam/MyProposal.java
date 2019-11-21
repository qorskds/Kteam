package com.example.kteam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyProposal extends AppCompatActivity {
    private LinearLayoutManager linearLayoutManager;
    private myproposalrecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<myproposalData> arrayList;
    private DatabaseReference mdatabase;
    private FirebaseAuth firebaseAuth;
    private String teamName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_proposal);

        mdatabase = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        arrayList = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(this);
        adapter = new myproposalrecyclerViewAdapter(arrayList);
        recyclerView = (RecyclerView) findViewById(R.id.registermyproposal);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);


        adapter.notifyDataSetChanged();


        mdatabase.child("users").child(firebaseAuth.getUid()).child("propose").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    if (dataSnapshot1.child("isRead").getValue().toString().equals("false")) {
                        teamName = dataSnapshot1.getKey();
                        mdatabase.child("teams").child(teamName).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                myproposalData tmp = new myproposalData();
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    tmp.setTeamName(teamName);
                                    if (dataSnapshot1.getKey().equals("stadium")) {
                                        tmp.setStaditum(dataSnapshot1.getValue().toString());
                                    } else if (dataSnapshot1.getKey().equals("teamInformation")) {
                                        tmp.setTeamIntroduce(dataSnapshot1.getValue().toString());
                                    } else if (dataSnapshot1.getKey().equals("teamLeader")) {
                                        tmp.setTeamLeader(dataSnapshot1.getValue().toString());
                                    }
                                }

                                arrayList.add(tmp);
                                adapter.notifyDataSetChanged();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


}

