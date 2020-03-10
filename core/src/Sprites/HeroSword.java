package Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Bits;
import com.salyin.muzos.Main;

import javax.xml.soap.Text;

import Screens.PlayScreen;

/**
 * @Author Kitsu ( Juan Miguel )
 *
 * HeroSword is class extending the Sprite to create parameters,
 * texture, collision filter, methods from each enemy the game create.
 *
 */
public class HeroSword extends Sprite {

    //variables
    public World world;
    public static Body b2body;
    private static EdgeShape sword;
    private static FixtureDef fdef;
    private static BodyDef bdef;
    private TextureRegion heroStand;

    //Cosntructor getting world and playscreen
    public HeroSword(World world, PlayScreen screen) {
        //this supper get the Hero atlas region.
        super(screen.getHeroSwordAtlas().findRegion("viking"));
        //set the texture region to set it in body
        this.world = world;
        //call to the define enemy method.
        defineHero();
        heroStand = new TextureRegion(getTexture(), -19,31,128,128);
        //bounds to body of the enemy.
        setBounds(0,0,70/Main.ppm, 64/Main.ppm);
        setRegion(heroStand);
    }


    /**
     * @Author Kitsu ( Juan Miguel )
     *
     * defineHero is a Method to create Hero shape, texture, position
     * collision filter.
     *
     */
    public void defineHero(){
        //define the body and set the position
        bdef = new BodyDef();
        bdef.position.set(32 / Main.ppm, 32 / Main.ppm);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        //def the type of fixture and the radius
        fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(8 / Main.ppm);
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);


        //Create a SwordShape to be a part of the Hero Body
        sword = new EdgeShape();

    }


    //Update with deltatime the position of the sprite.
    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);

    }

    /**
     * @Author Kitsu ( Juan Miguel )
     *
     * changeDirection is a Method to create Enemy shape, texture, position
     * collision filter.
     *
     * @return boolean
     */
    public static boolean changeDirection(boolean bool){

        //if bool is true change the direction of the edgeShape Sword
        //this destroy and recreate the sword to another position
        if (bool){
            sword.set(new Vector2(0 / Main.ppm, 0 / Main.ppm), new Vector2(22 / Main.ppm, 0 / Main.ppm));
            fdef.shape = sword;
            fdef.isSensor = false;
            for (int i = 1; i < b2body.getFixtureList().size; i++){
                b2body.destroyFixture(b2body.getFixtureList().get(i));
            }
            b2body.createFixture(fdef).setUserData("Sword");

        } else {
            sword.set(new Vector2(-22 / Main.ppm, 0 / Main.ppm), new Vector2(0 / Main.ppm, 0 / Main.ppm));
            fdef.shape = sword;
            fdef.isSensor = false;

            for (int i = 1; i < b2body.getFixtureList().size; i++){
                b2body.destroyFixture(b2body.getFixtureList().get(i));
            }
                b2body.createFixture(fdef).setUserData("Sword");
            }
        return bool;
    }


    /**
     * @Author Kitsu ( Juan Miguel )
     *
     * enemyHitted is a method to simulate the destroy of the enemy.
     * in fact is not destroy any fixture or body, is just destroy and recreate.
     * because i can´t control good the destroy bodies
     *
     */
    public void heroDie(){

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


    }


}