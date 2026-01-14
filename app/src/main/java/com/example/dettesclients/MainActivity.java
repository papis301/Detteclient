package com.example.dettesclients;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.color.DynamicColors;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ClientAdapter adapter;
    private ClientDao clientDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 🚫 Désactiver Material You
        DynamicColors.applyToActivitiesIfAvailable(
                getApplication()
        );
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clientDao = new ClientDao(this);

        recyclerView = findViewById(R.id.recyclerClients);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fabAddClient);
        fab.setOnClickListener(v -> showAddClientDialog());

        loadClients();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadClients(); // rafraîchir la liste après retour des dettes
    }

    private void loadClients() {
        List<Client> clients = clientDao.getAll();

        adapter = new ClientAdapter(clients, client -> {
            Intent intent = new Intent(MainActivity.this, ClientDettesActivity.class);
            intent.putExtra("client_id", client.getId());
            intent.putExtra("client_nom", client.getNom());
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);
    }

    private void showAddClientDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_add_client, null);

        EditText edtNom = view.findViewById(R.id.edtNom);
        EditText edtTel = view.findViewById(R.id.edtTel);

        new AlertDialog.Builder(this)
                .setTitle("Ajouter un client")
                .setView(view)
                .setPositiveButton("Enregistrer", (dialog, which) -> {
                    String nom = edtNom.getText().toString().trim();
                    String tel = edtTel.getText().toString().trim();

                    if (nom.isEmpty()) {
                        Toast.makeText(this, "Le nom est obligatoire", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    clientDao.insert(new Client(nom, tel));
                    loadClients();
                })
                .setNegativeButton("Annuler", null)
                .show();
    }
}
