package com.example.kteam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import javax.security.auth.login.LoginException;

public class machingRegister extends AppCompatActivity {
    private Spinner matchingRegisterSpinner;
    private EditText matchingRegistercoment;
    private EditText matchingRegisterlocation;
    private ArrayAdapter matchingRegisterSpinnerAdapter;
    private DatabaseReference mdatabase;
    private FirebaseAuth firebaseAuth;
    private EditText matchingRegisterTime;
    private Button registerButton;
    private String myTeamName;

    Map<String,Object> tmp = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maching_register);

        mdatabase = FirebaseDatabase.getInstance().getReference();
        firebaseAuth= FirebaseAuth.getInstance();

        matchingRegisterSpinner = (Spinner)findViewById(R.id.matchingRegisterSpinner);
        matchingRegistercoment = (EditText)findViewById(R.id.matchingRegistercoment);
        matchingRegisterlocation= (EditText)findViewById(R.id.matchingRegisterlocation);
        matchingRegisterTime= (EditText)findViewById(R.id.matchingRegisterTime);
        matchingRegisterSpinnerAdapter = ArrayAdapter.createFromResource(this,R.array.city,R.layout.support_simple_spinner_dropdown_item);
        matchingRegisterSpinner.setAdapter(matchingRegisterSpinnerAdapter);
        registerButton = (Button)findViewById(R.id.matchingRegisterButton);

        mdatabase.child("users").child(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    if(dataSnapshot1.getKey().equals("myTeamName")){
                        myTeamName=dataSnapshot1.getValue().toString();
                        return;
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tmp = new HashMap<>();
                tmp.put("Location",matchingRegisterlocation.getText().toString());
                tmp.put("Coment",matchingRegistercoment.getText().toString());
                tmp.put("Area",matchingRegisterSpinner.getSelectedItem());
                tmp.put("Time",matchingRegisterTime.getText().toString());
                mdatabase.child("matching").child(myTeamName).setValue(tmp);
                finish();

            }
        });











    }
}
