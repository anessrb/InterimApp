package com.example.interimapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SavedOffersAdapter extends RecyclerView.Adapter<SavedOffersAdapter.ViewHolder> {

    private Context context;
    private List<SavedOffer> savedOffersList;

    public SavedOffersAdapter(Context context, List<SavedOffer> savedOffersList) {
        this.context = context;
        this.savedOffersList = savedOffersList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_saved_offer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SavedOffer offer = savedOffersList.get(position);
        holder.titreTextView.setText(offer.getTitre());
        holder.nomCompagnieTextView.setText(offer.getNomCompagnie());
        holder.lieuTextView.setText(offer.getLieu());
        holder.salaireTextView.setText(offer.getSalaire());
        holder.dureeTextView.setText(offer.getDuree());
        // Add logic to load the image and other details as needed
    }

    @Override
    public int getItemCount() {
        return savedOffersList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView titreTextView;
        TextView nomCompagnieTextView;
        TextView lieuTextView;
        TextView salaireTextView;
        TextView dureeTextView;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titreTextView = itemView.findViewById(R.id.titre_tv);
            nomCompagnieTextView = itemView.findViewById(R.id.nomCompagnie_tv);
            lieuTextView = itemView.findViewById(R.id.lieu_tv);
            salaireTextView = itemView.findViewById(R.id.salaire_tv);
            dureeTextView = itemView.findViewById(R.id.duree_tv);
            imageView = itemView.findViewById(R.id.image_iv);
        }
    }
}
