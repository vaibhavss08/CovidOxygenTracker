package com.example.covidoxygentracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Country extends AppCompatActivity {
    FirebaseAuth mAuth;
    DatabaseReference ref;
    long avail;
    long require;
    long fulfil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.country_page);
        ref= FirebaseDatabase.getInstance().getReference().child("Country");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                avail= (long) snapshot.child("1").getValue();
                require=(long) snapshot.child("0").getValue();
                TextView a=findViewById(R.id.countryavailable);
                a.setText("Allotted "+ String.valueOf(avail));

                TextView r=findViewById(R.id.countryrequire);
                r.setText("Required "+String.valueOf(require));

                if(require==0 || require<=avail){
                    fulfil=100;
                }else {
                    fulfil = (long) ((long) (avail*100 / require));
                }

                CircleProgressBar progress=findViewById(R.id.custom_progressBar);

                progress.setProgressWithAnimation(fulfil);
                progress.setStrokeWidth(50);

                TextView t=findViewById(R.id.countryprogress);
                t.setText(String.valueOf(fulfil)+"%");
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });


        CircleProgressBar progressBar=findViewById(R.id.custom_progressBar);
        progressBar.setProgressWithAnimation(60);
        progressBar.setStrokeWidth(50);
        Button state = findViewById(R.id.state);
        Button source= findViewById(R.id.addsource);

        state.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Country.this,StateAnalysis.class));
            }
        });


        source.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Country.this, AddSource.class));
            }
        });

        Button signout=findViewById(R.id.sign_out);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Country.this,MainActivity.class));
            }
        });

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Country.this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                finish();
            }
        });
        builder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert=builder.create();
        alert.show();
    }
}
