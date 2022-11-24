package com.example.sms_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Le texte pour afficher l'etat des permissions.
    private TextView permissionState_Text;
    private Button permission_Button;

    private Button displaySms_Button;

    // Request Code pour les permissions SMS
    private static final int SMS_PERMISSION_CODE = 0;
    // La liste des permissions sous forme de tableau
    private static final String[] PERMISSIONS = {
            Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.SEND_SMS,
    };

    @Override
    // Nous somme au demarrage de l'activité.
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // On recupere les elements pour les permissions.
        permissionState_Text = findViewById(R.id.permissionInfos);
        permission_Button = findViewById(R.id.permissionBtn);

        displaySms_Button = findViewById(R.id.displaySms_Button);

        // Si nous avons pas toutes les permissions
        if(!IsAllPermissionGranted()){
            // Nous les demandons
            AskForPermissions();
        }
        SetupTextAndButtonForPermission();
    }

    /**
     * Les permissions pour recevoir / envoyer des SMS
     * https://developer.android.com/training/permissions/requesting
     */

    // Verifie que toutes les autorisations sont acceptées, dans ce cas la fonction retournera vrai, dans le cas contraire faux.
    private boolean IsAllPermissionGranted(){
        for (int x = 0; x < PERMISSIONS.length; x++){
            if(ContextCompat.checkSelfPermission(this, PERMISSIONS[x]) != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }

    // Demande des permissions
    private void AskForPermissions(){
        /* if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_SMS)) {
            // "This method returns true if the app has requested this permission previously and the user denied the request."
            // Pratique pour afficher des explications à l'utilisateur de maniere asynchrone
        }

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_SMS)) {
            // "This method returns true if the app has requested this permission previously and the user denied the request."
            // Pratique pour afficher des explications à l'utilisateur de maniere asynchrone
        }

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {
            // "This method returns true if the app has requested this permission previously and the user denied the request."
            // Pratique pour afficher des explications à l'utilisateur de maniere asynchrone
        } */

        // Demande des permissions, android demandera les permissions pas encore acceptées parmis la liste donnée
        ActivityCompat.requestPermissions(this, PERMISSIONS, SMS_PERMISSION_CODE);
    }

    // Action avec la reponse de la demande de permission
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            // REQUEST CODE pour READ, RECEIVE SMS..
            case SMS_PERMISSION_CODE:
                SetupTextAndButtonForPermission();
                break;
        }
    }

    private void SetupTextAndButtonForPermission(){

        boolean permissionOk = IsAllPermissionGranted();
        // Nous configurons le texte en consequence
        if(permissionOk)
            permissionState_Text.setText(R.string.permissionInfo_OK);
        else
            permissionState_Text.setText(R.string.permissionInfo_NotGood);

        // Si c'est ok -> on le cache, si non ok, on l'affiche
        DisplayPermissionButton(!permissionOk);
    }

    // Affiche / Cache le bouton pour les permissions
    private void DisplayPermissionButton(boolean disp){
        if(disp)
            permission_Button.setVisibility(View.VISIBLE);
        else
            permission_Button.setVisibility(View.INVISIBLE);
    }

    // Le click du bouton des permissions
    public void PermissionButton_OnClick(View view) {
        AskForPermissions();
        SetupTextAndButtonForPermission();
    }

    // Changement d'activiter, activiter affichant la liste des sms
    public void DisplaySms_Clicked(View view) {
        Intent newIntent = new Intent(getApplicationContext(), SmsListActivity.class);
        startActivity(newIntent);
    }
}