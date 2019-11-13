package com.example.kteam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
