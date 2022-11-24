package com.example.sms_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Sms_DAO {
    // Champs de la base de données
    private SQLiteDatabase database;
    private SmsSQLiteDataBase dbHelper;

    private String[] allColumns = { SmsSQLiteDataBase.COLUMN_ID, SmsSQLiteDataBase.COLUMN_PN, SmsSQLiteDataBase.COLUMN_MSG};

    // Constructeur du DAO
    public Sms_DAO(Context context) {
        dbHelper = new SmsSQLiteDataBase(context);
    }

    // OPEN DB
    public void Open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    // CLOSE DB
    public void Close() {
        dbHelper.close();
    }

    public SmsDataModel CreateSms(String phoneNumber, String message) {
        ContentValues values = new ContentValues();
        values.put(SmsSQLiteDataBase.COLUMN_PN, phoneNumber);
        values.put(SmsSQLiteDataBase.COLUMN_MSG, message);

        long insertId = database.insert(SmsSQLiteDataBase.TABLE_NAME, null, values);

        Log.d("_SMS", "CreateSms: id " + insertId + "msg : " + message + " pn : " + phoneNumber);

        Cursor cursor = database.query(SmsSQLiteDataBase.TABLE_NAME,
                allColumns, SmsSQLiteDataBase.COLUMN_ID + " = " + insertId, null,
                null, null, null);

        cursor.moveToFirst();
        SmsDataModel newSms = cursorToSms(cursor);
        cursor.close();

        return newSms;
    }

    // Supprime un SMS de la BDD
    public void DeleteSms(SmsDataModel sms) {
        long id = sms.getId();

        Log.d("_SMS", "DeleteSms: id" + id);

        database.delete(SmsSQLiteDataBase.TABLE_NAME, SmsSQLiteDataBase.COLUMN_ID
                + " = " + id, null);
    }

    // Retourne tous les SMS de la BDD
    public List<SmsDataModel> GetAllSmss() {
        List<SmsDataModel> smss = new ArrayList<SmsDataModel>();

        Cursor cursor = database.query(SmsSQLiteDataBase.TABLE_NAME,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            SmsDataModel comment = cursorToSms(cursor);
            smss.add(comment);
            cursor.moveToNext();
        }

        // Fermeture du curseur
        cursor.close();

        return smss;
    }

    // Retourne un sms de la BDD a partir d'un curseur (position du sms dans la bdd)
    private SmsDataModel cursorToSms(Cursor cursor) {
        SmsDataModel sms = new SmsDataModel();

        sms.setId(cursor.getLong(0));
        sms.setPhoneNumber(cursor.getString(1));
        sms.setMessage(cursor.getString(2));

        return sms;
    }
}
