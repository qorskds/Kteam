package com.example.kteam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference mdatabase;
    private FirebaseAuth firebaseAuth;
    private boolean isTeam=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mdatabase= FirebaseDatabase.getInstance().getReference();
        firebaseAuth=FirebaseAuth.getInstance();



        String uid;
        uid = firebaseAuth.getUid();

        mdatabase.child("users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    if(dataSnapshot1.getKey().equals("myTeamName")|| dataSnapshot1.getValue().toString().equals("")){
                        isTeam = false;
                    }else{
                        isTeam = true;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        TextView teamInformationText = (TextView)findViewById(R.id.teamInformationText);

        teamInformationText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });




    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);

        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu,menu);
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent;
        switch(item.getItemId()) {
            case R.id.menuMenu:return true;
            case R.id.menuTeamMake:intent = new Intent(getApplicationContext(), makeAteam.class);
                    startActivity(intent);
                    return true;

            case R.id.menuMy: intent= new Intent(getApplicationContext(), Information.class);
                startActivity(intent);
                return true;

            case R.id.menuLogout:intent= new Intent(getApplicationContext(), LoginActivity.class);
                finish();
                startActivity(intent);


                return true;


        }
        return super.onOptionsItemSelected(item);
    }
}
