package com.example.rddashboard;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerDateAdapter extends RecyclerView.Adapter<RecyclerDateAdapter.dateViewHolder>{

    private Context context;
    private ArrayList<Activities> activities;

    public RecyclerDateAdapter(Context context, ArrayList<Activities> activity) {
        this.context = context;
        this.activities = activity;
    }

    @NonNull
    @Override
    public dateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_row, parent, false);
        return new dateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull dateViewHolder holder, int position) {
        holder.activityText.setText(activities.get(position).getActivity());
        holder.durationText.setText(activities.get(position).getDuration());
        holder.cTimeText.setText(activities.get(position).getcTime());
        holder.avHrText.setText(activities.get(position).getAvrHeartRate());
    }

    @Override
    public int getItemCount() {
        return activities.size();
    }

    public class dateViewHolder extends RecyclerView.ViewHolder{

        TextView activityText, avHrText, cTimeText, durationText;
        ConstraintLayout rowLayout;

        public dateViewHolder(@NonNull View itemView) {
            super(itemView);
            activityText = itemView.findViewById(R.id.activityNameText);
            avHrText = itemView.findViewById(R.id.avHrValueText);
            cTimeText = itemView.findViewById(R.id.cTimeValueText);
            durationText = itemView.findViewById(R.id.durationValueText);
            rowLayout = itemView.findViewById(R.id.rowLayout);
        }
    }
}
