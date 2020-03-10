package com.salyin.muzos;



import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import Scenes.Hud;
import Screens.GameOverScreen;
import Screens.PlayScreen;
import Tools.WorldContact;
import bdd.GameDataBase;


/**
 *
 * @Author Kitsu ( Juan Miguel )
 *
 * MainClass for Game, this class extends from Game and act as the "Manager" of the set Screen.
 * The mainClass set the screen as the first stage of the game.
 * Set final static variables to set the Size and type of scale
 *
 */
public class Main extends Game {
    //Variables
    public static final int V_WIDTH = 512; //Constant with width size
    public static final int V_HEIGHT = 240; //Constant with height size
    public static final float ppm = 100; //Constant with scale size
    public SpriteBatch batch; //Define SpriteBatch
    public int nSlimes; //Define the nSlimes from Android
    public Hud hud; //Define the Hud
    public WorldContact wrd; //Define WorldContact
    private GameDataBase gameDataBase; //Define GameDataBase

    //Main of the game, getting parameters from android.
    public Main(int slimesMode, GameDataBase gameDataBase) {
        this.nSlimes = slimesMode;
        this.gameDataBase = gameDataBase;
    }


    //OnCreate of the game, set batch, set the main screen to
    //PlayScreen with the nSlimes and database from the game.
    //wrd is WorldContact to put the gamedatabase function in beginContact.
    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new PlayScreen(this, nSlimes, gameDataBase));
        hud = new Hud(batch, nSlimes, gameDataBase);
        wrd = new WorldContact(gameDataBase);

    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
    }


}