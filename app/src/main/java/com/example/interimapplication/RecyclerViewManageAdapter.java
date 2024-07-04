package com.example.interimapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RecyclerViewManageAdapter extends RecyclerView.Adapter<RecyclerViewManageAdapter.ManageViewHolder> {
    private DatabaseReference usersRef = FirebaseDatabase.getInstance("https://interimapp-e48dc-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("users");
    private DatabaseReference companyRef =FirebaseDatabase.getInstance("https://interimapp-e48dc-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("companies");
    private DatabaseReference appRef =FirebaseDatabase.getInstance("https://interimapp-e48dc-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("applications");
    ArrayList<Application> applications = new ArrayList<>();
    RecycleViewOnItemClick recycleViewOnItemClick;
    private Context context;

    public RecyclerViewManageAdapter(ArrayList<Application> applications, RecycleViewOnItemClick recycleViewOnItemClick, Context context) {
        this.applications = applications;
        this.recycleViewOnItemClick = recycleViewOnItemClick;
        this.context = context;
    }

    @NonNull
    @Override
    public ManageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_offer_custom_item, null, false);
        return new ManageViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull ManageViewHolder holder, int position) {

        Application application = applications.get(position);
        holder.titre_tv.setText(application.getTitre());
        holder.nomCompagnie_tv.setText(application.getNomCompagnie());
        usersRef.child(application.getUserId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User u = snapshot.getValue(User.class);
                holder.image_iv.setImageResource(u.getPicture());
                holder.condidate_name_offers_value_tv.setText(u.getFirstName()+" "+u.getLastName());
                holder.number_offers_value_tv.setText(u.getPhone());
                holder.sex_offers_value_tv.setText("Male");
                holder.last_degree_offers_value_tv.setText("Master");
                holder.adress_offers_value_tv.setText(u.getAddress());
                holder.age_offers_value_tv.setText("29 years");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.accept.setOnClickListener(v -> {
            appRef.child(applications.get(position).getId()).child("statut").setValue("Accepted");
        appRef.child(applications.get(position).getId()).child("seen").setValue("true");
        });

        holder.decline.setOnClickListener(v -> {
            appRef.child(applications.get(position).getId()).child("statut").setValue("Declined");
            appRef.child(applications.get(position).getId()).child("seen").setValue("true");
        });

        holder.contact.setOnClickListener(v -> {
            Intent intent = new Intent(context, ContactActivity.class);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return applications.size();
    }

    class ManageViewHolder extends RecyclerView.ViewHolder {
        ImageView image_iv;
        TextView titre_tv;
        TextView nomCompagnie_tv;
        TextView condidate_name_offers_value_tv;
        TextView number_offers_value_tv;
        TextView sex_offers_value_tv;
        TextView last_degree_offers_value_tv;
        TextView adress_offers_value_tv;
        TextView age_offers_value_tv;
        Button accept;
        Button decline;
        Button contact;

        public ManageViewHolder(@NonNull View itemView) {
            super(itemView);
            image_iv = itemView.findViewById(R.id.image_offers_iv);
            titre_tv = itemView.findViewById(R.id.titre_offers_tv);
            nomCompagnie_tv = itemView.findViewById(R.id.nomCompagnie_offers_tv);
            condidate_name_offers_value_tv = itemView.findViewById(R.id.condidate_name_offers_value_tv);
            number_offers_value_tv = itemView.findViewById(R.id.number_offers_value_tv);
            sex_offers_value_tv = itemView.findViewById(R.id.sex_offers_value_tv);
            last_degree_offers_value_tv = itemView.findViewById(R.id.last_degree_offers_value_tv);
            adress_offers_value_tv = itemView.findViewById(R.id.adress_offers_value_tv);
            age_offers_value_tv = itemView.findViewById(R.id.age_offers_value_tv);
            accept = itemView.findViewById(R.id.accept_offers);
            decline = itemView.findViewById(R.id.decline_offers);
            contact = itemView.findViewById(R.id.contact_offers);
            itemView.setOnClickListener(v -> recycleViewOnItemClick.onItemClick(getAdapterPosition()));
            itemView.setOnLongClickListener(v -> {
                recycleViewOnItemClick.onLongItemClick(getAdapterPosition());
                return true;
            });
        }
    }
}
