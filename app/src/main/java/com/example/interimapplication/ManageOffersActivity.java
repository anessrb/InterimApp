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

public class ManageOffersActivity extends AppCompatActivity implements RecycleViewOnItemClick {
    ArrayList<ManageOffers> bd = new ArrayList<>();
    RecyclerView rv;
    RecyclerViewOfferAdapter adapter;
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://interimapp-e48dc-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference offersRef = database.getReference("offers");
    DatabaseReference companies = database.getReference("companies");
    String id, companyName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_offers);

        Intent intent = this.getIntent();
        if (intent != null) {
            id = intent.getStringExtra("id");
        }

        rv = findViewById(R.id.rv_manage);
        adapter = new RecyclerViewOfferAdapter(bd, this, this, id);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        rv.setHasFixedSize(true);
        rv.setAdapter(adapter);
        rv.setLayoutManager(lm);
        DecoratorRecycleView itemDecorator = new DecoratorRecycleView(20);
        rv.addItemDecoration(itemDecorator);

        ImageButton home = findViewById(R.id.homeIcon);
        home.setOnClickListener(v -> {
            Intent homeIntent = new Intent(ManageOffersActivity.this, ManageOffersActivity.class);
            homeIntent.putExtra("id", id);
            startActivity(homeIntent);
        });

        ImageButton plus = findViewById(R.id.plusIcon);
        plus.setOnClickListener(v -> {
            Intent addOfferIntent = new Intent(ManageOffersActivity.this, AddOfferActivity.class);
            addOfferIntent.putExtra("id", id);
            startActivity(addOfferIntent);
        });

        ImageButton profileButton = findViewById(R.id.profileIcon);
        profileButton.setOnClickListener(v -> {
            Intent profileIntent = new Intent(ManageOffersActivity.this, profileCompany.class);
            profileIntent.putExtra("id", id);
            startActivity(profileIntent);
        });

        ImageButton message = findViewById(R.id.messageIcon);
        message.setOnClickListener(v -> {
            Intent offersIntent = new Intent(ManageOffersActivity.this, OffersActivity.class);
            offersIntent.putExtra("id", id);
            startActivity(offersIntent);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        companies.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                companyName = snapshot.child("companyName").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        offersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bd.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ManageOffers o = ds.getValue(ManageOffers.class);
                    if (o.getNomCompagnie().equals(companyName)) {
                        bd.add(o);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(ManageOffersActivity.this, beforeintro.class);
        startActivity(intent);
    }

    @Override
    public void onLongItemClick(int position) {
        bd.remove(position);
        adapter.notifyItemRemoved(position);
    }
}
