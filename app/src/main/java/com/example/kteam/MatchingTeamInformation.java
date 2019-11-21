package com.example.kteam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MatchingTeamInformation extends AppCompatActivity {
    private TextView matchinteaminformationName;
    private TextView matchinteaminformationLocation;
    private TextView matchinteaminformationIntroduce;
    private Button matchingteaminformtaionAgree;
    private Button matchingteaminformtaionCansle;
    private DatabaseReference mdatabase;
    private FirebaseAuth firebaseAuth;
    private String teamName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching_team_information);

        mdatabase= FirebaseDatabase.getInstance().getReference();
        firebaseAuth= FirebaseAuth.getInstance();

        matchinteaminformationName= (TextView)findViewById(R.id.matchinteaminformationName);
        matchinteaminformationLocation= (TextView)findViewById(R.id.matchinteaminformationLocation);
        matchinteaminformationIntroduce= (TextView)findViewById(R.id.matchinteaminformationIntroduce);
        matchingteaminformtaionAgree=(Button)findViewById(R.id.matchingteaminformtaionAgree);
        matchingteaminformtaionCansle=(Button)findViewById(R.id.matchingteaminformtaionCansle);


        matchinteaminformationName.setText(getIntent().getStringExtra("name"));
        matchinteaminformationLocation.setText(getIntent().getStringExtra("location"));
        matchinteaminformationIntroduce.setText(getIntent().getStringExtra("information"));
        matchingteaminformtaionCansle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mdatabase.child("users").child(firebaseAuth.getUid()).child("myTeamName").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        teamName=dataSnapshot.getValue().toString();

                        mdatabase.child("teams").child(teamName).child("matching").child(matchinteaminformationName.getText().toString()).child("isRead").setValue("true");

                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        matchingteaminformtaionAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mdatabase.child("users").child(firebaseAuth.getUid()).child("myTeamName").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        teamName = dataSnapshot.getValue().toString();
                        Map<String,Object> tmp = new HashMap<>();
                        tmp.put(matchinteaminformationName.getText().toString(),false+"");
                        mdatabase.child("teams").child(teamName).child("matchDate").setValue(tmp);
                        tmp.clear();
                        tmp.put(teamName,false+"");
                        mdatabase.child("teams").child(matchinteaminformationName.getText().toString()).child("matchDate").setValue(tmp);
                        mdatabase.child("teams").child(teamName).child("matching").child(matchinteaminformationName.getText().toString()).child("isRead").setValue("true");

                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });





    }
}

