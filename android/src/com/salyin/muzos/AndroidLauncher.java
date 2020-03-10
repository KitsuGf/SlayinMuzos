package com.salyin.muzos;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import bdd.AndroidDataBase;


/**
 * @Author Kitsu ( Juan Miguel )
 *
 * This class is responsible to connect android
 * activities and core from Libgdx.
 * In this case i get some parameters from bundle.
 */
public class AndroidLauncher extends AndroidApplication {


	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new Main(this.getIntent().getExtras().getInt("nSlime"),new AndroidDataBase(this)), config);

	}


}
