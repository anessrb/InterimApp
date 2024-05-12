package com.example.interimapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddOfferActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://interimapp-e48dc-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference companies = database.getReference("companies");
    private EditText jobEditText, periodEditText, salaryEditText, cityEditText, contractEditText, descriptionEditText;
    private String id, companyName;
    private int companyPicture;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_offre);

        Intent intent = this.getIntent();
        id = intent.getStringExtra("id");

        jobEditText = findViewById(R.id.job);
        periodEditText = findViewById(R.id.period);
        salaryEditText = findViewById(R.id.salary);
        cityEditText = findViewById(R.id.city);
        contractEditText = findViewById(R.id.contracttype);
        descriptionEditText = findViewById(R.id.description);

        Button sendOffer = findViewById(R.id.sendOffer);
        sendOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String job = jobEditText.getText().toString().trim();
                String period = periodEditText.getText().toString().trim();
                String salary = salaryEditText.getText().toString().trim();
                String city = cityEditText.getText().toString().trim();
                String contracttype = contractEditText.getText().toString().trim();
                String description = descriptionEditText.getText().toString().trim();

                if (job.isEmpty() || period.isEmpty() || salary.isEmpty() || city.isEmpty() || contracttype.isEmpty() || description.isEmpty()) {
                    Toast.makeText(AddOfferActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                saveUserData(job, period, salary, city, contracttype, description);

                Intent intent = new Intent(AddOfferActivity.this, ManageOffersActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);

                finish();

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        companies.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                companyName = snapshot.child("companyName").getValue(String.class);
                companyPicture = snapshot.child("picture").getValue(int.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void saveUserData(String job, String period, String salary, String city, String contracttype, String description) {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://interimapp-e48dc-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference offersRef = database.getReference("offers");

        String offerId = offersRef.push().getKey();
        ManageOffers o = new ManageOffers(companyPicture, job, companyName, city, salary, contracttype, period, description, offerId);

        offersRef.child(offerId).setValue(o);

        Toast.makeText(this, "The offer is added Successfully", Toast.LENGTH_SHORT).show();
    }

}