package com.example.kteam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FindTeam extends AppCompatActivity {
    private DatabaseReference mdatabase;
    private FirebaseAuth firebaseAuth;
    private ArrayList<findteamData> arrayList;
    private LinearLayoutManager linearLayoutManager;
    private findteamrecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_team);

        mdatabase = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        linearLayoutManager = new LinearLayoutManager(this);
        arrayList= new ArrayList<>();
        adapter = new findteamrecyclerViewAdapter(arrayList);

        RecyclerView findteamRecyclerView = (RecyclerView)findViewById(R.id.findteamRecyclerView);
        findteamRecyclerView.setLayoutManager(linearLayoutManager);
        findteamRecyclerView.setAdapter(adapter);

        Button findteamNameButton = (Button)findViewById(R.id.findteamNameButton);
        final EditText findteamName=(EditText)findViewById(R.id.findteamName);

        findteamNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mdatabase.child("teams").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        arrayList.clear();
                        for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                            if(dataSnapshot1.getKey().equals(findteamName.getText().toString())){
                                findteamData data= new findteamData(dataSnapshot1.getKey(),
                                        dataSnapshot1.child("teamLeader").getValue().toString(),dataSnapshot1.child("teamInformation").getValue().toString());
                                arrayList.add(data);
                                adapter.notifyDataSetChanged();

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });




        mdatabase.child("teams").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    findteamData data= new findteamData(dataSnapshot1.getKey(),
                            dataSnapshot1.child("teamLeader").getValue().toString(),dataSnapshot1.child("teamInformation").getValue().toString());
                    arrayList.add(data);
                    adapter.notifyDataSetChanged();
                }
                if(arrayList.size()==0){
                    Toast.makeText(FindTeam.this,"위와 같은 이름의 팀이 없습니다.",Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }
}
