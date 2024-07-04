package com.example.interimapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText firstNameEditText, lastNameEditText, emailEditText, addressEditText, passwordEditText, phoneEditText, birthdateEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createaccount);

        firstNameEditText = findViewById(R.id.name);
        lastNameEditText = findViewById(R.id.lastname);
        emailEditText = findViewById(R.id.mail);
        addressEditText = findViewById(R.id.adress);
        passwordEditText = findViewById(R.id.password);
        phoneEditText = findViewById(R.id.phone);
        birthdateEditText = findViewById(R.id.birthdate);

        Button createAccButton = findViewById(R.id.createAcc);
        createAccButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = firstNameEditText.getText().toString().trim();
                String lastName = lastNameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String address = addressEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String phone = phoneEditText.getText().toString().trim();
                String birthdate = birthdateEditText.getText().toString().trim();

                if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || address.isEmpty() || password.isEmpty() || phone.isEmpty() || birthdate.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                saveUserData(firstName, lastName, email, address, password, phone, birthdate);

                // Après l'enregistrement réussi, ouvrez EmailConfirmationActivity
                Intent intent = new Intent(RegisterActivity.this, EmailConfirmationActivity.class);
                intent.putExtra("firstName", firstName);
                startActivity(intent);
                finish(); // Facultatif : fermer l'activité actuelle pour empêcher l'utilisateur de revenir à cette page avec le bouton Retour
            }
        });
    }

    private void saveUserData(String firstName, String lastName, String email, String address, String password, String phone, String birthdate) {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://interimapp-e48dc-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference usersRef = database.getReference("users");

        String userId = usersRef.push().getKey();
        User user = new User(firstName, lastName, email, address, password, phone, birthdate, userId, R.drawable.teteprofile);

        usersRef.child(userId).setValue(user);

        Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();
    }
}
