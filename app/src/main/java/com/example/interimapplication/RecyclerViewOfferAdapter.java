package com.example.interimapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RecyclerViewOfferAdapter extends RecyclerView.Adapter<RecyclerViewOfferAdapter.OfferViewHolder> {
    ArrayList<ManageOffers> manageOffers = new ArrayList<>();
    RecycleViewOnItemClick recycleViewOnItemClick;
    public RecyclerViewOfferAdapter(ArrayList<ManageOffers> manageOffers, RecycleViewOnItemClick recycleViewOnItemClick) {
        this.manageOffers = manageOffers;
        this.recycleViewOnItemClick = recycleViewOnItemClick;
    }

    @NonNull
    @Override
    public OfferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_custom_item, null, false);
        RecyclerViewOfferAdapter.OfferViewHolder vh = new RecyclerViewOfferAdapter.OfferViewHolder(v);
        return vh;
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
    }

    @Override
    public int getItemCount() {
        return manageOffers.size();
    }

    class OfferViewHolder extends RecyclerView.ViewHolder{
        ImageView image_iv;
        TextView titre_tv;
        TextView nomCompagnie_tv;
        TextView lieu_tv;
        TextView salaire_tv;
        TextView temps_tv;
        TextView duree_tv;
        public OfferViewHolder(@NonNull View itemView) {
            super(itemView);
            image_iv = itemView.findViewById(R.id.image_manage_iv);
            titre_tv = itemView.findViewById(R.id.titre_manage_tv);
            nomCompagnie_tv = itemView.findViewById(R.id.nomCompagnie_manage_tv);
            lieu_tv = itemView.findViewById(R.id.lieu_manage_tv);
            salaire_tv = itemView.findViewById(R.id.salaire_manage_tv);
            temps_tv = itemView.findViewById(R.id.temps_manage_tv);
            duree_tv = itemView.findViewById(R.id.duree_manage_tv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recycleViewOnItemClick.onItemClick(getAdapterPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    recycleViewOnItemClick.onLongItemClick(getAdapterPosition());
                    return true;
                }
            });
        }
    }
}
