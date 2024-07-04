package com.example.interimapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference usersRef =FirebaseDatabase.getInstance("https://interimapp-e48dc-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("users");
    private DatabaseReference companyRef =FirebaseDatabase.getInstance("https://interimapp-e48dc-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("companies");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // Initialisation de FirebaseAuth et DatabaseReference

        // Récupération du bouton "Login" et ajout d'un écouteur de clic
        Button loginButton = findViewById(R.id.Login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        // Récupération du TextView pour "Create an account" et ajout d'un écouteur de clic
        TextView createAccountTextView = findViewById(R.id.createAccount);
        createAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent pour ouvrir RegisterActivity
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        // Récupération du TextView pour "Need a pro account" et ajout d'un écouteur de clic
        TextView needProAccountTextView = findViewById(R.id.needproaccount);
        needProAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent pour ouvrir RegisterCompany
                Intent intent = new Intent(LoginActivity.this, RegisterCompany.class);
                startActivity(intent);
            }
        });
    }

    private void loginUser() {
        // Retrieve user credentials from text input fields
        String email = ((TextInputEditText) findViewById(R.id.mail)).getText().toString();
        String password = ((TextInputEditText) findViewById(R.id.password)).getText().toString();

        // Check if the user exists in the "users" node
        usersRef.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot userDataSnapshot) {
                // Check if the user exists in the "companies" node
                companyRef.orderByChild("contactMail").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot companyDataSnapshot) {
                        if (userDataSnapshot.exists()) {
                            // User found in the "users" node, verify the password
                            for (DataSnapshot userSnapshot : userDataSnapshot.getChildren()) {
                                User user = userSnapshot.getValue(User.class);
                                if (user != null && user.getPassword().equals(password)) {
                                    // Password correct, login as a user
                                    Toast.makeText(LoginActivity.this, "User login successful!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.putExtra("id", user.getUserid());
                                    intent.putExtra("type", "user");
                                    startActivity(intent);
                                    //finish();
                                    return;
                                }
                            }
                        } else if (companyDataSnapshot.exists()) {
                            // User not found in the "users" node but found in the "companies" node, verify the password
                            for (DataSnapshot companySnapshot : companyDataSnapshot.getChildren()) {
                                Company company = companySnapshot.getValue(Company.class);
                                if (company != null && company.getPassword().equals(password)) {
                                    // Password correct, login as a company
                                    Toast.makeText(LoginActivity.this, "Company login successful!", Toast.LENGTH_SHORT).show();
                                    // Intent for opening the company's MainActivity
                                    Intent intent = new Intent(LoginActivity.this, ManageOffersActivity.class);
                                    intent.putExtra("id", company.getCompanyId());
                                    intent.putExtra("type", "company");
                                    startActivity(intent);
                                    finish();
                                    return;
                                }
                            }
                        }

                        // No user or company found or incorrect password
                        Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Error handling for database operation
                        Toast.makeText(LoginActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Error handling for database operation
                Toast.makeText(LoginActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }}
