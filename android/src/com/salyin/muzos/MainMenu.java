package com.salyin.muzos;

import android.content.Intent;
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

    public void goReg(View view) {
        Intent i = new Intent(
                this,
                Register.class);
        this.startActivity(i);

    }
}