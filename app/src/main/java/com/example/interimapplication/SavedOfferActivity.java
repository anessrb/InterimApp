package com.example.interimapplication;

import android.annotation.SuppressLint;
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

public class SavedOfferActivity extends AppCompatActivity implements RecycleViewOnItemClick {

    ArrayList<ManageOffers> bd = new ArrayList<>();
    RecyclerView rv;
    SavedOfferAdapter adapter;
    String id;

    private DatabaseReference usersRef = FirebaseDatabase.getInstance("https://interimapp-e48dc-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("users");
    private DatabaseReference companyRef =FirebaseDatabase.getInstance("https://interimapp-e48dc-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("companies");
    private DatabaseReference appRef =FirebaseDatabase.getInstance("https://interimapp-e48dc-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("applications");
    private DatabaseReference savedRef =FirebaseDatabase.getInstance("https://interimapp-e48dc-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("savedOffers");
    private DatabaseReference offersRef =FirebaseDatabase.getInstance("https://interimapp-e48dc-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("offers");

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saved_offers);

        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getStringExtra("id");
        }

        // Set up profile button
        ImageButton homeButton = findViewById(R.id.homeIcon);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SavedOfferActivity.this, MainActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        // Set up profile button
        ImageButton profileButton = findViewById(R.id.profileIcon);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SavedOfferActivity.this, ProfileActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        // Set up message button
        ImageButton message = findViewById(R.id.messageIcon);
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SavedOfferActivity.this, MainApplcations.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        // Set up save offers button
        ImageButton saveIcon = findViewById(R.id.saveIcon);
        saveIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SavedOfferActivity.this, SavedOfferActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        rv = findViewById(R.id.rv_savedOffers);
        adapter = new SavedOfferAdapter(bd, this);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        rv.setHasFixedSize(true);
        rv.setAdapter(adapter);
        rv.setLayoutManager(lm);
        DecoratorRecycleView itemDecorator = new DecoratorRecycleView(20);
        rv.addItemDecoration(itemDecorator);
    }

    protected void onStart() {
        super.onStart();
        ArrayList<String> offersId = new ArrayList<>();
        savedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    SavedOffers s = ds.getValue(SavedOffers.class);
                    if(s.getIdUser().equals(id) && !offersId.contains(s.getIdOffer())) offersId.add(s.getIdOffer());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });

        offersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bd.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    ManageOffers of = ds.getValue(ManageOffers.class);
                    if(offersId.contains(of.getId())) bd.add(of);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(SavedOfferActivity.this, DescriptionActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("Photo", bd.get(position).getPhoto());
        intent.putExtra("Titre", bd.get(position).getTitre());
        intent.putExtra("nomCompagnie", bd.get(position).getNomCompagnie());
        intent.putExtra("Salaire", bd.get(position).getSalaire());
        intent.putExtra("Lieu", bd.get(position).getLieu());
        intent.putExtra("Duree", bd.get(position).getDuree());
        intent.putExtra("Temps", bd.get(position).getTemps());
        intent.putExtra("Description", bd.get(position).getDescription());
        intent.putExtra("offerId", bd.get(position).getId());
        intent.putExtra("From", "SavedOfferActivity");
        startActivity(intent);
    }

    @Override
    public void onLongItemClick(int position) {

    }
}
