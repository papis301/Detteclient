package com.example.dettesclients;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ClientViewHolder> {

    // Interface pour gérer le clic sur un client
    public interface OnClientClickListener {
        void onClientClick(Client client);
    }

    private List<Client> clients;
    private OnClientClickListener listener;

    public ClientAdapter(List<Client> clients, OnClientClickListener listener) {
        this.clients = clients;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_client, parent, false);
        return new ClientViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientViewHolder holder, int position) {
        Client client = clients.get(position);

        holder.txtNom.setText(client.getNom());
        holder.txtTel.setText(client.getTelephone());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onClientClick(client);
            }
        });
    }

    @Override
    public int getItemCount() {
        return clients != null ? clients.size() : 0;
    }

    static class ClientViewHolder extends RecyclerView.ViewHolder {

        TextView txtNom, txtTel;

        public ClientViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNom = itemView.findViewById(R.id.txtNom);
            txtTel = itemView.findViewById(R.id.txtTel);
        }
    }
}
