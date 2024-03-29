package com.example.kteam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TeamInformation extends AppCompatActivity {
    private DatabaseReference mdatabase;
    private FirebaseAuth firebaseAuth;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<teamRecyclerData> arrayList;
    private recyclerViewAdapter adapter;
    private String uid;
    private String myTeamName;
    private ImageView teamInformationmessage;
    private ImageButton message;
    private LinearLayoutManager linearLayoutManagermatch;
    private ArrayList<teamMatchData> arrayListmatch;
    private RecyclerView matchrecyclerview;
    private teamMatchrecyclerViewAdapter adaptermatch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_information);

        mdatabase = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        recyclerView = (RecyclerView) findViewById(R.id.teamRecyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        linearLayoutManagermatch= new LinearLayoutManager(this);
        arrayListmatch= new ArrayList<>();
        matchrecyclerview= (RecyclerView)findViewById(R.id.teamInformationMatchRecyclerView);
        adaptermatch= new teamMatchrecyclerViewAdapter(arrayListmatch);
        matchrecyclerview.setLayoutManager(linearLayoutManagermatch);
        matchrecyclerview.setAdapter(adaptermatch);




        arrayList = new ArrayList<>();
        adapter = new recyclerViewAdapter(arrayList);
        recyclerView.setAdapter(adapter);
        uid = firebaseAuth.getUid();
        teamInformationmessage = (ImageView) findViewById(R.id.teamInformationmessage);

        final TextView teamInformationName = (TextView) findViewById(R.id.teamInformationName);
        final TextView teamInformationLeader = (TextView) findViewById(R.id.teamInformationLeader);
        final TextView teamInformationstadium = (TextView) findViewById(R.id.teamInformationstadium);
        final TextView teamInformationsTeamIntroduction = (TextView) findViewById(R.id.teamInformationsTeamIntroduction);


        teamInformationmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeamInformation.this, ResigsterConfirm.class);
                intent.putExtra("teamName", myTeamName);
                startActivity(intent);

            }
        });



        mdatabase.child("users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    if (dataSnapshot1.getKey().equals("myTeamName")) {
                        myTeamName = dataSnapshot1.getValue().toString();
                        Log.e(dataSnapshot1.getValue().toString(),"asdf");
                        Log.e(myTeamName,"ddd");
                        teamInformationName.setText(myTeamName);
                        Log.e(teamInformationName.getText().toString(),"asdaaaf");
                    }
                }
                mdatabase.child("teams").child(myTeamName).child("matchDate").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        teamMatchData tmp;
                        for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                            tmp= new teamMatchData(myTeamName,dataSnapshot1.getKey());
                            arrayListmatch.add(tmp);
                            adaptermatch.notifyDataSetChanged();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



                mdatabase.child("teams").child(myTeamName).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            if (dataSnapshot1.getKey().equals("teamLeader")) {
                                teamInformationLeader.setText(dataSnapshot1.getValue().toString());

                            } else if (dataSnapshot1.getKey().equals("stadium")) {
                                teamInformationstadium.setText(dataSnapshot1.getValue().toString());

                            } else if (dataSnapshot1.getKey().equals("teamInformation")) {
                                teamInformationsTeamIntroduction.setText(dataSnapshot1.getValue().toString());

                            } else if (dataSnapshot1.getKey().equals("teamLeaderUid") && dataSnapshot1.getValue().toString().equals(firebaseAuth.getUid())) {
                                teamInformationmessage.setVisibility(View.VISIBLE);
                            }

                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                mdatabase.child("users").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            if(dataSnapshot1.child("myTeamName").getValue()== null){

                            }else if (dataSnapshot1.child("myTeamName").getValue().toString().equals(myTeamName)) {
                                teamRecyclerData tmp = new teamRecyclerData(dataSnapshot1.child("nicknameText").getValue().toString(), dataSnapshot1.child("positionText").getValue().toString());
                                arrayList.add(tmp);
                                adapter.notifyDataSetChanged();
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }
}
