package com.example.rddashboard;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class RecyclerNameAdapter extends RecyclerView.Adapter<RecyclerNameAdapter.nameViewHolder> {

    private Context context;
    private ArrayList<Patient> patients;


    public RecyclerNameAdapter(Context ct, ArrayList<Patient> p){
        context = ct;
        patients = p;
    }

    @NonNull
    @Override
    public nameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.name_row, parent, false);
        return new nameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull nameViewHolder holder, int position) {
        holder.age.setText(patients.get(position).getUserAge());
        holder.email.setText(patients.get(position).getUserEmail());;
        holder.name.setText(patients.get(position).getUserName());

        holder.rowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Dashboard.class);
                intent.putExtra("age", patients.get(position).getUserAge());
                intent.putExtra("email", patients.get(position).getUserEmail());
                intent.putExtra("name", patients.get(position).getUserName());
                intent.putExtra("gender", patients.get(position).getUserGender());
                intent.putExtra("group", patients.get(position).getGroup());
                intent.putExtra("birthday", patients.get(position).getUserBirthday());
                intent.putExtra("weight", patients.get(position).getUserWeight());
                intent.putExtra("height", patients.get(position).getUserHeight());
                intent.putExtra("id", patients.get(position).getUserId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return patients.size();
    }

    public class nameViewHolder extends RecyclerView.ViewHolder{

        TextView name, email, age;
        ConstraintLayout rowLayout;

        public nameViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameText);
            email = itemView.findViewById(R.id.emailValueText);
            age = itemView.findViewById(R.id.ageValueText);
            rowLayout = itemView.findViewById(R.id.rowLayout);

        }
    }
}
