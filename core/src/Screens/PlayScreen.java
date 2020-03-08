package Screens;


/**
 * @Author Kitsu ( Juan Miguel )
 * <p>
 * PlayScreen is class to implements methods in Main(game)
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.salyin.muzos.Main;

import java.util.LinkedList;

import Scenes.Hud;
import Sprites.EnemyOne;
import Sprites.HeroSword;
import Tools.WorldContact;
import Tools.WorldCreator;
import Tools.WorldSensorLeft;
import Tools.WorldSensorRight;
import bdd.BaseDeDatos;

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
    private HeroSword player;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private LinkedList<EnemyOne> enemies = new LinkedList<EnemyOne>();
    private final static float timeOut = 3;
    private float time = 0;
    private MotionState motionState = MotionState.NONE;
    private Stage stage;
    private static int jumps = 0;
    private static int maxJumps = 1;
    private ImageButton rightButton;
    private ImageButton leftButton;
    private ImageButton jumpButton;
    private Texture img;
    private SpriteBatch b;
    private TextureAtlas tAtlasButtons;
    private TextureAtlas heroSwordAtlas;
    private TextureAtlas enemyOneAtlas;
    private int enemyNo = 0;
    private int slimeMode;
    private WorldSensorLeft sensorLeft;
    private WorldSensorRight sensorRight;
    private WorldContact wrd;
    private Music music;
    private int puntuacion;
    BaseDeDatos bdd;
    private int count = 0;
    private Batch batchTexto;
    BitmapFont textoPuntuacion;

//endregion

    public PlayScreen(Main game, int nSlimes, BaseDeDatos baseDeDatos) {
        //Base de Datos
        bdd = baseDeDatos;

        //Atlas searcher
        heroSwordAtlas = new TextureAtlas("sprites/hero_sword/viking.pack");
        enemyOneAtlas = new TextureAtlas("sprites/enemy_one/enemyOne.pack");
       // Gdx.app.log("asdasd", ""+puntuacion);
        //SlimeMode is a variable geting the game mode between normal mode or madness mode.
        slimeMode = nSlimes;
        //puntuacion = bdd.cargar();
        //Screen and stages.
        this.game = game;
        b = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        //Skins and atlas.
        tAtlasButtons = new TextureAtlas("hud_buttons/buttons.pack");
        Skin btSkin = new Skin();
        btSkin.addRegions(tAtlasButtons);

        //Skin buttons ui
        img = new Texture(Gdx.files.internal("skin/back_ui_2.png"));


        //region ImageButtons and Status in listener.
        //Here i declare the buttonStyle and call the skin to use drawables from
        //the atlas pack.
        //Inside the listener we have the events we can make in the game passed from status.

        //RightButton
        ImageButton.ImageButtonStyle rightStyle = new ImageButton.ImageButtonStyle();
        rightStyle.up = btSkin.getDrawable("right_off");
        rightStyle.down = btSkin.getDrawable("right_on");
        rightButton = new ImageButton(rightStyle);
        rightButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                motionState = MotionState.NONE;
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("Me muevo a:", "Derecha");
                motionState = MotionState.RIGHT;

                return true;
            }
        });

        //LeftButton
        ImageButton.ImageButtonStyle leftStyle = new ImageButton.ImageButtonStyle(); //** Button properties **//
        leftStyle.up = btSkin.getDrawable("left_off");
        leftStyle.down = btSkin.getDrawable("left_on");
        leftButton = new ImageButton(leftStyle);
        leftButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                motionState = MotionState.NONE;
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                Gdx.app.log("Me muevo a:", "Izquierda");

                motionState = MotionState.LEFT;

                return true;
            }
        });

        //JumpButton
        ImageButton.ImageButtonStyle jumpStyle = new ImageButton.ImageButtonStyle(); //** Button properties **//
        jumpStyle.up = btSkin.getDrawable("jump_off");
        jumpStyle.down = btSkin.getDrawable("jump_on");
        jumpButton = new ImageButton(jumpStyle);
        jumpButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                motionState = MotionState.NONE;
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("Accion:", "Salto!");
                motionState = MotionState.UP;

                return true;
            }
        });
        //endregion


        //Make a table to add buttons in better order.
        Table tb = new Table();
        tb.bottom();
        //tb.debug(); //Table debugger de-comment if is needed.
        tb.setFillParent(true);
        //Added the buttons to cells in the Table.
        tb.add(leftButton).height(Gdx.graphics.getHeight() / 6).width(Gdx.graphics.getWidth() / 7).padBottom(Gdx.graphics.getWidth() / 44);
        tb.add(rightButton).height(Gdx.graphics.getHeight() / 6).width(Gdx.graphics.getWidth() / 7).padBottom(Gdx.graphics.getWidth() / 44).padRight(Gdx.graphics.getWidth() / 2.7f);
        tb.add(jumpButton).height(Gdx.graphics.getHeight() / 6).width(Gdx.graphics.getWidth() / 7).padBottom(Gdx.graphics.getWidth() / 38).padRight(Gdx.graphics.getWidth() / 10);

        //Add the tb to Actor make the table interactive.
        stage.addActor(tb);


        //Declare the orthographic camera.
        gamecam = new OrthographicCamera();
        //Declare a New viewPort with calcs in ppm
        //Call hud class
        hud = new Hud(game.batch, nSlimes, bdd);


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
        //b2dr = new Box2DDebugRenderer();

        //Call class WorldCreator to create collisions and etc.
        new WorldCreator(world, map);

        //Create new objects
        player = new HeroSword(world, this);
        sensorLeft = new WorldSensorLeft(world, this);
        sensorRight = new WorldSensorRight(world, this);
        wrd = new WorldContact(bdd);

        //Music ingame
        music = Gdx.audio.newMusic(Gdx.files.internal("music/ingame.mp3"));
        music.setLooping(true);
        music.setVolume(50f);
        music.play();

        //Setter from contactListneer in worldContact.
        world.setContactListener(new WorldContact(bdd));

    }


    public TextureAtlas getHeroSwordAtlas() {
        return heroSwordAtlas;
    }

    public TextureAtlas getEnemyOneAtlas() {
        return enemyOneAtlas;
    }


    //
    //region ENUM TO  BUTTON BINDING IN TOUCHSCREEN.
    private enum MotionState {

        NONE {
            @Override
            public boolean update(HeroSword player) { return true; }
        },

        UP {
            @Override
            public boolean update(HeroSword player) {
                // the limit is reached
                if (jumps == maxJumps)
                    return true;

                player.b2body.applyLinearImpulse(new Vector2(0, 2.5f), player.b2body.getWorldCenter(), true);
                jumps++;


                return true;

            }
        },

        LEFT {
            @Override
            public boolean update(HeroSword player) {
                if (player.b2body.getLinearVelocity().x >= -2) {
                    player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
                    hs.changeDirection(false);
                }
                //method to flip sprite
                if (player.b2body.getLinearVelocity().x <= 0 && !player.isFlipX()) {
                    player.flip(true, false);
                }
                return false;
            }
        },

        RIGHT {
            @Override
            public boolean update(HeroSword player) {
                if (player.b2body.getLinearVelocity().x <= 2) {
                    player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
                    hs.changeDirection(true);
                }
                //method to flip sprite
                if (player.b2body.getLinearVelocity().x >= 0 && player.isFlipX()) {
                    player.flip(true, false);
                }

                return false;
            }
        };

        public abstract boolean update(HeroSword player);
    }
    //endregion ENUM TO  BUTTON BINDING IN TOUCHSCREEN.



    //endregion
    @Override
    public void show() {

    }

    //This method is for encapsule all updates and send it to Render.
    public void update(float dt) {

        //region Use TIME variable to add this and delta time.
        time += Gdx.graphics.getDeltaTime();
        //Now if time is higher than time_out spawn enemy, if not, stop.
        if (time > timeOut) {
            //condition to add enemies until X enemies.
            if (enemies.size() != slimeMode) {
                enemies.add(new EnemyOne(world, this));
                time = 0;
            }
        }
        //endregion

        //Tells the physics engine that 1/60th of a second has passed every time you call it
        world.step(1 / 60f, 6, 2);
        //Update the player
        player.update(dt);
        hud.update(dt);

        //region gamecam follow
        //Gamecam.position follow the player.
        //gamecam.position.x = player.b2body.getPosition().x;
        //gamecam.position.y = player.b2body.getPosition().y;
        //endregion

        //Update camera
        gamecam.update();
        //render the view.
        renderer.setView(gamecam);
    }



    @Override
    public void render(float delta) {
        // Update all in the method

        update(delta);
        //"Timer" to use in limitjumper.
        time += Gdx.graphics.getDeltaTime();

        //region inputs for motionState
        //Inputs to make motionState and send it to buttons.
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) motionState = MotionState.UP;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) motionState = MotionState.LEFT;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) motionState = MotionState.RIGHT;

        if (motionState.update(player)) motionState = MotionState.NONE;
        //endregion

        //region method to limit jump.
        if (player.b2body.getLinearVelocity().y == 0)
            jumps = 0;
        //endregion


        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        renderer.render();

        //b2dr.render(world, gamecam.combined); //<- Uncomment to get the debugger collider.

        //Draw IMG interface
        b.begin();
        b.draw(img,0,0,  Gdx.graphics.getWidth() , Gdx.graphics.getHeight() / 4);
        b.end();

        //Draw the table with buttons
        stage.act();
        stage.draw();


        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);

        try {
           if (enemyNo <= enemies.size()){
               for (int i = 0; i < enemies.size(); i ++) {
                   EnemyOne currentEnemy = enemies.get(i);
                   currentEnemy.draw(game.batch);
                   if (currentEnemy.direction) {
                       if (currentEnemy.b2body.getLinearVelocity().x <= 1) {
                           currentEnemy.b2body.applyLinearImpulse(new Vector2(0.1f, 0), currentEnemy.b2body.getWorldCenter(), true);
                       }
                   } else {
                       if (currentEnemy.b2body.getLinearVelocity().x >= -1) {
                           currentEnemy.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), currentEnemy.b2body.getWorldCenter(), true);
                       }
                   }

                   currentEnemy.update(delta);
               }
           } // I'm a teapot
       } catch (IndexOutOfBoundsException e){

        }
        //Draw the game sprites.
        game.batch.end();



        //Draw the hud with parameters
        hud.stage.draw();




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
        //Call all dispose methods.
        map.dispose();
        renderer.render();
        world.dispose();
        b2dr.dispose();
        img.dispose();

        hud.dispose();
    }


}
