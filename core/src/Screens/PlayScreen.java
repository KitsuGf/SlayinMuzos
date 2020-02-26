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
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.salyin.muzos.Main;

import java.util.LinkedList;

import javax.imageio.ImageTranscoder;

import Scenes.Hud;
import Sprites.EnemyOne;
import Sprites.HeroSword;
import Tools.WorldContact;
import Tools.WorldCreator;

public class PlayScreen implements Screen {

    //region Private Vars

    private static HeroSword hs;
    public PlayScreen() {}

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
    private Boolean isDerecha;
    private final static float timeOut = 2;
    private float time = 0;
    private MotionState motionState=MotionState.NONE;
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


//endregion

    public PlayScreen(Main game) {
        heroSwordAtlas =  new TextureAtlas("sprites/hero_sword/Mario_and_Enemies.pack");
        //Screen and stages.
        this.game = game;
        b = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        //Skins and atlas.
        tAtlasButtons = new TextureAtlas("hud_buttons/buttons.atlas");
        Skin btSkin = new Skin();
        btSkin.addRegions(tAtlasButtons);

        //TODO BACKGROUND
        img = new Texture(Gdx.files.internal("skin/bg_ui.png"));

        //region button right
        ImageButton.ImageButtonStyle rightStyle = new ImageButton.ImageButtonStyle(); //** Button properties **//
        rightStyle.up = btSkin.getDrawable("right_red");
        rightStyle.down = btSkin.getDrawable("right_green");
        rightButton = new ImageButton(rightStyle);
        rightButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

                motionState=MotionState.NONE;
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("Me muevo a:", "Derecha");
                motionState=MotionState.RIGHT;
                return true;
            }
        });
        //endregion button left;

        //region button left
        ImageButton.ImageButtonStyle leftStyle = new ImageButton.ImageButtonStyle(); //** Button properties **//
        leftStyle.up = btSkin.getDrawable("left_red");
        leftStyle.down = btSkin.getDrawable("left_green");
        leftButton = new ImageButton(leftStyle);
        leftButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

                motionState=MotionState.NONE;
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("Me muevo a:", "Izquierda");
                motionState=MotionState.LEFT;
                return true;
            }
        });
        //endregion button left

        //region button jump
        ImageButton.ImageButtonStyle jumpStyle = new ImageButton.ImageButtonStyle(); //** Button properties **//
        jumpStyle.up = btSkin.getDrawable("jump_red");
        jumpStyle.down = btSkin.getDrawable("jump_green");
        jumpButton = new ImageButton(jumpStyle);
        jumpButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

                motionState=MotionState.NONE;
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("Accion:", "Salto!");
                motionState=MotionState.UP;
                return true;
            }
        });
        //endregion button jump

        //region Table for buttons
        Table tb = new Table();
        tb.bottom();
        tb.debug();
        tb.setFillParent(true);

        tb.add(leftButton).height(Gdx.graphics.getHeight()/4).width(Gdx.graphics.getWidth()/4);
        tb.add(rightButton).height(Gdx.graphics.getHeight()/4).width(Gdx.graphics.getWidth()/4).padRight(Gdx.graphics.getWidth()/4);
        tb.add(jumpButton).height(Gdx.graphics.getHeight()/4).width(Gdx.graphics.getWidth()/4);

        stage.addActor(tb);
        //endregion


        //Declare the orthographic camera.
        gamecam = new OrthographicCamera();
        //Declare a New viewPort with calcs in meters
        //Call hud class
        hud = new Hud(game.batch);





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

        player = new HeroSword(world, this);

        //Setter from contactListneer in worldContact.
        world.setContactListener(new WorldContact());

    }



    public TextureAtlas getHeroSwordAtlas(){return heroSwordAtlas;}

    // TODO INTENTAR QUE ESTO SEA UNA CLASE EXTERNA.
    //region ENUM TO  BUTTON BINDING IN TOUCHSCREEN.
    private enum MotionState {

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

        player.update(dt);
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
        //game.batch.setProjectionMatrix(hud.stage.getCamera().combined);


        //TODO FIX THE BACKGROUND OF TABLE.
        /*b.begin();
        b.draw(img, 0, 0);
        b.end();*/


        stage.act();
        stage.draw();

        
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
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
        map.dispose();
        renderer.render();
        world.dispose();
        b2dr.dispose();
        img.dispose();
        hud.dispose();
    }
}
