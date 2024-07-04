package com.example.interimapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainApplcations extends AppCompatActivity implements RecycleViewOnItemClick {
    private DatabaseReference usersRef = FirebaseDatabase.getInstance("https://interimapp-e48dc-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("users");
    private DatabaseReference companyRef = FirebaseDatabase.getInstance("https://interimapp-e48dc-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("companies");
    private DatabaseReference appRef = FirebaseDatabase.getInstance("https://interimapp-e48dc-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("applications");
    RecyclerViewAppAdapter adapter;
    String idUser;
    ArrayList<Application> bd = new ArrayList<>();
    ArrayList<Application> filteredList = new ArrayList<>();
    RecyclerView rv;
    Spinner filterSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.applications);

        Intent intent = getIntent();
        if (intent != null) {
            idUser = intent.getStringExtra("id");
        }

        rv = findViewById(R.id.rv_applications);
        filterSpinner = findViewById(R.id.filterSpinner);
        adapter = new RecyclerViewAppAdapter(filteredList, this);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        rv.setHasFixedSize(true);
        rv.setAdapter(adapter);
        rv.setLayoutManager(lm);
        DecoratorRecycleView itemDecorator = new DecoratorRecycleView(20);
        rv.addItemDecoration(itemDecorator);

        appRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bd.clear(); // Clear the list before updating
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Application a = ds.getValue(Application.class);
                    if (a.getUserId().equals(idUser)) {
                        bd.add(a);
                    }
                }
                applyFilter(); // Apply the filter after data change
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        // Set up profile button
        ImageButton homeButton = findViewById(R.id.homeIcon);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainApplcations.this, MainActivity.class);
                intent.putExtra("id", idUser);
                startActivity(intent);
            }
        });

        // Set up profile button
        ImageButton profileButton = findViewById(R.id.profileIcon);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainApplcations.this, ProfileActivity.class);
                intent.putExtra("id", idUser);
                startActivity(intent);
            }
        });

        // Set up message button
        ImageButton message = findViewById(R.id.messageIcon);
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainApplcations.this, MainApplcations.class);
                intent.putExtra("id", idUser);
                startActivity(intent);
            }
        });

        // Set up save offers button
        ImageButton saveIcon = findViewById(R.id.saveIcon);
        saveIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainApplcations.this, SavedOfferActivity.class);
                intent.putExtra("id", idUser);
                startActivity(intent);
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.application_status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(adapter);

        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                applyFilter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                applyFilter();
            }
        });
    }

    private void applyFilter() {
        String selectedStatus = filterSpinner.getSelectedItem().toString();
        filteredList.clear();

        for (Application app : bd) {
            if (selectedStatus.equals("All") || app.getStatut().equalsIgnoreCase(selectedStatus)) {
                filteredList.add(app);
            }
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(MainApplcations.this, beforeintro.class);
        startActivity(intent);
    }

    @Override
    public void onLongItemClick(int position) {
        bd.remove(position);
    }
}
