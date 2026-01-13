package com.example.dettesclients;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;
import java.util.List;

public class DetteDao {

    DBHelper dbHelper;

    public DetteDao(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void insert(Dette d) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("client_id", d.getClientId());
        cv.put("montant", d.getMontant());
        cv.put("description", d.getDescription());
        cv.put("date", d.getDate());
        db.insert("dettes", null, cv);
    }

    public List<Dette> getByClient(int clientId) {
        List<Dette> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor c = db.rawQuery(
                "SELECT * FROM dettes WHERE client_id=? ORDER BY id DESC",
                new String[]{String.valueOf(clientId)}
        );

        while (c.moveToNext()) {
            list.add(new Dette(
                    c.getInt(c.getColumnIndexOrThrow("id")),           // id
                    c.getInt(c.getColumnIndexOrThrow("client_id")),    // client_id
                    c.getDouble(c.getColumnIndexOrThrow("montant")),   // montant
                    c.getString(c.getColumnIndexOrThrow("description")), // description
                    c.getString(c.getColumnIndexOrThrow("date")),     // date
                    c.getInt(c.getColumnIndexOrThrow("paye")) == 1    // payée ?
            ));
        }

        c.close();
        return list;
    }


    // Total des dettes non payées pour un client
    public double totalByClient(int clientId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT SUM(montant) FROM dettes WHERE client_id=? AND paye=0",
                new String[]{String.valueOf(clientId)}
        );

        double total = 0;
        if (c.moveToFirst()) {
            total = c.getDouble(0);
        }
        c.close();
        return total;
    }


    public void update(Dette d) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("montant", d.getMontant());
        cv.put("description", d.getDescription());
        cv.put("paye", d.isPaye() ? 1 : 0);
        db.update("dettes", cv, "id=?", new String[]{String.valueOf(d.getId())});
    }

    public void markAsPaid(int detteId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("paye", 1);
        db.update("dettes", cv, "id=?", new String[]{String.valueOf(detteId)});
    }
}

