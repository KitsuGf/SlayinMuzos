package com.salyin.muzos;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.salyin.muzos.Main;

import bdd.BaseDatos;

public class AndroidLauncher extends AndroidApplication {


	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new Main(this.getIntent().getExtras().getInt("nSlime"),new BaseDatos(this)), config);

	}


}
