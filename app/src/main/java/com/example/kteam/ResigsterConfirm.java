package com.example.kteam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class ResigsterConfirm extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RegisterConfirmrecyclerViewAdapter adapter;
    private ArrayList<RegisterConfrimData> arrayList;
    private DatabaseReference mdatabase;
    private FirebaseAuth firebaseAuth;
    private String teamName;
    private LinearLayoutManager linearLayoutManager;
    private String name;
    private teamMatchingConfirmrecyclerViewAdapter matchingAdapter;
    private RecyclerView matchingRecyclerView;
    private LinearLayoutManager linearLayoutManagermatching;
    private ArrayList<teamMatchingConfrimData> matchingarrayList;
    private String matchingteamName;


    public String getName() {
        return name;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resigster_confirm);


        mdatabase = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        teamName = getIntent().getStringExtra("teamName");

        arrayList = new ArrayList<>();
        adapter = new RegisterConfirmrecyclerViewAdapter(arrayList);


        matchingarrayList = new ArrayList<>();
        linearLayoutManagermatching = new LinearLayoutManager(this);
        matchingAdapter = new teamMatchingConfirmrecyclerViewAdapter(matchingarrayList);
        matchingRecyclerView = (RecyclerView) findViewById(R.id.regiserconfirmMatchingView);
        matchingRecyclerView.setLayoutManager(linearLayoutManagermatching);
        matchingRecyclerView.setAdapter(matchingAdapter);


        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView = (RecyclerView) findViewById(R.id.registerconfirmRecyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);




        mdatabase.child("teams").child(teamName).child("matching").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    matchingteamName = dataSnapshot1.getKey();
                    if (dataSnapshot1.child("isRead").getValue().toString().equals("false")) {

                        mdatabase.child("teams").child(matchingteamName).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                teamMatchingConfrimData tmp = new teamMatchingConfrimData(matchingteamName, dataSnapshot.child("stadium").getValue().toString()
                                        , dataSnapshot.child("teamInformation").getValue().toString());
                                matchingarrayList.add(tmp);
                                matchingAdapter.notifyDataSetChanged();
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


        mdatabase.child("register").child(teamName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (final DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    if (dataSnapshot1.child("read").getValue().toString().equals("false")) {
                        mdatabase.child("users").child(dataSnapshot1.getKey()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot dataSnapshot11 : dataSnapshot.getChildren()) {
                                    if (dataSnapshot11.getKey().equals("nicknameText")) {
                                        name = dataSnapshot11.getValue().toString();
                                        break;
                                    }
                                }
                                RegisterConfrimData registerConfrimData = new RegisterConfrimData(name, dataSnapshot1.child("registerText").getValue().toString(), dataSnapshot1.getKey());
                                arrayList.add(registerConfrimData);
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
