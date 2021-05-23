package com.example.covidoxygentracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class ParticularState extends AppCompatActivity {
    DatabaseReference ref;
    String state_name;
    long avail;
    long require;
    long fulfil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.state_particular);
        state_name=getIntent().getStringExtra("State Name");

        TextView textView=findViewById(R.id.state_heading);
        textView.setText(state_name);

        ref= FirebaseDatabase.getInstance().getReference().child("States").child(state_name);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                avail= (long) snapshot.child("1").getValue();
                require=(long) snapshot.child("0").getValue();
                TextView a=findViewById(R.id.available);
                a.setText("Allotted "+ String.valueOf(avail));

                TextView r=findViewById(R.id.required);
                r.setText("Required "+String.valueOf(require));

                if(require==0 || require<=avail){
                    fulfil=100;
                }else {
                    fulfil = (long) ((long) (avail*100 / require));
                }

                CircleProgressBar progress=findViewById(R.id.state_progressBar);

                progress.setProgressWithAnimation(fulfil);
                progress.setStrokeWidth(50);
                TextView t=findViewById(R.id.progresstext);
                t.setText(String.valueOf(fulfil)+"%");
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
        state_name=getIntent().getStringExtra("State Name")+" Analysis";

    }
}