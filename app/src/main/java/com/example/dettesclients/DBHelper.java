package com.example.dettesclients;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "dettes.db";
    public static final int DB_VERSION = 2;

    public static final String TABLE_CLIENT = "clients";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + TABLE_CLIENT + "(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "nom TEXT NOT NULL," +
                        "telephone TEXT)"
        );

        db.execSQL(
                "CREATE TABLE dettes (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "client_id INTEGER," +
                        "montant REAL," +
                        "description TEXT," +
                        "date TEXT," +
                        "FOREIGN KEY(client_id) REFERENCES clients(id) ON DELETE CASCADE)"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENT);
        onCreate(db);
    }
}

