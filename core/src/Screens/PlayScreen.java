package Screens;


/**
 * @Author Kitsu ( Juan Miguel )
 *
 * PlayScreen is class to implements methods in Main(game)
 *
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.salyin.muzos.Main;
import com.sun.org.apache.xpath.internal.operations.Bool;


import java.util.ArrayList;
import java.util.Random;

import Scenes.Hud;
import Sprites.EnemyOne;
import Sprites.HeroSword;
import Tools.WorldContact;
import Tools.WorldCreator;

public class PlayScreen implements Screen {

    //Private Vars

    private World world;
    private Box2DDebugRenderer b2dr;
    private OrthographicCamera gamecam;
    private Main game;
    private Viewport gamePort;
    private Hud hud;
    private HeroSword player;
    private HeroSword player2;
    private HeroSword player3;
    private HeroSword player4;
    private HeroSword player5;
    private EnemyOne enemy;
    private EnemyOne enemy2;
    private EnemyOne enemy3;
    private EnemyOne enemy4;
    private EnemyOne enemy5;
    private EnemyOne enemy6;
    private EnemyOne enemy7;
    private EnemyOne enemy8;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private Timer tm;
    private ArrayList<HeroSword> heroRandom;
    private Boolean isDerecha;




    public PlayScreen(Main game) {
        this.game = game;

        //Declare the orthographic camera.
        gamecam = new OrthographicCamera();
        //Declare a New viewPort with calcs in meters
        //Call hud class
        hud = new Hud(game.batch);
        //Load the tmxMap
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("maps/rooms/room_castle/castle_room.tmx");
        //Renderer render the tmxMap and use unitScale to define the scale in meters
        renderer = new OrthogonalTiledMapRenderer(map, 1/Main.ppm);
        gamePort = new StretchViewport(Main.V_WIDTH / Main.ppm, Main.V_HEIGHT / Main.ppm, gamecam);
        //Set the positions of the viewport
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
        //Call world and make it a object with parameters
        world = new World(new Vector2(0,-10), true);
        //Box2Debugger its call to see the collisions in MapObjects.
        b2dr = new Box2DDebugRenderer();

        //Call class WorldCreator to create collisions and etc.
        new WorldCreator(world, map);

        player = new HeroSword(world);

        enemy = new EnemyOne(world);
        enemy2 = new EnemyOne(world);
        enemy3 = new EnemyOne(world);
        enemy4 = new EnemyOne(world);
        enemy5 = new EnemyOne(world);
        enemy6 = new EnemyOne(world);
        enemy7 = new EnemyOne(world);
        enemy8 = new EnemyOne(world);

        world.setContactListener(new WorldContact());


        /*int limite = 10;

        for (int i = 0; i < limite ; i++) {
            heroRandom.add(new HeroSword(world));
        }*/

        //player.b2body.setTransform(200/Main.ppm, 300/Main.ppm , 0);
        //player2.b2body.setTransform();


    }


    public PlayScreen(Boolean isDerecha) {
        this.isDerecha = isDerecha;
    }


    public Boolean getDerecha() {
        return isDerecha;
    }

    public void setDerecha(Boolean derecha) {
        isDerecha = derecha;
    }

    @Override
    public void show() {

    }

    /**
     *
     * This Class is maded to get the keySwitch from keyboard.
     *
     * //TODO ARREGLA EL PUTO SALTO
     *
     * @param dt is DeltaTime
     */
    public void handleInput(float dt) {
       if (Gdx.input.isKeyJustPressed(Input.Keys.UP)){
           player.b2body.applyLinearImpulse(new Vector2(0,4f), player.b2body.getWorldCenter(), true);
       }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2) {
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
            isDerecha = true;

        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2) {
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
            isDerecha = false;

        }



    }



    //This method is for encapsule all updates.
    public void update(float dt) {
        handleInput(dt);
        world.step(1/60f, 6, 2);
        //Gamecam.position follow the player.
        //gamecam.position.x = player.b2body.getPosition().x;
        //gamecam.position.y = player.b2body.getPosition().y;
        gamecam.update();
        renderer.setView(gamecam);
    }


    //TODO Termina de comentar esto Juanmi

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        b2dr.render(world, gamecam.combined);
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);

        hud.stage.draw();
        game.batch.begin();
        game.batch.end();

    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.render();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }
}
