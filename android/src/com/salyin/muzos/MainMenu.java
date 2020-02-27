package com.salyin.muzos;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainMenu extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void goPlay(View view) {
        Intent i = new Intent(
                this,
                AndroidLauncher.class);
        this.startActivity(i);
    }

    @Override
    public void onBackPressed() {
        //TODO CAMBIAR LOS STRINGS POR RESOURCES
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Salir del juego");
        builder.setMessage("¿Estás seguro de que quieres salir del juego?").setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        builder.create();
        builder.show();

    }


}
