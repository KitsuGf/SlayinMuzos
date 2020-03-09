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


/**
 * @Author Kitsu ( Juan Miguel )
 *
 * EnemyOne is class extending the Sprite to create parameters,
 * texture, collision filter, methods from each enemy the game create.
 *
 */
public class EnemyOne extends Sprite{

    //variables
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

    //Cosntructor getting world and playscreen
    public EnemyOne(World world, PlayScreen screen) {
        //this supper get the enemy atlas region.
        super(screen.getEnemyOneAtlas().findRegion("sl"));
        //set the texture region to set it in body
        enemyStand = new TextureRegion(getTexture(), 0,6,32,25);
        //bounds to body of the enemy.
        setBounds(-2,0,42/Main.ppm, 35/Main.ppm);
        setRegion(enemyStand);
        this.world = world;
        //call to the define enemy method.
        defineEnemy();

    }


    //Update with deltatime the position of the sprite.
    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);

    }

    /**
     * @Author Kitsu ( Juan Miguel )
     *
     * defineEnemy is a Method to create Enemy shape, texture, position
     * collision filter.
     *
     */

    public void defineEnemy(){
        //define the body and set the position
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / Main.ppm, 32 / Main.ppm);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        //def the type of fixture and the radius
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(8 / Main.ppm);

        //create the fixture and put in random position between 10 and 500.
        fdef.shape = shape;
        b2body.createFixture(fdef);
        b2body.setTransform(random/Main.ppm, 300/Main.ppm , 0);
        //set the fixture filtert to detect if is colliding.
        b2body.createFixture(fdef).setUserData(this);
    }


    /**
     * @Author Kitsu ( Juan Miguel )
     *
     * enemyHitted is a method to simulate the destroy of the enemy.
     * in fact is not destroy any fixture or body, is just destroy and recreate.
     * because i can´t controll good the destroy bodys
     *
     */
    public void enemyHitted(int cont){
        //with runnable i can destroy bodies but some weird
        //things happens if i dont create again other body,
        //so my solution at this is simulate the destroy body crete again other in
        //different location not appearing in screen.
        Gdx.app.postRunnable(new Runnable(){

            public void run(){
                //if the world is not locked, destroy the body an create again
                //other
                if (!world.isLocked()){
                    //Here is the destroy body i can´t controll.
                    world.destroyBody(b2body);

                    //recreate the body out of the map.
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

    /**
     * @Author Kitsu ( Juan Miguel )
     *
     * enemyLeftCollide is a method called in WorldContact
     * enemyLeftCollide is only called if the enemy collision with leftWall
     *
     */
    public void enemyLeftCollide(){
        //If collision is true, flip the texture and get impulse in the
        //contrary directiopn.
        flip(true,false);
        //do a litle jump
        b2body.applyLinearImpulse(1,1,1,1,false);
        b2body.setLinearVelocity(0, b2body.getLinearVelocity().y);
        //if is fliped = true then direction is true too and pass the parameter to WorldContact
        if (isFlipX()){
            direction = true;
        }
    }

    /**
     * @Author Kitsu ( Juan Miguel )
     *
     * enemyRightCollide is a method called in WorldContact
     * enemyRightCollide is only called if the enemy collision with RightWall
     *
     */
    public void enemyRightCollide(){
        //If collision is true, flip the texture and get impulse in the
        //contrary directiopn.
        flip(true,false);
        //do a litle jump
        b2body.applyLinearImpulse(1,1,1,1,false);
        //if is fliped = true then direction is true too and pass the parameter to WorldContact
        b2body.setLinearVelocity(0, b2body.getLinearVelocity().y);
        if (isFlipX()){
            direction = false;
        }
    }


}
