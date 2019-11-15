package com.example.kteam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Context;

import java.util.HashMap;
import java.util.Map;

public class makeAteam extends AppCompatActivity {
    private DatabaseReference mdatabase;
    private AlertDialog dialog;
    private FirebaseAuth firebaseAuth;
    private ArrayAdapter adapter;
    private ArrayAdapter stadiumadapter;
    private String userName;
    boolean pName=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_ateam);


        mdatabase = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        final EditText teamName = (EditText)findViewById(R.id.teamName);
        final Spinner stadiumSpinner = (Spinner)findViewById(R.id.stadiumSpinner);
        final EditText teamInformation =(EditText)findViewById(R.id.teamInformation);

        Button registerTeam = (Button)findViewById(R.id.registerTeam);
        final String uid = firebaseAuth.getUid();

        mdatabase.child("users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    if(dataSnapshot1.getKey().equals("nicknameText")){
                        Log.e("adf",dataSnapshot1.getValue().toString());
                        userName=dataSnapshot1.getValue().toString();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        stadiumadapter =ArrayAdapter.createFromResource(this,R.array.city,R.layout.support_simple_spinner_dropdown_item);
        stadiumSpinner.setAdapter(stadiumadapter);

        registerTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdatabase.child("teams").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                            String a= dataSnapshot1.getKey().toString();
                            String b= teamName.getText().toString();

                            if(a.equals(b)){
                                pName = true;
                            }else{
                                pName=false;
                            }


                        }
                        if(pName){
                            AlertDialog.Builder builder = new AlertDialog.Builder(makeAteam.this);
                            dialog = builder.setMessage(("이름이 중복됩니다.")).setPositiveButton("확인", null).create();
                            dialog.show();
                            return;
                        }else if(teamName.getText().toString().isEmpty()){

                            AlertDialog.Builder builder = new AlertDialog.Builder(makeAteam.this);
                            dialog = builder.setMessage(("빈칸이 있습니다.")).setPositiveButton("확인", null).create();
                            dialog.show();
                            return;
                        }
                        Map<String,Object> tmp = new HashMap<>();
                        tmp.put("teamLeader",userName);
                        tmp.put("stadium",stadiumSpinner.getSelectedItem());
                        tmp.put("teamInformation",teamInformation.getText().toString());
                        mdatabase.child("teams").child(teamName.getText().toString()).updateChildren(tmp);

                        Map<String, Object> item =new HashMap<>();
                        item.put("myTeamName",teamName.getText().toString());
                        mdatabase.child("users").child(uid).updateChildren(item);

                        Toast.makeText(getApplicationContext(),"팀만들기를 성공하셨습니다.",Toast.LENGTH_SHORT).show();

                        finish();

                        return;


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }



        });




    }
}
