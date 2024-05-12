package com.example.interimapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterCompany extends AppCompatActivity {

    private TextInputEditText passwordEditText, companyNameEditText, companyAddressEditText, nationalNumEditText, contactNameEditText, contactMailEditText, contactNumberEditText, websiteEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createaccount2);

        // Récupérer les TextInputEditTexts
        companyNameEditText = findViewById(R.id.companyname);
        companyAddressEditText = findViewById(R.id.companyadress);
        nationalNumEditText = findViewById(R.id.nationalnum);
        contactNameEditText = findViewById(R.id.contactname);
        contactMailEditText = findViewById(R.id.contactmail);
        contactNumberEditText = findViewById(R.id.contactnumber);
        websiteEditText = findViewById(R.id.website);
        passwordEditText = findViewById(R.id.password);

        // Récupérer la référence de la base de données Firebase
        DatabaseReference companyRef = FirebaseDatabase.getInstance("https://interimapp-e48dc-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("companies");

        // Récupérer le bouton de création de compte
        Button createAccButton = findViewById(R.id.sendOffer);
        createAccButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupérer les valeurs des champs
                String companyName = companyNameEditText.getText().toString().trim();
                String companyAddress = companyAddressEditText.getText().toString().trim();
                String nationalNum = nationalNumEditText.getText().toString().trim();
                String contactName = contactNameEditText.getText().toString().trim();
                String contactMail = contactMailEditText.getText().toString().trim();
                String contactNumber = contactNumberEditText.getText().toString().trim();
                String website = websiteEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Vérifier que tous les champs sont remplis
                if (companyName.isEmpty() || companyAddress.isEmpty() || nationalNum.isEmpty() || contactName.isEmpty() || contactMail.isEmpty() || contactNumber.isEmpty() || website.isEmpty() || password.isEmpty()) {
                    // Afficher un message d'erreur si des champs sont vides
                    // Vous pouvez également ajouter une logique de validation supplémentaire ici
                    Toast.makeText(RegisterCompany.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Créer un nouvel objet Company
                Company company = new Company();
                company.setCompanyName(companyName);
                company.setCompanyAddress(companyAddress);
                company.setNationalNum(nationalNum);
                company.setContactName(contactName);
                company.setContactMail(contactMail);
                company.setContactNumber(contactNumber);
                company.setWebsite(website);
                company.setPassword(password);
                /////////////////////////////////////
                company.setPicture(R.drawable.apple);

                // Enregistrer les données dans Firebase Realtime Database
                String id = companyRef.push().getKey();
                company.setCompanyId(id);
                companyRef.child(id).setValue(company);

                // Afficher un message de succès
                Toast.makeText(RegisterCompany.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                // Rediriger l'utilisateur vers la page de login (LoginActivity)
                Intent intent = new Intent(RegisterCompany.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
