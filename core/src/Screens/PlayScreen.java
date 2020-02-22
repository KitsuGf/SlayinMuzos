package Screens;


/**
 * @Author Kitsu ( Juan Miguel )
 * <p>
 * PlayScreen is class to implements methods in Main(game)
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.salyin.muzos.Main;

import java.util.LinkedList;

import Scenes.Hud;
import Scenes.HudControllers;
import Sprites.EnemyOne;
import Sprites.HeroSword;
import Tools.WorldContact;
import Tools.WorldCreator;

public class PlayScreen implements Screen {

    //region Private Vars

    private static HeroSword hs;
    public HeroSword getHs() {
        return hs;
    }
    public void setHs(HeroSword hs) {
        this.hs = hs;
    }
    private World world;
    private Box2DDebugRenderer b2dr;
    private OrthographicCamera gamecam;
    private Main game;
    private Viewport gamePort;
    private Hud hud;
    private HudControllers hudc;
    private HeroSword player;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private LinkedList<EnemyOne> enemies = new LinkedList<EnemyOne>();
    private Boolean isDerecha;
    private final static float timeOut = 2;
    private float time = 0;
    private MotionState motionState=MotionState.NONE;
    private Stage stage;
    public Stage stageBt;
    public Viewport vpBt;
    private static int jumps = 0;
    private static int maxJumps = 1;

    //endregion

    public PlayScreen(Main game) {
        this.game = game;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Skin mySkin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));


        //TODO INTENTAR REGULAR LOS TAMAÑOS E INTENTAR ENVIARLO A UNA TABLA.
        //region button right
        ImageButton rightButton = new ImageButton(mySkin);
        rightButton.setSize(100,rightButton.getHeight());
        rightButton.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("flechas/flechaRoja/rightFechaRoja.png"))));
        rightButton.getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("flechas/flechaVerde/rightFechaVerde.png"))));
        rightButton.setPosition(200 ,0 );
        rightButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("pruba", "si");
                motionState=MotionState.NONE;
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("pruba", "si");
                motionState=MotionState.RIGHT;
                return true;
            }
        });
        stage.addActor(rightButton);
        //endregion button left;

        //region button left
        ImageButton leftButton = new ImageButton(mySkin);
        leftButton.setSize(100,rightButton.getHeight());
        leftButton.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("flechas/flechaRoja/flechaRoja.png"))));
        leftButton.getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("flechas/flechaVerde/fechaVerde.png"))));
        leftButton.setPosition(40,0);
        leftButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("pruba", "si");
                motionState=MotionState.NONE;
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("pruba", "si");
                motionState=MotionState.LEFT;
                return true;
            }
        });
        stage.addActor(leftButton);
        //endregion button left

        //region button jump
        ImageButton jumpButton = new ImageButton(mySkin);
        jumpButton.setSize(100 ,rightButton.getHeight());
        jumpButton.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("flechas/flechaRoja/flechaRoja.png"))));
        jumpButton.getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("flechas/flechaVerde/fechaVerde.png"))));
        jumpButton.setPosition(500,0);
        jumpButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("pruba", "si");
                motionState=MotionState.NONE;
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("pruba", "si");
                motionState=MotionState.UP;
                return true;
            }
        });
        stage.addActor(jumpButton);
        //endregion button jump


        //Declare the orthographic camera.
        gamecam = new OrthographicCamera();
        //Declare a New viewPort with calcs in meters
        //Call hud class
        hud = new Hud(game.batch);
        hudc = new HudControllers(game.batch);

        //Load the tmxMap
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("maps/rooms/room_castle/castle_room.tmx");
        //Renderer render the tmxMap and use unitScale to define the scale in meters
        renderer = new OrthogonalTiledMapRenderer(map, 1 / Main.ppm);
        gamePort = new StretchViewport(Main.V_WIDTH / Main.ppm, Main.V_HEIGHT / Main.ppm, gamecam);
        //Set the positions of the viewport
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 4, 0);
        //Call world and make it a object with parameters
        world = new World(new Vector2(0, -10), true);
        //Box2Debugger its call to see the collisions in MapObjects.
        b2dr = new Box2DDebugRenderer();

        //Call class WorldCreator to create collisions and etc.
        new WorldCreator(world, map);

        player = new HeroSword(world);

        //Setter from contactListneer in worldContact.
        world.setContactListener(new WorldContact());

    }

    // TODO INTENTAR QUE ESTO SEA UNA CLASE EXTERNA.
    //region ENUM TO  BUTTON BINDING IN TOUCHSCREEN.
    enum MotionState {

        NONE {
            @Override
            public boolean update(HeroSword player) {

                return true;
            }
        },

        UP {
            @Override
            public boolean update(HeroSword player) {


                // the limit is reached
                if(jumps == maxJumps)
                    return true;

                player.b2body.applyLinearImpulse(new Vector2(0, 2.5f), player.b2body.getWorldCenter(), true);
                jumps++;
                return true;

            }
        },


        LEFT{
            @Override
            public boolean update(HeroSword  player)  {
                if (player.b2body.getLinearVelocity().x >= -2) {
                    player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
                    hs.changeDirection(false);
                }
                return false;
            }
        },

        RIGHT{
            @Override
            public boolean update(HeroSword player) {

                if (player.b2body.getLinearVelocity().x <= 2) {
                    player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
                    hs.changeDirection(true);

                }

                return false;
            }
        };

        public abstract boolean update(HeroSword player);
    }
    //endregion ENUM TO  BUTTON BINDING IN TOUCHSCREEN.


    //region PLAYSCREEN BOOLEAN PARA EL GIRO
    public PlayScreen(Boolean isDerecha) {
        this.isDerecha = isDerecha;
    }

    public Boolean getDerecha() {
        return isDerecha;
    }

    public void setDerecha(Boolean derecha) {
        isDerecha = derecha;
    }
    //endregion
    @Override
    public void show() {

    }


    //This method is for encapsule all updates.
    public void update(float dt) {
        //region Use TIME variable to add this and delta time.
        time += Gdx.graphics.getDeltaTime();
        //Now if time is higher than time_out spawn enemy, if not, stop.
        if (time > timeOut){
            //condition to add enemies until 10 enemies.
            if (enemies.size() != 10){
                enemies.add(new EnemyOne(world));
                time = 0; // or set to 0 to repeat the process
            }
        }
        //endregion

        world.step(1 / 60f, 6, 2);
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

        //region inputs for motionState
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) motionState=MotionState.UP;
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) motionState=MotionState.LEFT;
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) motionState=MotionState.RIGHT;

        if(motionState.update(player)) motionState=MotionState.NONE;
        //endregion

        //region method to limit jump.
        if(player.b2body.getLinearVelocity().y == 0)
            jumps = 0;
        //endregion

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();
        b2dr.render(world, gamecam.combined);
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);

        stage.act();
        stage.draw();

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
