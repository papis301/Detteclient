package com.example.dettesclients;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ClientDao {

    private DBHelper dbHelper;

    public ClientDao(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void insert(Client client) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nom", client.getNom());
        cv.put("telephone", client.getTelephone());
        db.insert(DBHelper.TABLE_CLIENT, null, cv);
    }

    public List<Client> getAll() {
        List<Client> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM clients ORDER BY id DESC", null);

        while (c.moveToNext()) {
            list.add(new Client(
                    c.getInt(0),
                    c.getString(1),
                    c.getString(2)
            ));
        }
        c.close();
        return list;
    }
}

