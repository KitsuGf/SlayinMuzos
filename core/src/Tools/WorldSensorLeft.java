package Tools;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.salyin.muzos.Main;
import Screens.PlayScreen;



/**
 *
 * @Author Kitsu ( Juan Miguel )
 *
 *  WorldSensorLeft make a sensor collision to the Left wall
 *  thats make the enemy check the colission in WorldContact and change direction.
 *
 */
public class WorldSensorLeft {

    public World world; //Define World
    public static Body b2body; //Define Body
    private static FixtureDef fdef; //Define FixtureDef
    private static BodyDef bdef; //Define BodyDef

    //Constructor of WorldSensorLeft getting from parameters World and Screen
    public WorldSensorLeft(World world, PlayScreen screen) {
        this.world = world;
        defineSensor();

    }

    /**
     *
     * @Author Kitsu ( Juan Miguel )
     *
     *  Method to define the sensor in constructor
     *
     */
    public void defineSensor(){
        //Create a new BodyDef
        bdef = new BodyDef();
        //Set the position of the new bodydef
        bdef.position.set(32 / Main.ppm, 32 / Main.ppm);
        //Set the type of the bodydef
        bdef.type = BodyDef.BodyType.StaticBody;
        //set the world where created the bodyDef
        b2body = world.createBody(bdef);

        //Create a new Fixture to get box2D physics
        fdef = new FixtureDef();
        //define it as PolygonShape
        PolygonShape shape = new PolygonShape();
        //Set ass Box and set the size / in PPM
        shape.setAsBox(64/Main.ppm / 2, 500 / Main.ppm /2 );
        //set the shape to fdef shape.
        fdef.shape = shape;
        //set the position of the body
        b2body.setTransform(2/Main.ppm, 100/Main.ppm , 0);
        //set the filter of the collision
        b2body.createFixture(fdef).setUserData(this);

    }
}
