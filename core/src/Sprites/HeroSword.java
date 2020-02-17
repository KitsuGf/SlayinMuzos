package Sprites;

/**
 *
 * @Author Kitsu ( Juan Miguel )
 *
 * //TODO RECUERDA COMENTAR LA CLASE Y SUS METODOS
 *
 *
 */

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.salyin.muzos.Main;

import java.util.Random;

import Screens.PlayScreen;

public class HeroSword extends Sprite {
    protected Sprite sprite;
    public World world;
    public Body b2body;
    private PlayScreen ps;
    private Boolean b;



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
        shape.setRadius(6 / Main.ppm);

        fdef.shape = shape;
        b2body.createFixture(fdef);
        EdgeShape cab = new EdgeShape();

        ps = new PlayScreen(b); //TODO PREGUNTALE A MIGUEL ANDA


        /*cab.set(new Vector2(-20 / Main.ppm, 0 / Main.ppm), new Vector2(20 / Main.ppm, 0 / Main.ppm));
        fdef.shape = cab;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData("Cabeza");*/


        //TODO RECUERDA ARREGLAR ESTO

        if (ps.getDerecha() == b){
            cab.set(new Vector2(0 / Main.ppm, 0 / Main.ppm), new Vector2(20 / Main.ppm, 0 / Main.ppm));
            fdef.shape = cab;
            fdef.isSensor = true;
            b2body.createFixture(fdef).setUserData("Cabeza");


        }

        if (ps.getDerecha() != b){
            cab.set(new Vector2(-20 / Main.ppm, 0 / Main.ppm), new Vector2(0 / Main.ppm, 0 / Main.ppm));
            fdef.shape = cab;
            fdef.isSensor = true;
            b2body.createFixture(fdef).setUserData("Cabeza");

        }


    }





}