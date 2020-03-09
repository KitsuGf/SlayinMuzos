package Scenes;

/**
 *
 * @Author Kitsu ( Juan Miguel )
 *
 * @param Sb from Main
 * @param nSlimes from Main
 *
 * Hud Class is made to manage the HudPoints from the game.
 * Implements Disposable is needed to dispose the Hud in PlayScreen.
 *
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.salyin.muzos.Main;

import Tools.WorldContact;
import bdd.GameDataBase;

public class Hud implements Disposable {

    public Stage stage;
    public Viewport viewport;
    private static Integer countEnemy;
    private Integer countCombo;
    private static Integer countCoins;
    private static WorldContact wrd;
    private Label lbCombo;
    private static Label lbEnemy;
    private static Label lbCoins;
    private static Label scoreLabel;
    private Label lbTxComb;
    private Label lbTxEnemy;
    private Label lbTxCoins;
    static GameDataBase bdd;
    public Hud() {
    }


    public Hud(SpriteBatch sb, int nSlimes, GameDataBase gameDataBase) {
        bdd = gameDataBase;

        //Declare countEnemy as parameter nSlime from MainMenu. //TODO PONLE CONTADOR CUANDO CONSIGAS COMPLETRA LAS COLISIONES
        countEnemy = nSlimes;
        countCombo = 0;
        countCoins = 0;


        //Declare new StretchViewport to fit in the screen with the PPM and the new OCamera.
        viewport = new StretchViewport(Main.V_WIDTH, Main.V_HEIGHT, new OrthographicCamera());
        //Declare the stage as a new Stage with the viewport and the SpriteBatch.
        stage = new Stage(viewport, sb);

        //Create new Table to manage the position of the labels of the points.
        Table tb = new Table();
        //Set the table at the top of the screen.
        tb.top();
        //Set the fill parent as true.
        tb.setFillParent(true);

        //Making the labels with the info we want to show in the table.
        lbTxEnemy = new Label(String.format("Enemies:"), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        lbEnemy = new Label(String.format("%02d", countEnemy), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        lbTxCoins = new Label(String.format("Coins:"), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel =new Label(String.format("%06d", countCoins), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        lbTxComb = new Label(String.format("Combo:"), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        lbCombo = new Label(String.format("%03d", countCombo), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        //Add labels to a table in order and with the padding we want.
        tb.add(lbTxEnemy);
        tb.add(lbEnemy).padLeft(5);
        tb.add(lbTxCoins).padLeft(100);
        tb.add(lbCoins).padLeft(5);
        tb.add(lbTxComb).padLeft(100);
        tb.add(lbCombo).padLeft(5);
        //add the table to stage as Actor.
        stage.addActor(tb);

    }

    public void update (float dt){ }

    @Override
    public void dispose() {
        //Dispose the stage to show the table in PlayScreen.
        stage.dispose();

    }

    public static void addScore(int value){

        countEnemy -= value;
        Gdx.app.log("val", ""+countEnemy);
        lbEnemy.setText(String.format("%05d", countEnemy));

    }

}
