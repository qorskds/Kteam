package com.example.kteam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TeamInformation extends AppCompatActivity {
    private DatabaseReference mdatabase;
    private FirebaseAuth firebaseAuth;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<teamRecyclerData> arrayList;
    private recyclerViewAdapter adapter;
    private String uid;
    private String myTeamName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_information);

        mdatabase= FirebaseDatabase.getInstance().getReference();
        firebaseAuth= FirebaseAuth.getInstance();

      recyclerView = (RecyclerView)findViewById(R.id.teamRecyclerView);
      linearLayoutManager = new LinearLayoutManager(this);
      recyclerView.setLayoutManager(linearLayoutManager);

      arrayList = new ArrayList<>();
      adapter= new recyclerViewAdapter(arrayList);
      recyclerView.setAdapter(adapter);
        uid= firebaseAuth.getUid();

        final TextView teamInformationName = (TextView)findViewById(R.id.teamInformationName) ;
        final TextView teamInformationLeader = (TextView)findViewById(R.id.teamInformationLeader) ;
        final TextView teamInformationstadium = (TextView)findViewById(R.id.teamInformationstadium) ;
        final TextView teamInformationsTeamIntroduction = (TextView)findViewById(R.id.teamInformationsTeamIntroduction) ;


      mdatabase.child("users").child(uid).addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                  if(dataSnapshot1.getKey().equals("myTeamName")){
                      myTeamName = dataSnapshot1.getValue().toString();

                      teamInformationName.setText(myTeamName);
                  }
              }


              mdatabase.child("teams").child(myTeamName).addValueEventListener(new ValueEventListener() {
                  @Override
                  public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                      for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                          if(dataSnapshot1.getKey().equals("teamLeader")){
                              teamInformationLeader.setText(dataSnapshot1.getValue().toString());

                          }else if(dataSnapshot1.getKey().equals("stadium")){
                              teamInformationstadium.setText(dataSnapshot1.getValue().toString());

                          }else if(dataSnapshot1.getKey().equals("teamInformation")){
                              teamInformationsTeamIntroduction.setText(dataSnapshot1.getValue().toString());

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
                      for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                              if(dataSnapshot1.child("myTeamName").getValue().toString().equals(myTeamName)){
                                  teamRecyclerData tmp = new teamRecyclerData(dataSnapshot1.child("nicknameText").getValue().toString(),dataSnapshot1.child("positionText").getValue().toString());
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
