package com.example.dettesclients;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "dettes.db";
    public static final int DB_VERSION = 3;

    public static final String TABLE_CLIENT = "clients";
    public static final String TABLE_DETTE = "dettes";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Création table clients
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS " + TABLE_CLIENT + "(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "nom TEXT NOT NULL," +
                        "telephone TEXT)"
        );

        // Création table dettes
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS " + TABLE_DETTE + "(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "client_id INTEGER," +
                        "montant REAL," +
                        "description TEXT," +
                        "date TEXT," +
                        "paye INTEGER DEFAULT 0," +
                        "FOREIGN KEY(client_id) REFERENCES clients(id) ON DELETE CASCADE)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Migration : si ancienne version < 3, ajouter colonne paye si elle n'existe pas
        if (oldVersion < 3) {
            try {
                db.execSQL("ALTER TABLE " + TABLE_DETTE + " ADD COLUMN paye INTEGER DEFAULT 0");
            } catch (Exception e) {
                e.printStackTrace(); // ignore si colonne existe déjà
            }
        }
        // Tu peux ajouter d'autres migrations pour future version ici
    }
}
