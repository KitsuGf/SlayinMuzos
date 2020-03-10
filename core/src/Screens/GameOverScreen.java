package Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.salyin.muzos.Main;


/**
 * @Author Kitsu ( Juan Miguel )
 *
 * GameOverScree is class to set the gameOverScreen
 * when the hero dies.
 */
public class GameOverScreen implements Screen {
    private Viewport viewport; //Define the viewport
    private Stage stage; //Define the stage
    private Game game; //Define the game

    //constructor with game
    public GameOverScreen(final Game game){
        this.game = game;
        //Camera setted at the size of the screen
        viewport= new FitViewport(Main.V_WIDTH, Main.V_HEIGHT, new OrthographicCamera());
        //set the stage with viewport
        stage = new Stage(viewport,((Main) game).batch);

        //create a label with default style (Sorry for that, no time)
        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        //Create a table
        Table tb = new Table();
        //set it at the center
        tb.center();
        //fill the row
        tb.setFillParent(true);
        //label what say GAME OVER
        Label gameOverlb = new Label ("GAME OVER", font);
        //add it to the table
        tb.add(gameOverlb).expandX();
        //set the table as actor.
        stage.addActor(tb);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
