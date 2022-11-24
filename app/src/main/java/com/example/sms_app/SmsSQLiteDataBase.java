package com.example.sms_app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Cette classe se charge de la création de la base de données.
 * La méthode onUpgrade() va simplement supprimer toutes les données existantes et recréer la table.
 * Elle définit également plusieurs constantes.
 */

public class SmsSQLiteDataBase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "sms.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "SMS";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_PN = "PhoneNumber";
    public static final String COLUMN_MSG = "Message";


    // Commande sql pour la création de la base de données
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_PN + " TEXT NOT NULL, " +
            COLUMN_MSG + " TEXT NOT NULL " +
            ");";

    // Constructeur par defaut
    public SmsSQLiteDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SmsSQLiteDataBase.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
