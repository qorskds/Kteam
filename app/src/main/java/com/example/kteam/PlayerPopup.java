package com.example.kteam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.BatchUpdateException;
import java.util.HashMap;
import java.util.Map;

public class PlayerPopup extends AppCompatActivity {
    private DatabaseReference mdatabase;
    private FirebaseAuth firebaseAuth;
    private String teamName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_popup);
        mdatabase = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        final TextView playerPopupName = (TextView) findViewById(R.id.playerPopupName);
        final TextView playerPopupAge = (TextView) findViewById(R.id.playerPopupAge);
        final TextView playerPopupHeight = (TextView) findViewById(R.id.playerPopupHeight);
        final TextView playerPopupBuild = (TextView) findViewById(R.id.playerPopupBuild);
        final TextView playerPopupPosition = (TextView) findViewById(R.id.playerPopupPosition);
        final TextView playerPopupLocation = (TextView) findViewById(R.id.playerPopupLocation);
        final TextView playerPopupcharacter = (TextView) findViewById(R.id.playerPopupcharacter);
        Button playerPopupPropose = (Button) findViewById(R.id.playerPopupPropose);
        Button playerPopupClose = (Button) findViewById(R.id.playerPopupClose);


        final String uid = getIntent().getStringExtra("uid");
        mdatabase.child("users").child(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    if(dataSnapshot1.getKey().equals("myTeamName")){
                        teamName=dataSnapshot1.getValue().toString();
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        mdatabase.child("users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                    if (dataSnapshot1.getKey().equals("ageText")) {
                        playerPopupAge.setText(dataSnapshot1.getValue().toString());

                    } else if (dataSnapshot1.getKey().equals("bulid")) {
                        playerPopupBuild.setText(dataSnapshot1.getValue().toString());

                    } else if (dataSnapshot1.getKey().equals("heightText")) {
                        playerPopupHeight.setText(dataSnapshot1.getValue().toString());

                    } else if (dataSnapshot1.getKey().equals("nicknameText")) {
                        playerPopupName.setText(dataSnapshot1.getValue().toString());

                    } else if (dataSnapshot1.getKey().equals("positionText")) {
                        playerPopupPosition.setText(dataSnapshot1.getValue().toString());

                    } else if (dataSnapshot1.getKey().equals("locationText")) {
                        playerPopupLocation.setText(dataSnapshot1.getValue().toString());
                    } else if (dataSnapshot1.getKey().equals("character")) {
                        playerPopupcharacter.setText(dataSnapshot1.getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        playerPopupPropose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> tmp = new HashMap<>();
                tmp.put("isRead","false");


                mdatabase.child("users").child(uid).child("propose").child(teamName).updateChildren(tmp);
                Toast.makeText(getApplicationContext(),"신청 되었습니다",Toast.LENGTH_SHORT).show();
                finish();

            }
        });

        playerPopupClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
