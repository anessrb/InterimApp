package com.example.interimapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class beforeintro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beforeintro);

        Button anonymousButton = findViewById(R.id.Anonymous);
        Button loginButton = findViewById(R.id.Loginpage);

        anonymousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent pour démarrer MainActivity
                Intent intent = new Intent(beforeintro.this, MainActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent pour démarrer LoginActivity
                Intent intent = new Intent(beforeintro.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
