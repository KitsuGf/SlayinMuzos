package Sprites;

/**
 *
 * @Author Kitsu ( Juan Miguel )
 *
 * //TODO RECUERDA COMENTAR LA CLASE Y SUS METODOS
 *
 */

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.salyin.muzos.Main;

public class HeroSword extends Sprite {
    protected Sprite sprite;
    public World world;
    public Body b2body;


    public HeroSword(World world) {
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
        shape.setRadius(15 / Main.ppm);

        fdef.shape = shape;
        b2body.createFixture(fdef);



    }

}