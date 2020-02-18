package Sprites;

/**
 *
 * @Author Kitsu ( Juan Miguel )
 *
 * //TODO RECUERDA COMENTAR LA CLASE Y SUS METODOS
 *
 *
 */

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.salyin.muzos.Main;


public class HeroSword extends Sprite {
    protected Sprite sprite;
    public World world;
    public static Body b2body;
    private static EdgeShape cab;
    private static FixtureDef fdef;
    private static BodyDef bdef;


    public HeroSword(World world) {
        this.world = world;
        defineHero();
    }

    public void defineHero(){

        bdef = new BodyDef();
        bdef.position.set(32 / Main.ppm, 32 / Main.ppm);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / Main.ppm);

        fdef.shape = shape;
        b2body.createFixture(fdef);
        cab = new EdgeShape();


    }

    public static void changeDirection(boolean bool){
        if (bool){
            cab.set(new Vector2(0 / Main.ppm, 0 / Main.ppm), new Vector2(20 / Main.ppm, 0 / Main.ppm));
            fdef.shape = cab;
            fdef.isSensor = true;

            for (int i = 1; i < b2body.getFixtureList().size; i++){
                b2body.destroyFixture(b2body.getFixtureList().get(i));
            }
            b2body.createFixture(fdef).setUserData("Sword");

        } else {
            cab.set(new Vector2(-20 / Main.ppm, 0 / Main.ppm), new Vector2(0 / Main.ppm, 0 / Main.ppm));
            fdef.shape = cab;
            fdef.isSensor = true;

            for (int i = 1; i < b2body.getFixtureList().size; i++){
                b2body.destroyFixture(b2body.getFixtureList().get(i));
            }
                b2body.createFixture(fdef).setUserData("Sword");
            }
        }



}