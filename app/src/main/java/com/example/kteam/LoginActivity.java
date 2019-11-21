package com.example.kteam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText emailEditText;
    private EditText passwordEditText;
    private CheckBox checkBoxid;
    private CheckBox checkBoxpwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


        SharedPreferences pref= getSharedPreferences("pref", Activity.MODE_PRIVATE);

        String id =pref.getString("id_save","");
        String pawd= pref.getString("pwd_save","");
        Boolean checkid= pref.getBoolean("checkid",false);
        Boolean checkpwd=pref.getBoolean("checkpwd",false);


        TextView register = (TextView)findViewById(R.id.register);
        Button loginButton = (Button)findViewById(R.id.LoginButton);
        emailEditText = (EditText)findViewById(R.id.userEmail);
        passwordEditText= (EditText)findViewById(R.id.userPassword);
        checkBoxid= (CheckBox)findViewById(R.id.storeID);
        checkBoxpwd=(CheckBox)findViewById(R.id.storePwd);

        final SharedPreferences.Editor editor = pref.edit();

        if(checkid==true){
            emailEditText.setText(id);
            checkBoxid.setChecked(checkid);
        }
        if(checkpwd==true){
            passwordEditText.setText(pawd);
            checkBoxpwd.setChecked(checkpwd);
        }



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                if(email.isEmpty()||password.isEmpty()){

                    Toast.makeText(LoginActivity.this,"빈칸이 있습니다.",Toast.LENGTH_SHORT).show();
                    return;
                }
                firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            finish();
                            editor.putString("id_save",emailEditText.getText().toString());
                            editor.putBoolean("checkid",checkBoxid.isChecked());
                            editor.putString("pwd_save",passwordEditText.getText().toString());
                            editor.putBoolean("checkpwd",checkBoxpwd.isChecked());
                            editor.commit();
                            LoginActivity.this.startActivity(intent);
                        }else{
                            Toast.makeText(LoginActivity.this,"로그인 실패",Toast.LENGTH_SHORT).show();
                        }
                    }
                });



            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this,Register.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });






    }
}
