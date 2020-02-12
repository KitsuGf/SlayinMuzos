package heros;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class HeroSword {
    protected Sprite sprite;
    private World mnd;
    private BodyDef bodyProper;
    private Body body;
    private FixtureDef bodyPhisics;

    public HeroSword(World m) {

        mnd = m;
        sprite = new Sprite(new Texture("sprites/hero_sword/hero_sword.png"));
        int xSprite = 1;
        int ySprite = 1;
        sprite.setBounds(3,8 ,xSprite, ySprite);

        this.bodyProper = new BodyDef();
        bodyProper.type = BodyDef.BodyType.DynamicBody;
        bodyProper.position.set(sprite.getX(), sprite.getY());

        body = mnd.createBody(bodyProper);

        bodyPhisics = new FixtureDef();
        bodyPhisics.shape = new PolygonShape();
        ((PolygonShape)bodyPhisics.shape).setAsBox(xSprite/2f, ySprite/2f);
        bodyPhisics.density = 1f;
        body.createFixture(bodyPhisics);

        sprite.setOrigin(this.sprite.getWidth()/2, this.sprite.getHeight()/2);

    }


    public void draw(Batch batch, float parentAlph){

        sprite.setPosition((body.getPosition().x-sprite.getWidth())/2, body.getPosition().y-sprite.getHeight()/2);
        sprite.setRotation(MathUtils.radiansToDegrees*body.getAngle());
        sprite.draw(batch);

    }


}