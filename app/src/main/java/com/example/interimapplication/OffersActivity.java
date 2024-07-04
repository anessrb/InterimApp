package com.example.interimapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OffersActivity extends AppCompatActivity implements RecycleViewOnItemClick {
    private DatabaseReference usersRef = FirebaseDatabase.getInstance("https://interimapp-e48dc-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("users");
    private DatabaseReference companyRef = FirebaseDatabase.getInstance("https://interimapp-e48dc-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("companies");
    private DatabaseReference appRef = FirebaseDatabase.getInstance("https://interimapp-e48dc-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("applications");
    String id;
    RecyclerViewManageAdapter adapter;
    ArrayList<Application> bd = new ArrayList<>();
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offers);

        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getStringExtra("id");
        }

        ImageButton home = findViewById(R.id.homeIcon);
        home.setOnClickListener(v -> {
            Intent homeIntent = new Intent(OffersActivity.this, ManageOffersActivity.class);
            homeIntent.putExtra("id", id);
            startActivity(homeIntent);
        });

        ImageButton plus = findViewById(R.id.plusIcon);
        plus.setOnClickListener(v -> {
            Intent addOfferIntent = new Intent(OffersActivity.this, AddOfferActivity.class);
            addOfferIntent.putExtra("id", id);
            startActivity(addOfferIntent);
        });

        ImageButton profileButton = findViewById(R.id.profileIcon);
        profileButton.setOnClickListener(v -> {
            Intent profileIntent = new Intent(OffersActivity.this, profileCompany.class);
            profileIntent.putExtra("id", id);
            startActivity(profileIntent);
        });

        ImageButton message = findViewById(R.id.messageIcon);
        message.setOnClickListener(v -> {
            Intent offersIntent = new Intent(OffersActivity.this, OffersActivity.class);
            offersIntent.putExtra("id", id);
            startActivity(offersIntent);
        });

        rv = findViewById(R.id.rv_offer);
        adapter = new RecyclerViewManageAdapter(bd, this, this);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        rv.setHasFixedSize(true);
        rv.setAdapter(adapter);
        rv.setLayoutManager(lm);
        DecoratorRecycleView itemDecorator = new DecoratorRecycleView(20);
        rv.addItemDecoration(itemDecorator);

        appRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bd.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Application a = ds.getValue(Application.class);
                    if (a.getCompanyId().equals(id) && a.isSeen().equals("false")) {
                        bd.add(a);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        ImageButton filterButton = findViewById(R.id.filterButton);
        filterButton.setOnClickListener(v -> showFilterDialog());
    }

    private void showFilterDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.filter_dialog_company, null);
        builder.setView(dialogView);

        EditText editTextSex = dialogView.findViewById(R.id.editTextSex);
        EditText editTextLastDegree = dialogView.findViewById(R.id.editTextLastDegree);
        EditText editTextAge = dialogView.findViewById(R.id.editTextAge);
        Button buttonApplyFilters = dialogView.findViewById(R.id.buttonApplyFilters);

        AlertDialog dialog = builder.create();

        buttonApplyFilters.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();
    }

    @Override
    public void onItemClick(int position) {

        String id = bd.get(position).getUserId();
        usersRef.child(id).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    User u = snapshot.getValue(User.class);
                    String cv = u.getCv();
                    Uri uri = Uri.parse(cv);

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(uri, "application/pdf");
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(intent);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onLongItemClick(int position) {
        bd.remove(position);
    }
}
