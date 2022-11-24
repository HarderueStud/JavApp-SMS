package com.example.sms_app;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.List;

public class SmsListActivity extends ListActivity {
    private static Sms_DAO sms_dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_list);

        sms_dao = new Sms_DAO(this);
        sms_dao.Open();

        RefreshSmsList();
    }

    private void RefreshSmsList(){
        List<SmsDataModel> values = sms_dao.GetAllSmss();
        ArrayAdapter<SmsDataModel> adapter = new ArrayAdapter<SmsDataModel>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }

    // Application qui reprend
    @Override
    protected void onResume() {
        sms_dao.Open();
        super.onResume();
    }

    // Application mise en pause
    @Override
    protected void onPause() {
        sms_dao.Close();
        super.onPause();
    }

    // Retour a l'activiter de base
    public void GoBackToMainActivity(View view) {
        Intent newIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(newIntent);
    }

    public void RefreshList(View view) {
        RefreshSmsList();
    }
}