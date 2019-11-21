package com.example.kteam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.ObjectsCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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

public class ProPosePopup extends AppCompatActivity {
    private TextView proposePopupStadium;
    private TextView proposePopupTeamIntroduction;
    private TextView proposePopupTeamName;
    private TextView proposePopupTeamLeader;
    private DatabaseReference mdatabase;
    private FirebaseAuth firebaseAuth;
    private Button proposePopupinButton;
    private Button proposePopupcloseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_pose_popup);

        mdatabase= FirebaseDatabase.getInstance().getReference();
        firebaseAuth= FirebaseAuth.getInstance();


        proposePopupinButton=(Button)findViewById(R.id.proposePopupinButton);
        proposePopupcloseButton=(Button)findViewById(R.id.proposePopupicloseButton);

        proposePopupStadium= (TextView)findViewById(R.id.proposePopupStadium);
        proposePopupTeamIntroduction= (TextView)findViewById(R.id.proposePopupTeamIntroduction);
        proposePopupTeamName= (TextView)findViewById(R.id.proposePopupTeamName);
        proposePopupTeamLeader= (TextView)findViewById(R.id.proposePopupTeamLeader);


        proposePopupTeamName.setText(getIntent().getStringExtra("teamName"));
        final Map<String, Object> tmp2= new HashMap<>();
        tmp2.put("isRead","true");

        proposePopupcloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdatabase.child("users").child(firebaseAuth.getUid()).child("propose").child(proposePopupTeamName.getText().toString()).updateChildren(tmp2);
                finish();
            }
        });
        proposePopupinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> tmp = new HashMap<>();
                tmp.put("myTeamName",proposePopupTeamName.getText().toString());
                mdatabase.child("users").child(firebaseAuth.getUid()).updateChildren(tmp);
                mdatabase.child("users").child(firebaseAuth.getUid()).child("propose").child(proposePopupTeamName.getText().toString()).updateChildren(tmp2);
                 finish();
            }
        });



        mdatabase.child("teams").child(proposePopupTeamName.getText().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    if(dataSnapshot1.getKey().equals("stadium")){
                        proposePopupStadium.setText(dataSnapshot1.getValue().toString());
                    }else if(dataSnapshot1.getKey().equals("teamInformation")){
                        proposePopupTeamIntroduction.setText(dataSnapshot1.getValue().toString());
                    }else if(dataSnapshot1.getKey().equals("teamLeader")){
                        proposePopupTeamLeader.setText(dataSnapshot1.getValue().toString());
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
