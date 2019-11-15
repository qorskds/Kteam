package com.example.kteam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FindPlayer extends AppCompatActivity {
    private DatabaseReference mdatabase;
    private FirebaseAuth firebaseAuth;
    private ArrayList<findplayerData> arrayList;
    private ArrayList<findplayerData> arrayListtmp;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private  findplayerrecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_player);
        mdatabase = FirebaseDatabase.getInstance().getReference();
        firebaseAuth=FirebaseAuth.getInstance();

        recyclerView = (RecyclerView)findViewById(R.id.findplayerRecycler);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();
        adapter= new findplayerrecyclerViewAdapter(arrayList);
        recyclerView.setAdapter(adapter);
        Button findNameButton= (Button)findViewById(R.id.findNameButton);
        final EditText findName = (EditText)findViewById(R.id.findName);



        findNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdatabase.child("users").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        arrayList.clear();
                        for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                            arrayListtmp= new ArrayList<>();
                            if(dataSnapshot1.child("nicknameText").getValue().toString().equals(findName.getText().toString())){
                                findplayerData tmp = new findplayerData(dataSnapshot1.child("nicknameText").getValue().toString(),
                                        dataSnapshot1.child("bulid").getValue().toString(),dataSnapshot1.child("heightText").getValue().toString(),
                                        dataSnapshot1.child("positionText").getValue().toString(),dataSnapshot1.child("character").getValue().toString(),dataSnapshot1.child("locationText").getValue().toString());
                                arrayList.add(tmp);
                                adapter.notifyDataSetChanged();
                            }
                        }
                        if(arrayList.size()==0){
                            Toast.makeText(FindPlayer.this,"위와 같은 이름의 선수가 없습니다.",Toast.LENGTH_SHORT).show();
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



            }
        });





        mdatabase.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    if(dataSnapshot1.hasChildren()){
                        findplayerData tmp = new findplayerData(dataSnapshot1.child("nicknameText").getValue().toString(),
                                dataSnapshot1.child("bulid").getValue().toString(),dataSnapshot1.child("heightText").getValue().toString(),
                                dataSnapshot1.child("positionText").getValue().toString(),dataSnapshot1.child("character").getValue().toString(),dataSnapshot1.child("locationText").getValue().toString());
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
