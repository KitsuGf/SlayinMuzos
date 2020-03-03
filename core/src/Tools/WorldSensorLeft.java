package Tools;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.salyin.muzos.Main;

import Screens.PlayScreen;

public class WorldSensorLeft {

    public World world;
    public static Body b2body;
    private static FixtureDef fdef;
    private static BodyDef bdef;


    public WorldSensorLeft(World world, PlayScreen screen) {
        this.world = world;
        defineSensor();

    }

    public void defineSensor(){

        bdef = new BodyDef();
        bdef.position.set(32 / Main.ppm, 32 / Main.ppm);
        bdef.type = BodyDef.BodyType.StaticBody;
        b2body = world.createBody(bdef);

        fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(64/Main.ppm / 2, 64 / Main.ppm /2 );
        fdef.shape = shape;
        b2body.setTransform(2/Main.ppm, 20/Main.ppm , 0);
        b2body.createFixture(fdef).setUserData(this);

    }
}
