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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RecyclerViewOfferAdapter extends RecyclerView.Adapter<RecyclerViewOfferAdapter.OfferViewHolder> {
    private ArrayList<ManageOffers> manageOffers;
    private RecycleViewOnItemClick recycleViewOnItemClick;
    private Context context;
    private String companyId;

    public RecyclerViewOfferAdapter(ArrayList<ManageOffers> manageOffers, RecycleViewOnItemClick recycleViewOnItemClick, Context context, String companyId) {
        this.manageOffers = manageOffers;
        this.recycleViewOnItemClick = recycleViewOnItemClick;
        this.context = context;
        this.companyId = companyId;
    }

    @NonNull
    @Override
    public OfferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_custom_item, null, false);
        return new OfferViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OfferViewHolder holder, int position) {
        ManageOffers manage = manageOffers.get(position);
        holder.image_iv.setImageResource(manage.getPhoto());
        holder.titre_tv.setText(manage.getTitre());
        holder.nomCompagnie_tv.setText(manage.getNomCompagnie());
        holder.lieu_tv.setText(manage.getLieu());
        holder.salaire_tv.setText(manage.getSalaire());
        holder.temps_tv.setText(manage.getTemps());
        holder.duree_tv.setText(manage.getDuree());

        holder.modifyButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, AddOfferActivity.class);
            intent.putExtra("offerId", manage.getId());
            intent.putExtra("id", companyId); // Pass the company ID as well
            context.startActivity(intent);
        });

        holder.deleteButton.setOnClickListener(v -> {
            FirebaseDatabase database = FirebaseDatabase.getInstance("https://interimapp-e48dc-default-rtdb.europe-west1.firebasedatabase.app/");
            DatabaseReference offersRef = database.getReference("offers");
            offersRef.child(manage.getId()).removeValue().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "Offer deleted", Toast.LENGTH_SHORT).show();
                    manageOffers.remove(position);
                    notifyItemRemoved(position);
                } else {
                    Toast.makeText(context, "Failed to delete offer", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return manageOffers.size();
    }

    class OfferViewHolder extends RecyclerView.ViewHolder {
        ImageView image_iv;
        TextView titre_tv;
        TextView nomCompagnie_tv;
        TextView lieu_tv;
        TextView salaire_tv;
        TextView temps_tv;
        TextView duree_tv;
        Button modifyButton;
        Button deleteButton;

        public OfferViewHolder(@NonNull View itemView) {
            super(itemView);
            image_iv = itemView.findViewById(R.id.image_manage_iv);
            titre_tv = itemView.findViewById(R.id.titre_manage_tv);
            nomCompagnie_tv = itemView.findViewById(R.id.nomCompagnie_manage_tv);
            lieu_tv = itemView.findViewById(R.id.lieu_manage_tv);
            salaire_tv = itemView.findViewById(R.id.salaire_manage_tv);
            temps_tv = itemView.findViewById(R.id.temps_manage_tv);
            duree_tv = itemView.findViewById(R.id.duree_manage_tv);
            modifyButton = itemView.findViewById(R.id.modify_manage_offers);
            deleteButton = itemView.findViewById(R.id.delete_manage_offers);

            itemView.setOnClickListener(v -> recycleViewOnItemClick.onItemClick(getAdapterPosition()));
            itemView.setOnLongClickListener(v -> {
                recycleViewOnItemClick.onLongItemClick(getAdapterPosition());
                return true;
            });
        }
    }
}
