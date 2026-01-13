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

    int clientId;
    DetteDao detteDao;
    TextView txtTotal;
    RecyclerView recycler;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_client_dettes);

        clientId = getIntent().getIntExtra("client_id", -1);
        detteDao = new DetteDao(this);

        txtTotal = findViewById(R.id.txtTotal);
        recycler = findViewById(R.id.recyclerDettes);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        findViewById(R.id.btnAddDette).setOnClickListener(v -> showDialog());
        load();
    }

    void load() {
        List<Dette> list = detteDao.getByClient(clientId);
        recycler.setAdapter(new DetteAdapter(list));
        txtTotal.setText("Total : " + detteDao.totalByClient(clientId));
    }

    void showDialog() {
        View v = getLayoutInflater().inflate(R.layout.dialog_add_dette, null);
        EditText edtMontant = v.findViewById(R.id.edtMontant);
        EditText edtDesc = v.findViewById(R.id.edtDescription);

        new AlertDialog.Builder(this)
                .setTitle("Ajouter dette")
                .setView(v)
                .setPositiveButton("OK", (d, w) -> {
                    String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                            .format(new Date());
                    detteDao.insert(new Dette(
                            clientId,
                            Double.parseDouble(edtMontant.getText().toString()),
                            edtDesc.getText().toString(),
                            date
                    ));
                    load();
                })
                .setNegativeButton("Annuler", null)
                .show();
    }
}
