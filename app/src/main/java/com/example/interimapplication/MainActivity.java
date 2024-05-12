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

public class MainActivity extends AppCompatActivity implements RecycleViewOnItemClick {
    ArrayList<ManageOffers> bd = new ArrayList<>();
    RecyclerView rv;
    RecyclerViewHomeAdapter adapter;
    String id;

    FirebaseDatabase database = FirebaseDatabase.getInstance("https://interimapp-e48dc-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference offersRef = database.getReference("offers");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getStringExtra("id");
        }

        rv = findViewById(R.id.rv_main);
        adapter = new RecyclerViewHomeAdapter(bd, this);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        rv.setHasFixedSize(true);
        rv.setAdapter(adapter);
        rv.setLayoutManager(lm);
        DecoratorRecycleView itemDecorator = new DecoratorRecycleView(20);
        rv.addItemDecoration(itemDecorator);

        // Récupérer une référence vers le bouton de profil
        ImageButton profileButton = findViewById(R.id.profileIcon);

        // Ajouter un écouteur de clic au bouton de profil
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent pour ouvrir la page de profil
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        ImageButton message = findViewById(R.id.messageIcon);
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainApplcations.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
    }

    protected void onStart() {
        super.onStart();

        offersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bd.clear(); // Clear the list before updating
                for(DataSnapshot ds : snapshot.getChildren()){
                    ManageOffers o = ds.getValue(ManageOffers.class);
                    bd.add(o);
                }
                adapter.notifyDataSetChanged(); // Notify the adapter of data changes
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(MainActivity.this, DescriptionActivity.class);
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
        startActivity(intent);
    }

    @Override
    public void onLongItemClick(int position) {
        bd.remove(position);
    }
}
