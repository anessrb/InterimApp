package com.example.interimapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

public class profileCompany extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://interimapp-e48dc-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference companies = database.getReference("companies");

    String name, mail, number, adress, id;
    int pic;
    private ImageView imageView;
    private TextView emailTextView, nameCompanyTextView, adressTextView, numberTextView;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilecompany);

        imageView = findViewById(R.id.imageView17);
        emailTextView = findViewById(R.id.emailcompany);
        nameCompanyTextView = findViewById(R.id.textView13);
        adressTextView = findViewById(R.id.adressCompany);
        numberTextView = findViewById(R.id.numberCompany);

        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getStringExtra("id");
        }
        companies.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name = snapshot.child("companyName").getValue(String.class);
                pic = snapshot.child("picture").getValue(int.class);
                mail = snapshot.child("contactMail").getValue(String.class);
                number = snapshot.child("contactNumber").getValue(String.class);
                adress = snapshot.child("companyAddress").getValue(String.class);

                emailTextView.setText(mail);
                nameCompanyTextView.setText(name);
                adressTextView.setText(adress);
                numberTextView.setText(number);
                imageView.setImageResource(pic);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Button logoutButton = findViewById(R.id.Logout);
        ImageView modifyFotoImageView = findViewById(R.id.modifypicture);
        ImageButton home = findViewById(R.id.homeIcon);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profileCompany.this, ManageOffersActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profileCompany.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // Gestion du clic sur l'icône "modifyfoto"
        modifyFotoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
