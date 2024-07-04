package com.example.interimapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

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
import java.util.List;

public class SavedOffersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SavedOffersAdapter adapter;
    private List<SavedOffer> savedOffersList;
    private DatabaseReference savedOffersRef;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_offers);

        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getStringExtra("id");
        }

        // Set up profile button
        ImageButton homeButton = findViewById(R.id.homeIcon);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SavedOffersActivity.this, MainActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        // Set up profile button
        ImageButton profileButton = findViewById(R.id.profileIcon);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SavedOffersActivity.this, ProfileActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        // Set up message button
        ImageButton message = findViewById(R.id.messageIcon);
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SavedOffersActivity.this, MainApplcations.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        // Set up save offers button
        ImageButton saveIcon = findViewById(R.id.saveIcon);
        saveIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SavedOffersActivity.this, SavedOffersActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.rv_savedOffers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        savedOffersList = new ArrayList<>();
        adapter = new SavedOffersAdapter(this, savedOffersList);
        recyclerView.setAdapter(adapter);
        DecoratorRecycleView itemDecorator = new DecoratorRecycleView(20);
        recyclerView.addItemDecoration(itemDecorator);

        savedOffersRef = FirebaseDatabase.getInstance("https://interimapp-e48dc-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("savedOffers");

        savedOffersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                savedOffersList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    SavedOffer offer = ds.getValue(SavedOffer.class);
                    //if(offer.getIdUser().equals(id))
                        savedOffersList.add(offer);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }
}
