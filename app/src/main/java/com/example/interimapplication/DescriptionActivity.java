package com.example.interimapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DescriptionActivity extends AppCompatActivity {
    private DatabaseReference usersRef = FirebaseDatabase.getInstance("https://interimapp-e48dc-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("users");
    private DatabaseReference companyRef =FirebaseDatabase.getInstance("https://interimapp-e48dc-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("companies");
    private DatabaseReference appRef =FirebaseDatabase.getInstance("https://interimapp-e48dc-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("applications");

    String titre, salaire, lieu, duree, description, idUser, nomCompagnie, idCompany, idOffer;
    String statut = "Pending";
    int photo;
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

        companyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    Company c = ds.getValue(Company.class);
                    if(c.getCompanyName().equals(nomCompagnie)){
                        idCompany = c.getCompanyId();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
                Toast.makeText(DescriptionActivity.this, "Your application has been registred successfully!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
