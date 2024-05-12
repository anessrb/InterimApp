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

public class RecyclerViewHomeAdapter extends RecyclerView.Adapter<RecyclerViewHomeAdapter.OffreViewHolder> {

    ArrayList<ManageOffers> offres = new ArrayList<>();
    RecycleViewOnItemClick recycleViewOnItemClick;

    public RecyclerViewHomeAdapter(ArrayList<ManageOffers> offres, RecycleViewOnItemClick recycleViewOnItemClick) {
        this.offres = offres;
        this.recycleViewOnItemClick = recycleViewOnItemClick;
    }

    @NonNull
    @Override
    public OffreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.offre_custom_item, null, false);
        OffreViewHolder vh = new OffreViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull OffreViewHolder holder, int position) {
        ManageOffers offre = offres.get(position);
        holder.image_iv.setImageResource(offre.getPhoto());
        holder.titre_tv.setText(offre.getTitre());
        holder.nomCompagnie_tv.setText(offre.getNomCompagnie());
        holder.lieu_tv.setText(offre.getLieu());
        holder.salaire_tv.setText(offre.getSalaire());
        holder.temps_tv.setText(offre.getTemps());
        holder.duree_tv.setText(offre.getDuree());

    }

    @Override
    public int getItemCount() {
        return offres.size();
    }

    class OffreViewHolder extends RecyclerView.ViewHolder{
        ImageView image_iv;
        TextView titre_tv;
        TextView nomCompagnie_tv;
        TextView lieu_tv;
        TextView salaire_tv;
        TextView temps_tv;
        TextView duree_tv;
        public OffreViewHolder(@NonNull View itemView) {
            super(itemView);
            image_iv = itemView.findViewById(R.id.image_iv);
            titre_tv = itemView.findViewById(R.id.titre_tv);
            nomCompagnie_tv = itemView.findViewById(R.id.nomCompagnie_tv);
            lieu_tv = itemView.findViewById(R.id.lieu_tv);
            salaire_tv = itemView.findViewById(R.id.salaire_tv);
            temps_tv = itemView.findViewById(R.id.temps_tv);
            duree_tv = itemView.findViewById(R.id.duree_tv);
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
