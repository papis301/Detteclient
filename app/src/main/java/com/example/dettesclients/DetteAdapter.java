package com.example.dettesclients;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class DetteAdapter extends RecyclerView.Adapter<DetteAdapter.DetteViewHolder> {

    private List<Dette> dettes;

    public DetteAdapter(List<Dette> dettes) {
        this.dettes = dettes;
    }

    @NonNull
    @Override
    public DetteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_dette, parent, false);
        return new DetteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DetteViewHolder holder, int position) {
        Dette d = dettes.get(position);

        holder.txtMontant.setText("Montant : " + d.getMontant());
        holder.txtDesc.setText(d.getDescription());
        holder.txtDate.setText(d.getDate());
    }

    @Override
    public int getItemCount() {
        return dettes != null ? dettes.size() : 0;
    }

    static class DetteViewHolder extends RecyclerView.ViewHolder {

        TextView txtMontant, txtDesc, txtDate;

        public DetteViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMontant = itemView.findViewById(R.id.txtMontant);
            txtDesc = itemView.findViewById(R.id.txtDescription);
            txtDate = itemView.findViewById(R.id.txtDate);
        }
    }
}

