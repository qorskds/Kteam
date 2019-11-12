package com.example.kteam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Maketeam extends AppCompatActivity {
    private DatabaseReference mdatabase;
    private AlertDialog dialog;
    private FirebaseAuth firebaseAuth;
    private ArrayAdapter adapter;
    boolean pName=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maketeam);


        mdatabase = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        final EditText teamName = (EditText)findViewById(R.id.teamName);
        EditText teamLeader = (EditText)findViewById(R.id.teamLeader);
        EditText stadium = (EditText)findViewById(R.id.stadium);

        Button registerTeam = (Button)findViewById(R.id.registerTeam);

        registerTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdatabase.child("teams").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(teamName.getText().toString().equals(dataSnapshot.child("teamName"))){
                            pName=false;
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });
        Map<String,Object> tmp = new HashMap<>();
        tmp.put("teamLeader",teamLeader.getText().toString());
        tmp.put("stadium",stadium.getText().toString());
        mdatabase.child("teams").child(teamName.getText().toString()).updateChildren(tmp);





    }

}
