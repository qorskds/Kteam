package com.example.kteam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class FindPlayer extends AppCompatActivity {
    private DatabaseReference mdatabase;
    private FirebaseAuth firebaseAuth;
    private ArrayList<findplayerData> arrayList;
    private ArrayList<findplayerData> arrayListtmp;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private findplayerrecyclerViewAdapter adapter;
    private Spinner findlocationSpinner;
    private ArrayAdapter findlocationSpinnerAdapter;
    private ArrayAdapter findheightAdapter;
    private Spinner findlowheight;
    private Spinner findhighheight;
    private boolean height;
    private Spinner findpositon;
    private ArrayAdapter findpositonadapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_player);
        mdatabase = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        recyclerView = (RecyclerView) findViewById(R.id.findplayerRecycler);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();
        adapter = new findplayerrecyclerViewAdapter(arrayList);
        recyclerView.setAdapter(adapter);
        Button findNameButton = (Button) findViewById(R.id.findNameButton);
        final EditText findName = (EditText) findViewById(R.id.findName);
        findlocationSpinner = (Spinner) findViewById(R.id.findlocationSpinner);
        findpositon= (Spinner)findViewById(R.id.findpositon);

        findpositonadapter= ArrayAdapter.createFromResource(this,R.array.position,R.layout.support_simple_spinner_dropdown_item);
        findpositon.setAdapter(findpositonadapter);

        findlocationSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.city, R.layout.support_simple_spinner_dropdown_item);
        findlocationSpinner.setAdapter(findlocationSpinnerAdapter);
        findlowheight = (Spinner) findViewById(R.id.findlowheight);
        findhighheight = (Spinner) findViewById(R.id.findhighheight);
        findheightAdapter = ArrayAdapter.createFromResource(this, R.array.height, R.layout.support_simple_spinner_dropdown_item);
        findhighheight.setAdapter(findheightAdapter);
        findlowheight.setAdapter(findheightAdapter);




        findNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mdatabase.child("users").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        arrayList.clear();
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            arrayListtmp = new ArrayList<>();


                            if (findName.getText().toString().equals("")) {
                                if (dataSnapshot1.child("playerRegister").getValue().toString().equals("true")
                                        && dataSnapshot1.child("locationText").getValue().toString().equals(findlocationSpinner.getSelectedItem())
                                        && Integer.parseInt((String) findhighheight.getSelectedItem()) >= Integer.parseInt(dataSnapshot1.child("heightText").getValue().toString())
                                        && Integer.parseInt((String) findlowheight.getSelectedItem()) <= Integer.parseInt(dataSnapshot1.child("heightText").getValue().toString())
                                        && dataSnapshot1.child("positionText").getValue().toString().equals(findpositon.getSelectedItem().toString())
                                ) {
                                    findplayerData tmp = new findplayerData(dataSnapshot1.child("nicknameText").getValue().toString(),
                                            dataSnapshot1.child("bulid").getValue().toString(), dataSnapshot1.child("heightText").getValue().toString(),
                                            dataSnapshot1.child("positionText").getValue().toString(), dataSnapshot1.child("character").getValue().toString(), dataSnapshot1.child("locationText").getValue().toString(),dataSnapshot1.getKey());
                                    arrayList.add(tmp);
                                    adapter.notifyDataSetChanged();

                                }
                            } else {
                                if (dataSnapshot1.child("playerRegister").getValue().toString().equals("true")
                                        && dataSnapshot1.child("locationText").getValue().toString().equals(findlocationSpinner.getSelectedItem())
                                        && Integer.parseInt((String) findhighheight.getSelectedItem()) >= Integer.parseInt(dataSnapshot1.child("heightText").getValue().toString())
                                        && Integer.parseInt((String) findlowheight.getSelectedItem()) <= Integer.parseInt(dataSnapshot1.child("heightText").getValue().toString())
                                        && dataSnapshot1.child("positionText").getValue().toString().equals(findpositon.getSelectedItem().toString())
                                ) {
                                    Log.e("asdf","asdf");
                                    if (dataSnapshot1.child("nicknameText").getValue().toString().equals(findName.getText().toString())) {
                                        findplayerData tmp = new findplayerData(dataSnapshot1.child("nicknameText").getValue().toString(),
                                                dataSnapshot1.child("bulid").getValue().toString(), dataSnapshot1.child("heightText").getValue().toString(),
                                                dataSnapshot1.child("positionText").getValue().toString(), dataSnapshot1.child("character").getValue().toString(), dataSnapshot1.child("locationText").getValue().toString(),dataSnapshot1.getKey());
                                        arrayList.add(tmp);
                                        adapter.notifyDataSetChanged();
                                    }

                                }
                            }
                        }

                        if (arrayList.size() == 0) {
                            Toast.makeText(FindPlayer.this, "위와 같은 이름의 선수가 없습니다.", Toast.LENGTH_SHORT).show();
                            adapter.notifyDataSetChanged();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        mdatabase.child("users").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    if (dataSnapshot1.hasChildren()) {
                        if (dataSnapshot1.child("playerRegister").getValue().equals(true)) {
                            findplayerData tmp = new findplayerData(dataSnapshot1.child("nicknameText").getValue().toString(),
                                    dataSnapshot1.child("bulid").getValue().toString(), dataSnapshot1.child("heightText").getValue().toString(),
                                    dataSnapshot1.child("positionText").getValue().toString(), dataSnapshot1.child("character").getValue().toString(), dataSnapshot1.child("locationText").getValue().toString(),dataSnapshot1.getKey());
                            arrayList.add(tmp);
                            adapter.notifyDataSetChanged();

                        }
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        findhighheight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (Integer.parseInt(findhighheight.getSelectedItem().toString()) < Integer.parseInt(findlowheight.getSelectedItem().toString())) {

                    Toast.makeText(FindPlayer.this, "최소 값보다 작습니다.", Toast.LENGTH_SHORT).show();
                    findhighheight.setSelection(Integer.parseInt((findlowheight.getSelectedItem().toString())));

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        findlowheight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (Integer.parseInt(findhighheight.getSelectedItem().toString()) < Integer.parseInt(findlowheight.getSelectedItem().toString())) {
                    Toast.makeText(FindPlayer.this, "최대 값보다 큽니다.", Toast.LENGTH_SHORT).show();
                    findlowheight.setSelection(Integer.parseInt((findhighheight.getSelectedItem().toString())) - 1);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
}
