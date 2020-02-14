package Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.salyin.muzos.Main;

import java.util.Random;

public class EnemyOne extends Sprite{

    protected Sprite sprite;
    public World world;
    public Body b2body;
    Random r = new Random();
    int low = 10;
    int high = 400;
    int random = r.nextInt(high-low) + low;




    public EnemyOne(World world) {
        this.world = world;
        defineHero();
    }

    public void defineHero(){

        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / Main.ppm, 32 / Main.ppm);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(10 / Main.ppm);


        fdef.shape = shape;
        b2body.createFixture(fdef);
        b2body.setTransform(random/Main.ppm, 300/Main.ppm , 0);


    }


}
