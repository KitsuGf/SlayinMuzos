package Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.salyin.muzos.Main;

import java.util.Iterator;
import java.util.Random;

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
    public int enemyIndex;




    public EnemyOne(World world, PlayScreen screen, int index) {
        super(screen.getEnemyOneAtlas().findRegion("sl"));
        enemyStand = new TextureRegion(getTexture(), 0,6,32,25);
        setBounds(0,0,42/Main.ppm, 35/Main.ppm);
        setRegion(enemyStand);
        this.world = world;
        this.enemyIndex = index;
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

    public void enemyHitted(){

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
