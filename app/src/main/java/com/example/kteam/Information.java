package com.example.kteam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class Information extends AppCompatActivity {
    private DatabaseReference mdatabase;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        mdatabase = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        final TextView infoNickname= (TextView)findViewById(R.id.infoNickname);
        final TextView infoAge = (TextView)findViewById(R.id.infoAge);
        final TextView infoHeight =(TextView)findViewById(R.id.infoHeight);
        final TextView infoBuild =(TextView)findViewById(R.id.infoBuild);
        final TextView infoPosition =(TextView)findViewById(R.id.infoPosition);
        final TextView infoTeam =(TextView)findViewById(R.id.infoTeam);
        final Switch playerRegistrationSwitch = (Switch)findViewById(R.id.playerRegistrationSwitch);


        final String uid = firebaseAuth.getUid();


        mdatabase.child("users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){

                    if(dataSnapshot1.getKey().equals("ageText")){
                        infoAge.setText(dataSnapshot1.getValue().toString());

                    }else if(dataSnapshot1.getKey().equals("bulid")){
                        infoBuild.setText(dataSnapshot1.getValue().toString());

                    }else if(dataSnapshot1.getKey().equals("heightText")){
                        infoHeight.setText(dataSnapshot1.getValue().toString());

                    }else if(dataSnapshot1.getKey().equals("nicknameText")){
                        infoNickname.setText(dataSnapshot1.getValue().toString());

                    }else if(dataSnapshot1.getKey().equals("positionText")){
                        infoPosition.setText(dataSnapshot1.getValue().toString());

                    }else if(dataSnapshot1.getKey().equals("playerRegister")){
                        playerRegistrationSwitch.setChecked((Boolean)dataSnapshot1.getValue());


                    }
                    if(dataSnapshot1.getKey().equals("team")){
                        infoTeam.setText(dataSnapshot1.getValue().toString());
                    }else{
                        infoTeam.setText("팀이 없습니다.");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        Button infoReviseButton = (Button)findViewById(R.id.infoReviseButton);
        infoReviseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Information_Revise.class);
                startActivity(intent);
            }
        });
        Button infoExitButton = (Button)findViewById(R.id.infoExitButton);

        infoExitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });




    }
}
