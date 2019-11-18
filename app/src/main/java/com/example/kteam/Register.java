package com.example.kteam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {
    private DatabaseReference mdatabase;
    private AlertDialog dialog;
    private FirebaseAuth firebaseAuth;
    private Spinner bulidSpinner;
    private Spinner locationSpinner;
    private ArrayAdapter bulidadapter;
    private ArrayAdapter locationadapter;
   private Spinner heightSpinner;
   private ArrayAdapter heightadapter;
    String email;
    String password;
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-0!@#$%^&*?_~]{4,16}$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mdatabase = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        bulidSpinner = (Spinner)findViewById(R.id.bulidSpinner);
        locationSpinner = (Spinner)findViewById(R.id.location);
        heightSpinner=(Spinner)findViewById(R.id.heightText);

        locationadapter = ArrayAdapter.createFromResource(this,R.array.city,R.layout.support_simple_spinner_dropdown_item);
        locationSpinner.setAdapter(locationadapter);


        bulidadapter =ArrayAdapter.createFromResource(this,R.array.bulid,R.layout.support_simple_spinner_dropdown_item);
        bulidSpinner.setAdapter(bulidadapter);

        heightadapter = ArrayAdapter.createFromResource(this,R.array.height,R.layout.support_simple_spinner_dropdown_item);
        heightSpinner.setAdapter(heightadapter);



        final EditText passwordText = ((EditText) findViewById(R.id.PasswordText));
        final EditText emailText = ((EditText) findViewById(R.id.EmailText));
        final EditText ageText =(EditText)findViewById(R.id.ageText);
       final EditText nicknameText=(EditText)findViewById(R.id.nicknameText);
        final EditText positionText = (EditText)findViewById(R.id.positionText);
        final EditText characterText = (EditText)findViewById(R.id.characterText);



        Button SingUpButton = (Button) findViewById(R.id.SingUpButton);
        SingUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emailText.getText().toString().isEmpty()||ageText.getText().toString().isEmpty()||passwordText.getText().toString().
                        isEmpty()||nicknameText.getText().toString().isEmpty()||positionText.getText().toString().isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                    dialog = builder.setMessage(("빈칸이 있습니다.")).setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }
                firebaseAuth.createUserWithEmailAndPassword(emailText.getText().toString(),passwordText.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            Toast.makeText(Register.this,"회원가입 성공",Toast.LENGTH_SHORT).show();
                            String uid = FirebaseAuth.getInstance().getUid();
                            Map<String,Object> tmp = new HashMap<>();
                            tmp.put("userEmail",emailText.getText().toString());
                            tmp.put("ageText",ageText.getText().toString());
                            tmp.put("heightText",heightSpinner.getSelectedItem());
                            tmp.put("nicknameText",nicknameText.getText().toString());
                            tmp.put("positionText",positionText.getText().toString());
                            tmp.put("bulid",bulidSpinner.getSelectedItem().toString());
                            tmp.put("character",characterText.getText().toString());
                            tmp.put("locationText",locationSpinner.getSelectedItem().toString());
                            tmp.put("myTeamName","");
                            tmp.put("playerRegister",false);

                            mdatabase.child("users").child(uid).updateChildren(tmp);
                            Intent intent = new Intent(Register.this, LoginActivity.class);
                            finish();
                            Register.this.startActivity(intent);

                        }else{
                            Toast.makeText(Register.this,"회원가입 실패",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }

        });

    }

}
