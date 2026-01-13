package com.example.dettesclients;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ClientDettesActivity extends AppCompatActivity {

    private int clientId;
    private String clientNom;
    private DetteDao detteDao;
    private RecyclerView recycler;
    private TextView txtTotal;
    private DetteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_dettes);

        clientId = getIntent().getIntExtra("client_id", -1);
        clientNom = getIntent().getStringExtra("client_nom");

        setTitle("Dettes de " + clientNom);

        recycler = findViewById(R.id.recyclerDettes);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        txtTotal = findViewById(R.id.txtTotal);

        detteDao = new DetteDao(this);

        findViewById(R.id.btnAddDette).setOnClickListener(v -> showAddDetteDialog());

        loadDettes();
    }

    private void loadDettes() {
        List<Dette> list = detteDao.getByClient(clientId);

        adapter = new DetteAdapter(list, dette -> showEditDetteDialog(dette));
        recycler.setAdapter(adapter);

        double total = detteDao.totalByClient(clientId);
        txtTotal.setText("Total Restant: " + total);
    }

    private void showAddDetteDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_add_dette, null);

        EditText edtMontant = view.findViewById(R.id.edtMontant);
        EditText edtDesc = view.findViewById(R.id.edtDescription);

        new AlertDialog.Builder(this)
                .setTitle("Ajouter une dette")
                .setView(view)
                .setPositiveButton("Enregistrer", (dialog, which) -> {
                    double montant;
                    try {
                        montant = Double.parseDouble(edtMontant.getText().toString());
                    } catch (Exception e) {
                        edtMontant.setError("Montant invalide");
                        return;
                    }

                    String desc = edtDesc.getText().toString().trim();
                    String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

                    Dette d = new Dette(clientId, montant, desc, date);
                    detteDao.insert(d);
                    loadDettes();
                })
                .setNegativeButton("Annuler", null)
                .show();
    }

    private void showEditDetteDialog(Dette d) {
        View view = getLayoutInflater().inflate(R.layout.dialog_add_dette, null);

        EditText edtMontant = view.findViewById(R.id.edtMontant);
        EditText edtDesc = view.findViewById(R.id.edtDescription);

        edtMontant.setText(String.valueOf(d.getMontant()));
        edtDesc.setText(d.getDescription());

        new AlertDialog.Builder(this)
                .setTitle("Modifier dette")
                .setView(view)
                .setPositiveButton("Enregistrer", (dialog, which) -> {
                    try {
                        d.setMontant(Double.parseDouble(edtMontant.getText().toString()));
                    } catch (Exception e) {
                        edtMontant.setError("Montant invalide");
                        return;
                    }
                    d.setDescription(edtDesc.getText().toString());
                    detteDao.update(d);
                    loadDettes();
                })
                .setNeutralButton(d.isPaye() ? "Marquer non payée" : "Marquer payée", (dialog, which) -> {
                    d.setPaye(!d.isPaye());
                    detteDao.update(d);
                    loadDettes();
                })
                .setNegativeButton("Annuler", null)
                .show();
    }
}
