package com.example.kteam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class teampopup extends AppCompatActivity {
    private DatabaseReference mdatabase;
    private FirebaseAuth firebaseAuth;
    private AlertDialog dialog;
    private boolean pResiger;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mdatabase = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        setTheme(android.R.style.Theme_NoTitleBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teampopup);
        name = getIntent().getStringExtra("teamName");
        TextView teamPopupName = (TextView)findViewById(R.id.teamPopupName);
        final TextView teamPopupLeader =(TextView)findViewById(R.id.teamPopupLeader);
        final TextView teamPopupstadium = (TextView)findViewById(R.id.teamPopupstadium);
        final TextView teamPopupTeamIntroduction =(TextView)findViewById(R.id.teamPopupTeamIntroduction);
        final EditText registerText =(EditText)findViewById(R.id.registerText);
        teamPopupName.setText(name);
        mdatabase.child("teams").child(name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    if(dataSnapshot1.getKey().equals("teamLeader")){
                        teamPopupLeader.setText(dataSnapshot1.getValue().toString());

                    }else if(dataSnapshot1.getKey().equals("stadium")){
                        teamPopupstadium.setText(dataSnapshot1.getValue().toString());

                    }else if(dataSnapshot1.getKey().equals("teamInformation")){
                        teamPopupTeamIntroduction.setText(dataSnapshot1.getValue().toString());
                    }

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        final Button teamPopupResiger = (Button)findViewById(R.id.teamPopupResister);
        Button teamPopupClose =(Button)findViewById(R.id.teamPopupClose);

       teamPopupClose.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               finish();
           }
       });
       mdatabase.child("users").child(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               if(name.equals(dataSnapshot.child("myTeamName").getValue())){
                   teamPopupResiger.setEnabled(false);
                   registerText.setEnabled(false);


               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
        mdatabase.child("register").child(name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pResiger=true;
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    if (dataSnapshot1.getKey().equals(firebaseAuth.getUid())) {
                        pResiger = false;
                        break;
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


       teamPopupResiger.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(final View v) {

               AlertDialog.Builder builder = new AlertDialog.Builder(teampopup.this);
               builder.setMessage("정말 신청하시겠습니까?");
               builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       return;
                   }
               });
               builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {


                       Map<String,String> tmp = new HashMap<>();
                       tmp.put("read","false");
                       tmp.put("registerText",registerText.getText().toString());
                       mdatabase.child("register").child(name).child(firebaseAuth.getUid()).setValue(tmp);
                       finish();
                       Toast.makeText(getApplicationContext(),"신청 완료 되었습니다.",Toast.LENGTH_SHORT);

                   }
               });
               dialog = builder.create();
               if (registerText.getText().toString().equals("")){
                   Toast.makeText(getApplicationContext(),"빈칸을 채워주세요",Toast.LENGTH_SHORT).show();
                   return;
               }


               if (!pResiger){
                   Toast.makeText(getApplicationContext(),"이미 신청하셨습니다.",Toast.LENGTH_SHORT).show();
                   return;

               }else{
                   dialog.show();
               }



           }
       });







    }

}
