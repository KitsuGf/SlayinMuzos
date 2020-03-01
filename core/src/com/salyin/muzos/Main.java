package com.salyin.muzos;


/**
 *
 * @Author Kitsu ( Juan Miguel )
 *
 * //TODO RECUERDA COMENTAR LA CLASE Y SUS METODOS
 *
 */


import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import Scenes.Hud;
import Screens.PlayScreen;


public class Main extends Game {
    public static final int V_WIDTH = 512;
    public static final int V_HEIGHT = 240;
    public static final float ppm = 100;
    public SpriteBatch batch;
    public int nSlimes;
    public Hud hud;

    public Main(int slimesMode) {
        this.nSlimes = slimesMode;
    }


    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new PlayScreen(this, nSlimes));
        hud = new Hud(batch, nSlimes);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
    }


}