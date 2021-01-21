package com.example.rddashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference ref;
    private RecyclerView nameList;
    private ArrayList<Patient> patientData;
    private ProgressBar progressBar;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ref = FirebaseDatabase.getInstance().getReference("-MRYHneQ9TamoJf_f5mP");
        nameList = findViewById(R.id.nameListRecycler);
        progressBar = findViewById(R.id.progressBarMain);
        searchView = findViewById(R.id.searchView);

    }

    public DatabaseReference getRef() {
        return ref;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(ref != null){
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        progressBar.setVisibility(View.GONE);
                        patientData = new ArrayList<>();
                        int count = 0;
                        for(DataSnapshot ds : snapshot.getChildren()){
                            patientData.add(ds.child("userInfo").getValue(Patient.class));
                            patientData.get(count).setUserId(ds.getKey());
                            count++;
                        }
                        RecyclerNameAdapter nameAdapter = new RecyclerNameAdapter(MainActivity.this, patientData);
                        nameList.setAdapter(nameAdapter);
                        nameList.setLayoutManager(new LinearLayoutManager(MainActivity.this));

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(MainActivity.this, error.getCode(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        if(searchView != null){
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    search(newText);
                    return false;
                }
            });
        }
    }

    private void search(String str){
        ArrayList<Patient> searchPatientList = new ArrayList<>();
        for(Patient patient : patientData){
            if(patient.getUserName().toLowerCase().contains(str.toLowerCase())){
                searchPatientList.add(patient);
            }
        }
        RecyclerNameAdapter recyclerNameAdapter = new RecyclerNameAdapter(MainActivity.this, searchPatientList);
        nameList.setAdapter(recyclerNameAdapter);
    }
}