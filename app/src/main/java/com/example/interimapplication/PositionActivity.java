package com.example.interimapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.interimapplication.beforeintro;

public class PositionActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.position);

        Button allowButton = findViewById(R.id.allow);
        Button skipButton = findViewById(R.id.skip);

        allowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Vérifier la permission de localisation
                if (ContextCompat.checkSelfPermission(PositionActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    // Si la permission est déjà accordée, vous pouvez effectuer votre action ici
                    // Par exemple, démarrer une nouvelle activité ou effectuer une autre action.
                    openBeforeIntroActivity();
                } else {
                    // Si la permission n'est pas accordée, demandez-la
                    ActivityCompat.requestPermissions(PositionActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            LOCATION_PERMISSION_REQUEST_CODE);
                }
            }
        });

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Si l'utilisateur choisit de ne pas autoriser la localisation pour le moment,
                // vous pouvez implémenter une action appropriée ici.
                openBeforeIntroActivity();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            // Vérifiez si la permission a été accordée
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // La permission de localisation a été accordée
                // Vous pouvez effectuer votre action ici, par exemple démarrer une nouvelle activité.
                openBeforeIntroActivity();
            } else {
                // La permission de localisation n'a pas été accordée
                // Vous pouvez afficher un message à l'utilisateur ou effectuer une autre action appropriée.
            }
        }
    }

    private void openBeforeIntroActivity() {
        Intent intent = new Intent(PositionActivity.this, beforeintro.class);
        startActivity(intent);
        // Terminez cette activité si vous ne voulez pas revenir à celle-ci en appuyant sur le bouton Retour
        finish();
    }
}
