package com.example.kteam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Information_Revise extends AppCompatActivity {
    private DatabaseReference mdatabase;
    private FirebaseAuth firebaseAuth;
    private Spinner locationSpinner;
    private Spinner buildSpinner;
    private ArrayAdapter buildadapter;
    private ArrayAdapter locationadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information__revise);
        mdatabase = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        locationSpinner = (Spinner)findViewById(R.id.infoReviseLocation);
        buildSpinner =(Spinner)findViewById(R.id.infoReviseBuild);



        buildadapter =ArrayAdapter.createFromResource(this,R.array.bulid,R.layout.support_simple_spinner_dropdown_item);
        buildSpinner.setAdapter(buildadapter);
        locationadapter = ArrayAdapter.createFromResource(this,R.array.city,R.layout.support_simple_spinner_dropdown_item);
        locationSpinner.setAdapter(locationadapter);


        final EditText infoReviseNickname= (EditText) findViewById(R.id.infoReviseNickname);
        final EditText infoReviseAge = (EditText)findViewById(R.id.infoReviseAge);
        final EditText infoReviseHeight =(EditText)findViewById(R.id.infoReviseHeight);
        final EditText infoRevisePosition =(EditText)findViewById(R.id.infoRevisePosition);
        final TextView infoReviseTeam = (TextView)findViewById(R.id.infoReviseTeam);
        Button infoReviseStoreButton = (Button)findViewById(R.id.infoReviseStore);
        final Switch playerRegistrationReviseSwitch = (Switch)findViewById(R.id.playerRegistrationReviseSwitch);
        final EditText infocharacter =(EditText)findViewById(R.id.nfoRevisecharacter);




        final String uid = firebaseAuth.getUid();


        mdatabase.child("users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){

                    if(dataSnapshot1.getKey().equals("ageText")){
                        infoReviseAge.setText(dataSnapshot1.getValue().toString());

                    }else if(dataSnapshot1.getKey().equals("bulid")){
                        buildSpinner.setSelection(buildadapter.getPosition(dataSnapshot1.getValue().toString()));

                    }else if(dataSnapshot1.getKey().equals("heightText")){
                        infoReviseHeight.setText(dataSnapshot1.getValue().toString());

                    }else if(dataSnapshot1.getKey().equals("nicknameText")){
                        infoReviseNickname.setText(dataSnapshot1.getValue().toString());

                    }else if(dataSnapshot1.getKey().equals("positionText")){
                        infoRevisePosition.setText(dataSnapshot1.getValue().toString());

                    }else if(dataSnapshot1.getKey().equals("playerRegister")){
                        playerRegistrationReviseSwitch.setChecked((boolean)dataSnapshot1.getValue());
                    }else if(dataSnapshot1.getKey().equals("locationText")){

                        locationSpinner.setSelection(locationadapter.getPosition(dataSnapshot1.getValue().toString()));
                    }else if(dataSnapshot1.getKey().equals("character")){
                        infocharacter.setText(dataSnapshot1.getValue().toString());
                    }
                    if(dataSnapshot1.getKey().equals("myTeamName")){
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
                tmp.put("bulid",buildSpinner.getSelectedItem());
                tmp.put("heightText",infoReviseHeight.getText().toString());
                tmp.put("nicknameText",infoReviseNickname.getText().toString());
                tmp.put("positionText",infoRevisePosition.getText().toString());
                tmp.put("playerRegister",playerRegistrationReviseSwitch.isChecked());
                tmp.put("character",infocharacter.getText().toString());
                tmp.put("locationText",locationSpinner.getSelectedItem());

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
