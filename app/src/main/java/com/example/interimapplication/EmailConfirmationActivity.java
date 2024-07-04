package com.example.interimapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EmailConfirmationActivity extends AppCompatActivity {

    private TextView hidearTextView;
    private String firstName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmationmail);

        hidearTextView = findViewById(R.id.hidear);

        // Get the first name from the intent
        Intent intent = getIntent();
        if (intent != null) {
            firstName = intent.getStringExtra("firstName");
            hidearTextView.setText("Hi " + firstName + "!");
        }

        Button validateButton = findViewById(R.id.validate);
        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Simulate email validation and redirect to the login screen
                Intent intent = new Intent(EmailConfirmationActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
