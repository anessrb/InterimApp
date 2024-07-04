package com.example.interimapplication;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

public class ProfileActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://interimapp-e48dc-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference users = database.getReference("users");
    String name, mail, number, id;
    int pic;
    Uri uri;
    private ImageView imageView;
    private TextView emailTextView, nameTextView, numberTextView;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PICK_DOCUMENT_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        imageView = findViewById(R.id.imageView17);
        emailTextView = findViewById(R.id.email);
        nameTextView = findViewById(R.id.textView13);
        numberTextView = findViewById(R.id.numberuser);

        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getStringExtra("id");
        }

        // Set up profile button
        ImageButton homeButton = findViewById(R.id.homeIcon);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        // Set up profile button
        ImageButton profileButton = findViewById(R.id.profileIcon);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, ProfileActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        // Set up message button
        ImageButton message = findViewById(R.id.messageIcon);
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, MainApplcations.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        // Set up save offers button
        ImageButton saveIcon = findViewById(R.id.saveIcon);
        saveIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, SavedOfferActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        users.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name = snapshot.child("firstName").getValue(String.class);
                pic = snapshot.child("picture").getValue(int.class);
                mail = snapshot.child("email").getValue(String.class);
                number = snapshot.child("phone").getValue(String.class);

                emailTextView.setText(mail);
                nameTextView.setText(name);
                numberTextView.setText(number);

                /*if(snapshot.child("picuri").exists()){

                    String s = snapshot.child("picuri").getValue(String.class);
                    Uri uri = Uri.parse(s);

                    imageView.setImageURI(uri);

                } else {*/
                    imageView.setImageResource(pic);
                //}

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ImageView modifyFotoImageView = findViewById(R.id.modifypicture);
        ImageView plusButton = findViewById(R.id.imageView169);
        Button logoutButton = findViewById(R.id.Logout);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        modifyFotoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openDocumentChooser();
            }
        });
    }

    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void openDocumentChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(Intent.createChooser(intent, "Select Document"), PICK_DOCUMENT_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            if (requestCode == PICK_IMAGE_REQUEST) {
                uri = data.getData();
                String PictureName = getFileName(uri);
                Toast.makeText(this, "Picture selected: " + PictureName, Toast.LENGTH_SHORT).show();
                users.child(id).child("picuri").setValue(uri.toString());

                imageView.setImageURI(uri);

            } else if (requestCode == PICK_DOCUMENT_REQUEST) {
                uri = data.getData();
                String documentName = getFileName(uri);
                Toast.makeText(this, "Document selected: " + documentName, Toast.LENGTH_SHORT).show();
                users.child(id).child("cv").setValue(uri.toString());
            }
        }
    }


    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }
}
