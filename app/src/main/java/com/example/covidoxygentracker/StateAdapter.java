package com.example.covidoxygentracker;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.covidoxygentracker.R;

import java.util.ArrayList;

public class StateAdapter extends ArrayAdapter<String> {

    public StateAdapter(Activity context, ArrayList<String> statesArrayList){
        super(context, 0, statesArrayList);
    }

    @Override
    public View getView(int position, View convertView,ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate( R.layout.state, parent, false);
        }

        String st = getItem(position);
        TextView o2 = listItemView.findViewById(R.id.state_individual);
        o2.setText(st);
        return listItemView;
    }
}
