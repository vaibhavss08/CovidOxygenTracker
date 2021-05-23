package com.example.covidoxygentracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class StateAnalysis extends AppCompatActivity {
    StateAdapter adapter;
    ArrayList<String> statesArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.state_view);

        String [] states_list={"Andaman and Nicobar Islands","Andhra Pradesh","Arunachal Pradesh",
                "Assam", "Bihar", "Chandigarh", "Chhattisgarh","Dadra and Nagar Haveli","Daman and Diu",
                "Delhi","Goa","Gujarat", "Haryana","Himachal Pradesh","Jammu and Kashmir","Jharkhand",
                "Karnataka","Kerala","Ladakh","Lakshadweep", "Madhya Pradesh","Maharashtra","Manipur",
                "Meghalaya","Mizoram","Nagaland","Orissa","Puducherry", "Punjab","Rajasthan","Sikkim",
                "Tamil Nadu","Telangana","Tripura","Uttaranchal","Uttar Pradesh", "West Bengal"};

        ListView listView=(ListView) findViewById(R.id.states);
        statesArrayList=new ArrayList<String>(Arrays.asList(states_list));
        adapter = new StateAdapter(this, statesArrayList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getBaseContext(), ParticularState.class);
                intent.putExtra("State Name", (String)parent.getItemAtPosition(position));
                startActivity(intent);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search,menu);
        MenuItem menuItem=menu.findItem(R.id.search);
        SearchView searchView=(SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}