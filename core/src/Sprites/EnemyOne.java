package Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.salyin.muzos.Main;

import java.util.ArrayList;
import java.util.Random;

import Scenes.Hud;
import Screens.PlayScreen;

public class EnemyOne extends Sprite{

    protected Sprite sprite;
    public World world;
    public Body b2body;
    Random r = new Random();
    int low = 10;
    int high = 500;
    int random = r.nextInt(high-low) + low;
    private TextureRegion enemyStand;
    public boolean direction = false;
    public boolean isHeroDie = false;


    public EnemyOne(World world, PlayScreen screen) {
        super(screen.getEnemyOneAtlas().findRegion("sl"));
        enemyStand = new TextureRegion(getTexture(), 0,6,32,25);
        setBounds(-2,0,42/Main.ppm, 35/Main.ppm);
        setRegion(enemyStand);
        this.world = world;
        defineEnemy();

    }


    //Update with deltatime the position of the sprite.
    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);

    }

    public void defineEnemy(){

        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / Main.ppm, 32 / Main.ppm);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(8 / Main.ppm);


        fdef.shape = shape;
        b2body.createFixture(fdef);
        b2body.setTransform(random/Main.ppm, 300/Main.ppm , 0);
        b2body.createFixture(fdef).setUserData(this);
    }

    public void enemyHitted(int cont){

        Gdx.app.postRunnable(new Runnable(){
            public void run(){
                if (!world.isLocked()){

                    world.destroyBody(b2body);

                    BodyDef bdef = new BodyDef();
                    bdef.position.set(32 / Main.ppm, 32 / Main.ppm);
                    bdef.type = BodyDef.BodyType.DynamicBody;
                    b2body = world.createBody(bdef);

                    FixtureDef fdef = new FixtureDef();
                    CircleShape shape = new CircleShape();
                    shape.setRadius(8 / Main.ppm);


                    fdef.shape = shape;
                    b2body.createFixture(fdef);
                    b2body.setTransform(0, 0/Main.ppm , 0);
                    b2body.createFixture(fdef).setUserData(this);

                }

            }
        });

        Hud.addScore(cont); //TODO INTENTAR HACERLO MISMO CON BOOLEANA

    }

    public void enemyLeftCollide(){
        flip(true,false);
        b2body.applyLinearImpulse(1,1,1,1,false);
        b2body.setLinearVelocity(0, b2body.getLinearVelocity().y);

        if (isFlipX()){
            direction = true;
        }
    }

    public void enemyRightCollide(){
        flip(true,false);
        b2body.applyLinearImpulse(1,1,1,1,false);
        b2body.setLinearVelocity(0, b2body.getLinearVelocity().y);
        if (isFlipX()){
            direction = false;
        }
    }


}
