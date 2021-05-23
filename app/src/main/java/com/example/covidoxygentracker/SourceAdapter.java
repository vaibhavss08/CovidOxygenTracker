package com.example.covidoxygentracker;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static androidx.core.content.ContextCompat.startActivity;

public class SourceAdapter extends RecyclerView.Adapter<SourceAdapter.myViewHolder> {

    static ArrayList<SourceInfo> list;
    public SourceAdapter(ArrayList<SourceInfo> list) {
        this.list = list;
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.source_particular,parent,false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        SourceInfo info=list.get(position);
        holder.name.setText("Name: "+info.getName());
        holder.address.setText("Address: "+info.getAddress());
        holder.contact.setText("Contact: " + info.getNumber());
        holder.supply.setText("Supply: "+info.getSupply());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder  {

        TextView name,address,contact,supply;
        Button allot;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.nam);
            address=itemView.findViewById(R.id.add);
            contact=itemView.findViewById(R.id.cont);
            supply=itemView.findViewById(R.id.sup);
            allot=itemView.findViewById(R.id.allot_to_state);
            allot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(v.getContext(),allot_oxygen.class);
                    intent.putExtra("Source Name",list.get(getAdapterPosition()).name);
                    v.getContext().startActivity(intent);
                }
            });
        }

    }

}
