package com.example.interimapplication;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class DescriptionActivity extends AppCompatActivity implements OnMapReadyCallback {
    private DatabaseReference usersRef = FirebaseDatabase.getInstance("https://interimapp-e48dc-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("users");
    private DatabaseReference companyRef = FirebaseDatabase.getInstance("https://interimapp-e48dc-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("companies");
    private DatabaseReference appRef = FirebaseDatabase.getInstance("https://interimapp-e48dc-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("applications");

    private MapView mapView;
    private GoogleMap gMap;

    String titre, salaire, lieu, duree, description, idUser, nomCompagnie, idCompany, idOffer;
    String statut = "Pending";
    int photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description);

        Intent intent = getIntent();
        if (intent != null) {
            photo = intent.getIntExtra("Photo", 0);
            titre = intent.getStringExtra("Titre");
            idUser = intent.getStringExtra("id");
            salaire = intent.getStringExtra("Salaire");
            lieu = intent.getStringExtra("Lieu");
            duree = intent.getStringExtra("Duree");
            description = intent.getStringExtra("Description");
            nomCompagnie = intent.getStringExtra("nomCompagnie");
            idOffer = intent.getStringExtra("offerId");

            TextView cityTextView = findViewById(R.id.textView160);
            cityTextView.setText(lieu);

            TextView durationTextView = findViewById(R.id.textView162);
            durationTextView.setText(duree);

            TextView salaryTextView = findViewById(R.id.textView16);
            salaryTextView.setText(salaire);

            TextView descriptionTextView = findViewById(R.id.textView15);
            descriptionTextView.setText(description);
        }

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        companyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Company c = ds.getValue(Company.class);
                    if (c.getCompanyName().equals(nomCompagnie)) {
                        idCompany = c.getCompanyId();
                    }
                }
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
                Intent intent = new Intent(DescriptionActivity.this, MainActivity.class);
                intent.putExtra("id", idUser);
                startActivity(intent);
            }
        });

        // Set up profile button
        ImageButton profileButton = findViewById(R.id.profileIcon);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DescriptionActivity.this, ProfileActivity.class);
                intent.putExtra("id", idUser);
                startActivity(intent);
            }
        });

        // Set up message button
        ImageButton message = findViewById(R.id.messageIcon);
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DescriptionActivity.this, MainApplcations.class);
                intent.putExtra("id", idUser);
                startActivity(intent);
            }
        });

        // Set up save offers button
        ImageButton saveIcon = findViewById(R.id.saveIcon);
        saveIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DescriptionActivity.this, SavedOfferActivity.class);
                intent.putExtra("id", idUser);
                startActivity(intent);
            }
        });

        Button apply = findViewById(R.id.apply);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = appRef.push().getKey();


                Application app = new Application(photo, titre, nomCompagnie, statut, "Aujourdhui", idUser, idCompany, idOffer, id);

                appRef.child(id).setValue(app);

                Intent intent = new Intent(DescriptionActivity.this, MainActivity.class);
                intent.putExtra("id", idUser);
                startActivity(intent);
                Toast.makeText(DescriptionActivity.this, "Your application has been registered successfully!", Toast.LENGTH_SHORT).show();
            }
        });

        Button saveOffer = findViewById(R.id.save_Offer);
        saveOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference savedOffersRef = FirebaseDatabase.getInstance("https://interimapp-e48dc-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("savedOffers");
                String id = savedOffersRef.push().getKey();
                SavedOffer savedOffer = new SavedOffer(photo, titre, nomCompagnie, salaire, lieu, duree, description, idUser, idOffer, id);
                savedOffersRef.child(id).setValue(savedOffer);
                Toast.makeText(DescriptionActivity.this, "Offer saved successfully!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        LatLng location = getLocationFromAddress(lieu); // Convert the location to LatLng
        if (location != null) {
            gMap.addMarker(new MarkerOptions().position(location).title(lieu));
            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10));
        } else {
            Toast.makeText(this, "Location not found: " + lieu, Toast.LENGTH_SHORT).show();
        }
    }

    private LatLng getLocationFromAddress(String strAddress) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addressList;
        try {
            addressList = geocoder.getFromLocationName(strAddress, 1);
            if (addressList != null && !addressList.isEmpty()) {
                Address address = addressList.get(0);
                return new LatLng(address.getLatitude(), address.getLongitude());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
