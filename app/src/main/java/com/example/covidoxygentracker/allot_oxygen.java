package com.example.covidoxygentracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class allot_oxygen extends AppCompatActivity {
    StateAdapter adapter;
    ArrayList<String> statesArrayList;
    DatabaseReference ref;
    String availabl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allot_oxygen);

        Intent intent=getIntent();
        String source_name=intent.getStringExtra("Source Name");

        ref=FirebaseDatabase.getInstance().getReference("Source").child(source_name);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                availabl=snapshot.child("supply").getValue().toString();
                TextView textView=findViewById(R.id.available);
                TextView textView2=findViewById(R.id.allot_source_name);
                textView2.setText(source_name);
                if(Integer.parseInt(availabl)==0){
                    textView.setText("Oxygen is not available");
                }else{
                    textView.setText("Available Oxygen: "+availabl);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

            String [] states_list={"Andaman and Nicobar Islands","Andhra Pradesh","Arunachal Pradesh",
                    "Assam", "Bihar", "Chandigarh", "Chhattisgarh","Dadra and Nagar Haveli","Daman and Diu",
                    "Delhi","Goa","Gujarat", "Haryana","Himachal Pradesh","Jammu and Kashmir","Jharkhand",
                    "Karnataka","Kerala","Ladakh","Lakshadweep", "Madhya Pradesh","Maharashtra","Manipur",
                    "Meghalaya","Mizoram","Nagaland","Orissa","Puducherry", "Punjab","Rajasthan","Sikkim",
                    "Tamil Nadu","Telangana","Tripura","Uttaranchal","Uttar Pradesh", "West Bengal"};

            ListView listView=(ListView) findViewById(R.id.states_list);
            statesArrayList=new ArrayList<String>(Arrays.asList(states_list));
            adapter = new StateAdapter(this, statesArrayList);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent1=new Intent(getBaseContext(),allottostate.class);
                    intent1.putExtra("State", (String) parent.getItemAtPosition(position));
                    intent1.putExtra("Supply",availabl);
                    intent1.putExtra("Source Name",source_name);
                    startActivity(intent1);
                }
            });
    }
}
