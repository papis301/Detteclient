package com.example.dettesclients;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dettesclients.Dette;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class DetteAdapter extends RecyclerView.Adapter<DetteAdapter.DetteViewHolder> {

    private final Context context;
    private final List<Dette> dettes;
    private final OnDetteActionListener listener;

    public interface OnDetteActionListener {
        void onMarquerPaye(Dette dette);
        void onLongClick(Dette dette);
    }

    public DetteAdapter(Context context, List<Dette> dettes, OnDetteActionListener listener) {
        this.context = context;
        this.dettes = dettes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DetteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.item_dette, parent, false);
        return new DetteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DetteViewHolder holder, int position) {
        Dette d = dettes.get(position);

        holder.txtMontant.setText("Montant : " + d.getMontant() + " FCFA");
        holder.txtDesc.setText(d.getDescription());
        holder.txtDate.setText(d.getDate());

        if (d.isPaye()) {
            holder.card.setCardBackgroundColor(
                    ContextCompat.getColor(context, R.color.md_theme_secondaryContainer)
            );
            holder.btnPayer.setVisibility(View.GONE);
        } else {
            holder.card.setCardBackgroundColor(
                    ContextCompat.getColor(context, android.R.color.white)
            );
            holder.btnPayer.setVisibility(View.VISIBLE);
        }

        holder.btnPayer.setOnClickListener(v -> {
            if (listener != null) listener.onMarquerPaye(d);
        });

        holder.itemView.setOnLongClickListener(v -> {
            if (listener != null) listener.onLongClick(d);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return dettes.size();
    }

    static class DetteViewHolder extends RecyclerView.ViewHolder {

        MaterialCardView card;
        TextView txtMontant, txtDesc, txtDate;
        MaterialButton btnPayer;

        public DetteViewHolder(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.cardDette);
            txtMontant = itemView.findViewById(R.id.txtMontant);
            txtDesc = itemView.findViewById(R.id.txtDesc);
            txtDate = itemView.findViewById(R.id.txtDate);
            btnPayer = itemView.findViewById(R.id.btnPayer);
        }
    }
}
