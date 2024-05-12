package com.example.interimapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAppAdapter extends RecyclerView.Adapter<RecyclerViewAppAdapter.AppViewHolder> {

    ArrayList<Application> applications = new ArrayList<>();
    RecycleViewOnItemClick recycleViewOnItemClick;

    public RecyclerViewAppAdapter(ArrayList<Application> applications, RecycleViewOnItemClick recycleViewOnItemClick) {
        this.applications = applications;
        this.recycleViewOnItemClick = recycleViewOnItemClick;
    }

    @NonNull
    @Override
    public AppViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.application_custom_item, null, false);
        RecyclerViewAppAdapter.AppViewHolder vh = new RecyclerViewAppAdapter.AppViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewHolder holder, int position) {
        Application app = applications.get(position);
        holder.image_iv.setImageResource(app.getPhoto());
        holder.titre_tv.setText(app.getTitre());
        holder.nomCompagnie_tv.setText(app.getNomCompagnie());
        holder.statut_tv.setText(app.getStatut());
        holder.date_tv.setText(app.getDate());
        int stt = 0;
        if(app.getStatut().equals("Pending")) stt = R.drawable.icon3;
        if(app.getStatut().equals("Accepted")) stt = R.drawable.icon1;
        if(app.getStatut().equals("Declined")) stt = R.drawable.icon2;
        holder.statut_iv.setImageResource(stt);
    }

    @Override
    public int getItemCount() {
        return applications.size();
    }


    class AppViewHolder extends RecyclerView.ViewHolder{
        ImageView image_iv;
        ImageView statut_iv;
        TextView titre_tv;
        TextView nomCompagnie_tv;
        TextView statut_tv;
        TextView date_tv;
        public AppViewHolder(@NonNull View itemView) {
            super(itemView);
            image_iv = itemView.findViewById(R.id.imageApp_iv);
            statut_iv = itemView.findViewById(R.id.statut_iv);
            titre_tv = itemView.findViewById(R.id.titreApp_tv);
            nomCompagnie_tv = itemView.findViewById(R.id.nomCompagnieApp_tv);
            statut_tv = itemView.findViewById(R.id.statutApp_tv);
            date_tv = itemView.findViewById(R.id.dateApp_tv);
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
