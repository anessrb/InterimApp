package com.example.interimapplication;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements RecycleViewOnItemClick {
    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;
    ArrayList<ManageOffers> bd = new ArrayList<>();
    ArrayList<ManageOffers> filteredList = new ArrayList<>();
    RecyclerView rv;
    RecyclerViewHomeAdapter adapter;
    String id;

    FirebaseDatabase database = FirebaseDatabase.getInstance("https://interimapp-e48dc-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference offersRef = database.getReference("offers");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getStringExtra("id");
        }



        rv = findViewById(R.id.rv_main);
        adapter = new RecyclerViewHomeAdapter(filteredList, this);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        rv.setHasFixedSize(true);
        rv.setAdapter(adapter);
        rv.setLayoutManager(lm);
        DecoratorRecycleView itemDecorator = new DecoratorRecycleView(20);
        rv.addItemDecoration(itemDecorator);

        // Set up profile button
        ImageButton homeButton = findViewById(R.id.homeIcon);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        // Set up profile button
        ImageButton profileButton = findViewById(R.id.profileIcon);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        // Set up message button
        ImageButton message = findViewById(R.id.messageIcon);
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainApplcations.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        // Set up save offers button
        ImageButton saveIcon = findViewById(R.id.saveIcon);
        saveIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SavedOfferActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        // Set up search functionality
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterOffers(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterOffers(newText);
                return false;
            }
        });

        // Set up voice search button
        ImageButton voiceSearchButton = findViewById(R.id.voiceSearchButton);
        voiceSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });

        // Set up filter button
        ImageButton filterButton = findViewById(R.id.filterButton);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFilterDialog();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        offersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bd.clear(); // Clear the list before updating
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ManageOffers o = ds.getValue(ManageOffers.class);
                    bd.add(o);
                }
                filterOffers(""); // Apply empty filter to show all offers initially
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }

    private void filterOffers(String query) {
        filteredList.clear();
        if (query.isEmpty()) {
            filteredList.addAll(bd);
        } else {
            for (ManageOffers offer : bd) {
                if (offer.getTitre().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(offer);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void filterOffersByCriteria(String city, String duration, String salary) {
        filteredList.clear();
        for (ManageOffers offer : bd) {
            boolean matches = true;
            if (!city.isEmpty() && !offer.getLieu().toLowerCase().contains(city.toLowerCase())) {
                matches = false;
            }
            if (!duration.isEmpty() && !offer.getDuree().toLowerCase().contains(duration.toLowerCase())) {
                matches = false;
            }
            if (!salary.isEmpty() && !offer.getSalaire().toLowerCase().contains(salary.toLowerCase())) {
                matches = false;
            }
            if (matches) {
                filteredList.add(offer);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(MainActivity.this, DescriptionActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("Photo", filteredList.get(position).getPhoto());
        intent.putExtra("Titre", filteredList.get(position).getTitre());
        intent.putExtra("nomCompagnie", filteredList.get(position).getNomCompagnie());
        intent.putExtra("Salaire", filteredList.get(position).getSalaire());
        intent.putExtra("Lieu", filteredList.get(position).getLieu());
        intent.putExtra("Duree", filteredList.get(position).getDuree());
        intent.putExtra("Temps", filteredList.get(position).getTemps());
        intent.putExtra("Description", filteredList.get(position).getDescription());
        intent.putExtra("offerId", filteredList.get(position).getId());
        startActivity(intent);
    }

    @Override
    public void onLongItemClick(int position) {
        filteredList.remove(position);
    }

    // Prompt speech input
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak the job title");

        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            // Handle error
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                if (result != null && !result.isEmpty()) {
                    String spokenText = result.get(0);
                    SearchView searchView = findViewById(R.id.searchView);
                    searchView.setQuery(spokenText, true);
                }
            }
        }
    }

    // Show filter dialog
    private void showFilterDialog() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View filterView = LayoutInflater.from(this).inflate(R.layout.filter_dialog, null);

        EditText editTextCity = filterView.findViewById(R.id.editTextCity);
        EditText editTextDuration = filterView.findViewById(R.id.editTextDuration);
        EditText editTextSalary = filterView.findViewById(R.id.editTextSalary);
        Button buttonApplyFilters = filterView.findViewById(R.id.buttonApplyFilters);

        buttonApplyFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = editTextCity.getText().toString().trim();
                String duration = editTextDuration.getText().toString().trim();
                String salary = editTextSalary.getText().toString().trim();
                filterOffersByCriteria(city, duration, salary);
                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.setContentView(filterView);
        bottomSheetDialog.show();
    }
}
