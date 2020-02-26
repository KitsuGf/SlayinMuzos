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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.salyin.muzos.Main;

import javax.xml.soap.Text;

import Screens.PlayScreen;


public class HeroSword extends Sprite {
    protected Sprite sprite;
    public World world;
    public static Body b2body;
    private static EdgeShape cab;
    private static FixtureDef fdef;
    private static BodyDef bdef;
    private TextureRegion heroStand;


    public HeroSword(World world, PlayScreen screen) {
        super(screen.getHeroSwordAtlas().findRegion("little_mario"));
        this.world = world;
        defineHero();
        heroStand = new TextureRegion(getTexture(), 1,11,16,16);
        setBounds(0,0,16 /Main.ppm, 16/Main.ppm);
        setRegion(heroStand);
    }

    public void defineHero(){

        bdef = new BodyDef();
        bdef.position.set(32 / Main.ppm, 32 / Main.ppm);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5 / Main.ppm);

        fdef.shape = shape;
        b2body.createFixture(fdef);
        cab = new EdgeShape();


    }


    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
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