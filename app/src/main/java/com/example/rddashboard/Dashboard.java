package com.example.rddashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {

    private TextView nameText, ageText, dobText, genderText, emailText, heightText, weightText, mmText,
            groupText, hrText, stepsText, hrLabel, stepsLabel, noData, mmNoData, noActivity;
    private ImageView timeImage;
    private String age, name, email, dob, gender, height, weight, group, id;
    private Spinner dateSpinner, weekSpinner;
    private RecyclerView activityRecycler;
    private DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        nameText = findViewById(R.id.dbNameText);
        ageText = findViewById(R.id.dbAgeValueText);
        emailText = findViewById(R.id.dbEmailValueText);
        dobText = findViewById(R.id.dbBdayValueText);
        genderText = findViewById(R.id.dbSexValueText);
        heightText = findViewById(R.id.dbHeightValueText);
        weightText = findViewById(R.id.dbWeightValueText);
        groupText = findViewById(R.id.dbGroupValueText);
        dateSpinner = findViewById(R.id.dateSpinner);
        weekSpinner = findViewById(R.id.weekSpinner);
        mmText = findViewById(R.id.mmValueText);
        hrText = findViewById(R.id.hrValueText);
        hrLabel = findViewById(R.id.hrLabelText);
        stepsText = findViewById(R.id.stepValueText);
        stepsLabel = findViewById(R.id.stepLabelText);
        noData = findViewById(R.id.noDataText);
        mmNoData = findViewById(R.id.mmNoData);
        noActivity = findViewById(R.id.noActivityText);
        timeImage = findViewById(R.id.timeImage);
        activityRecycler = findViewById(R.id.activityRecycler);

        getData();
        setData();

        ref = FirebaseDatabase.getInstance().getReference("-MRYHneQ9TamoJf_f5mP").child(id);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ref != null) {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        if (snapshot.hasChild("StatsByDate")) {
                            ArrayList<Date> dateList = new ArrayList<>();
                            ArrayList<String> dates = new ArrayList<>();
                            String hr = null;
                            String stp = null;
                            for (DataSnapshot ds : snapshot.child("StatsByDate").getChildren()) {
                                dates.add(ds.getKey());
                                if (ds.hasChild("heartRate") && ds.hasChild("steps")) {
                                    hr = ds.child("heartRate").getValue().toString();
                                    stp = ds.child("steps").getValue().toString();
                                } else if (ds.hasChild("heartRate")) {
                                    hr = ds.child("heartRate").getValue().toString();
                                    stp = "No Data";
                                } else if (ds.hasChild("steps")) {
                                    hr = "No Data";
                                    stp = ds.child("steps").getValue().toString();
                                }

                                if (ds.hasChild("activityTrack")) {
                                    ArrayList<Activities> activities = new ArrayList<>();
                                    for (DataSnapshot dataSnapshot : ds.child("activityTrack").getChildren()) {
                                        activities.add(dataSnapshot.getValue(Activities.class));
                                    }
                                    dateList.add(new Date(stp, hr, activities));
                                } else {
                                    dateList.add(new Date(stp, hr));
                                }

                            }
                            ArrayAdapter<String> dateAdapter =
                                    new ArrayAdapter<String>(Dashboard.this,
                                            android.R.layout.simple_spinner_dropdown_item, dates);
                            dateSpinner.setAdapter(dateAdapter);
                            dateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    hrText.setText(dateList.get(position).getHeartRate());
                                    stepsText.setText(dateList.get(position).getSteps());

                                    if (dateList.get(position).getActivityTrack() != null) {
                                        activityRecycler.setVisibility(View.VISIBLE);
                                        noActivity.setVisibility(View.GONE);
                                        RecyclerDateAdapter dateAdapter = new RecyclerDateAdapter(Dashboard.this,
                                                dateList.get(position).getActivityTrack());
                                        activityRecycler.setAdapter(dateAdapter);
                                        activityRecycler.setLayoutManager(new LinearLayoutManager(Dashboard.this));
                                    } else {
                                        activityRecycler.setVisibility(View.GONE);
                                        noActivity.setVisibility(View.VISIBLE);
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        } else {
                            hrText.setVisibility(View.GONE);
                            hrLabel.setVisibility(View.GONE);
                            stepsText.setVisibility(View.GONE);
                            stepsLabel.setVisibility(View.GONE);
                            dateSpinner.setVisibility(View.GONE);
                            activityRecycler.setVisibility(View.GONE);
                            noData.setVisibility(View.VISIBLE);
                        }
                        if (snapshot.hasChild("moderateMin")) {
                            ArrayList<String> weeks = new ArrayList<>();
                            ArrayList<String> weekData = new ArrayList<>();
                            for (DataSnapshot ds : snapshot.child("moderateMin").getChildren()) {
                                  weeks.add(ds.getKey());
                                  weekData.add(ds.child("moderateMins").getValue().toString());
                                  //String.valueOf(Float.parseFloat(ds.child("moderateMins").getValue().toString()) * 60)
                            }
                            ArrayAdapter<String> weekAdapter = new ArrayAdapter<String>(Dashboard.this,
                                    android.R.layout.simple_spinner_dropdown_item, weeks);
                            weekSpinner.setAdapter(weekAdapter);
                            weekSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    mmText.setText(String.valueOf(Float.parseFloat(weekData.get(position)) * 60));
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        } else {
                            weekSpinner.setVisibility(View.GONE);
                            timeImage.setVisibility(View.GONE);
                            mmText.setVisibility(View.GONE);
                            mmNoData.setVisibility(View.VISIBLE);
                        }

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(Dashboard.this, error.getCode(), Toast.LENGTH_SHORT);
                }
            });
        }
    }

    private void getData() {
        if (getIntent().hasExtra("age") && getIntent().hasExtra("name") &&
                getIntent().hasExtra("email") && getIntent().hasExtra("birthday") &&
                getIntent().hasExtra("weight") && getIntent().hasExtra("height")
                && getIntent().hasExtra("group") && getIntent().hasExtra("gender")
                && getIntent().hasExtra("id")) {

            age = getIntent().getStringExtra("age");
            name = getIntent().getStringExtra("name");
            email = getIntent().getStringExtra("email");
            dob = getIntent().getStringExtra("birthday");
            weight = getIntent().getStringExtra("weight");
            height = getIntent().getStringExtra("height");
            group = getIntent().getStringExtra("group");
            gender = getIntent().getStringExtra("gender");
            id = getIntent().getStringExtra("id");


        } else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }

    private void setData() {
        nameText.setText(name);
        ageText.setText(age);
        emailText.setText(email);
        dobText.setText(dob);
        genderText.setText(gender);
        heightText.setText(height);
        weightText.setText(weight);
        groupText.setText(group);

    }
}