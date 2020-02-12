package Scenes;

/**
 *
 * @Author Kitsu ( Juan Miguel )
 *
 * //TODO RECUERDA COMENTAR LA CLASE Y SUS METODOS
 *
 */

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


public class Hud implements Disposable {
    public Stage stage;
    public Viewport viewport;
    private Integer countEnemy;
    private Integer countCombo;
    private Integer countCoins;

    Label lbCombo;
    Label lbEnemy;
    Label lbCoins;
    Label lbTxComb;
    Label lbTxEnemy;
    Label lbTxCoins;


    public Hud(SpriteBatch sb) {

        countEnemy = 20;
        countCombo = 0;
        countCoins = 0;

        viewport = new StretchViewport(Main.V_WIDTH, Main.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table tb = new Table();
        tb.top();
        tb.setFillParent(true);

        lbTxEnemy = new Label(String.format("Enemies:"), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        lbEnemy = new Label(String.format("%02d", countEnemy), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        lbTxCoins = new Label(String.format("Coins:"), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        lbCoins = new Label(String.format("%05d", countCoins), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        lbTxComb = new Label(String.format("Combo:"), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        lbCombo = new Label(String.format("%03d", countCombo), new Label.LabelStyle(new BitmapFont(), Color.WHITE));


        tb.add(lbTxEnemy).expandX().padTop(0);
        tb.add(lbEnemy).expandX().padTop(0);
        tb.add(lbTxCoins).expandX().padTop(0);
        tb.add(lbCoins).expandX().padTop(0);
        tb.add(lbTxComb).expandX().padTop(0);
        tb.add(lbCombo).expandX().padTop(0);

        stage.addActor(tb);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
