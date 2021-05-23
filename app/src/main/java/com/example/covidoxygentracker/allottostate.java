package com.example.covidoxygentracker;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class allottostate extends AppCompatActivity {
    DatabaseReference ref;
    long required;
    long to_be_allotted;
    Button allot_state;
    long available;
    int j;
    long b;
    long a;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allot_to_state);
        Intent i=getIntent();
        String state=i.getStringExtra("State");
        String supply=i.getStringExtra("Supply");
        String source=i.getStringExtra("Source Name");

        ref= FirebaseDatabase.getInstance().getReference().child("States").child(state);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                required=(long)snapshot.child("0").getValue();
                available=(long)snapshot.child("1").getValue();
                TextView textView=findViewById(R.id.state_allot);
                textView.setText("Required: "+(required-available));

                TextView textView1=findViewById(R.id.supply_available);
                textView1.setText("Available at source: "+supply);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        DatabaseReference ref3=FirebaseDatabase.getInstance().getReference("Country");
        ref3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    b = Long.parseLong(snapshot.child("1").getValue().toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

        EditText t=findViewById(R.id.editTextNumber);
        allot_state=findViewById(R.id.buttonallot);
        DatabaseReference ref1=FirebaseDatabase.getInstance().getReference("Source").child(source).child("supply");

        allot_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(t.getText().toString()) ) {
                    Toast.makeText(allottostate.this, "Empty fields are not allowed", Toast.LENGTH_SHORT).show();
                }else{
                    to_be_allotted=Long.parseLong(t.getText().toString());
                    if(to_be_allotted > (required-available)){
                        Toast.makeText(allottostate.this,"Entered amount is more than required.",Toast.LENGTH_SHORT).show();
                    }else{
                        j=1;
                        ref1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                while(j-->0) {
                                    a = Long.parseLong(snapshot.getValue().toString());
                                    a -= Long.parseLong(String.valueOf(to_be_allotted));
                                    ref1.setValue(String.valueOf(a));

                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) { }
                        });

                        DatabaseReference ref2=FirebaseDatabase.getInstance().getReference("States").child(state).child("1");
                        ref2.setValue(available+to_be_allotted);

                        DatabaseReference ref4=FirebaseDatabase.getInstance().getReference("Country").child("1");
                        ref4.setValue(b+to_be_allotted);

                        Toast.makeText(allottostate.this,"Oxygen Allotted",Toast.LENGTH_SHORT).show();
                }

                }
            }
        });
    }
}
