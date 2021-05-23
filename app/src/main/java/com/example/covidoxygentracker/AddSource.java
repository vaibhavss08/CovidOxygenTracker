package com.example.covidoxygentracker;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddSource extends AppCompatActivity {
    EditText mName,mAddress,mContact,mSupply;
    Button buttonaddsource,buttonviewsource;
    SourceInfo sourceInfo;
    DatabaseReference ref;
    int a;
    int j;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.source_page);

        mName=findViewById(R.id.source_name);
        mAddress=findViewById(R.id.source_address);
        mContact=findViewById(R.id.source_contact);
        mSupply=findViewById(R.id.source_supply);
        ref= FirebaseDatabase.getInstance().getReference("Source");
        sourceInfo=new SourceInfo();

        buttonaddsource=findViewById(R.id.source_add);
        buttonviewsource =findViewById(R.id.view_source);

        buttonaddsource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=mName.getText().toString();
                String address=mAddress.getText().toString();
                String contact=mContact.getText().toString();
                String supply=mSupply.getText().toString();


                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(contact)
                        || TextUtils.isEmpty(address) || TextUtils.isEmpty(supply)) {
                    Toast.makeText(AddSource.this, "Empty fields are not allowed", Toast.LENGTH_SHORT).show();
                } else {
                    addDatatoFirebase(name, address,contact, supply);
                }
            }
        });

        buttonviewsource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddSource.this,ViewSource.class));
            }
        });
    }

    private void addDatatoFirebase(String name, String address, String contact, String supply) {
        sourceInfo.setName(name);
        sourceInfo.setAddress(address);
        sourceInfo.setNumber(contact);
        sourceInfo.setSupply(supply);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                ref.child(name).setValue(sourceInfo);
                Toast.makeText(AddSource.this, "Data Added", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(AddSource.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
