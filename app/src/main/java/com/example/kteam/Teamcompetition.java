package com.example.kteam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.contentcapture.DataRemovalRequest;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ThrowOnExtraProperties;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Teamcompetition extends AppCompatActivity {
    private Button competitionMatchingbutton;
    private ArrayAdapter arrayAdapter;
    private ArrayList<TeamcompetitionData> arrayList;
    private TeamcompetitonrecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private DatabaseReference mdatabase;
    private FirebaseAuth firebaseAuth;
    private String teamName;


    private TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teamcompetition);

        mdatabase = FirebaseDatabase.getInstance().getReference();
        firebaseAuth =FirebaseAuth.getInstance();

        arrayList = new ArrayList<>();
        adapter = new TeamcompetitonrecyclerViewAdapter(arrayList);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView = (RecyclerView) findViewById(R.id.teamcompetitonRecyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        competitionMatchingbutton = (Button) findViewById(R.id.competitionMatchingbutton);

        competitionMatchingbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(teamName.equals("")){
                    Toast.makeText(getApplicationContext(),"팀이 없습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(getApplicationContext(), machingRegister.class);
                startActivity(intent);
            }
        });
        mdatabase.child("users").child(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    if(dataSnapshot1.getKey().equals("myTeamName")){
                        teamName=dataSnapshot1.getValue().toString();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mdatabase.child("matching").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    if (!dataSnapshot1.getKey().equals(teamName)) {
                        TeamcompetitionData tmp = new TeamcompetitionData(dataSnapshot1.getKey(), dataSnapshot1.child("Area").getValue().toString(),
                                dataSnapshot1.child("Location").getValue().toString()
                                , dataSnapshot1.child("Coment").getValue().toString(), dataSnapshot1.child("Time").getValue().toString());
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
}
