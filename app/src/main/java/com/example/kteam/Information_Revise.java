package com.example.kteam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Information_Revise extends AppCompatActivity {
    private DatabaseReference mdatabase;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information__revise);
        mdatabase = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        final EditText infoReviseNickname= (EditText) findViewById(R.id.infoReviseNickname);
        final EditText infoReviseAge = (EditText)findViewById(R.id.infoReviseAge);
        final EditText infoReviseHeight =(EditText)findViewById(R.id.infoReviseHeight);
        final EditText infoReviseBuild =(EditText)findViewById(R.id.infoReviseBuild);
        final EditText infoRevisePosition =(EditText)findViewById(R.id.infoRevisePosition);
        final TextView infoReviseTeam = (TextView)findViewById(R.id.infoReviseTeam);
        Button infoReviseStoreButton = (Button)findViewById(R.id.infoReviseStore);
        final Switch playerRegistrationReviseSwitch = (Switch)findViewById(R.id.playerRegistrationReviseSwitch);




        final String uid = firebaseAuth.getUid();


        mdatabase.child("users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){

                    if(dataSnapshot1.getKey().equals("ageText")){
                        infoReviseAge.setText(dataSnapshot1.getValue().toString());

                    }else if(dataSnapshot1.getKey().equals("bulid")){
                        infoReviseBuild.setText(dataSnapshot1.getValue().toString());

                    }else if(dataSnapshot1.getKey().equals("heightText")){
                        infoReviseHeight.setText(dataSnapshot1.getValue().toString());

                    }else if(dataSnapshot1.getKey().equals("nicknameText")){
                        infoReviseNickname.setText(dataSnapshot1.getValue().toString());

                    }else if(dataSnapshot1.getKey().equals("positionText")){
                        infoRevisePosition.setText(dataSnapshot1.getValue().toString());

                    }else if(dataSnapshot1.getKey().equals("playerRegister")){
                        playerRegistrationReviseSwitch.setChecked((boolean)dataSnapshot1.getValue());
                    }
                    if(dataSnapshot1.getKey().equals("team")){
                        infoReviseTeam.setText(dataSnapshot1.getValue().toString());
                    }else{
                        infoReviseTeam.setText("팀이 없습니다.");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        infoReviseStoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,Object> tmp = new HashMap<>();
                tmp.put("ageText",infoReviseAge.getText().toString());
                tmp.put("bulid",infoReviseBuild.getText().toString());
                tmp.put("heightText",infoReviseHeight.getText().toString());
                tmp.put("nicknameText",infoReviseNickname.getText().toString());
                tmp.put("positionText",infoRevisePosition.getText().toString());
                tmp.put("playerRegister",playerRegistrationReviseSwitch.isChecked());

                mdatabase.child("users").child(uid).updateChildren(tmp);
                finish();

            }
        });
        Button infoReviseExit =(Button)findViewById(R.id.infoReviseExit);
        infoReviseExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });








    }
}
