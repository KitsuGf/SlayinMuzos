package Tools;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import Sprites.EnemyOne;
import Sprites.HeroSword;
import Sprites.ObjectInteract;

public class WorldContact implements ContactListener {
    private int count = 0;
    private boolean bol = false;

    public boolean isBol() {
        return bol;
    }

    public void setBol(boolean bol) {
        this.bol = bol;
    }

    @Override
    public void beginContact(Contact contact) {
        //Gdx.app.log("Esta colisionando?" , "no");
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        if (fixA == null || fixB == null) return;
        if (fixA.getUserData() == null || fixB.getUserData() == null) return;



        if (isSlimeContact(fixA,fixB)){
            HeroSword a;
            EnemyOne b;

            if (fixA.getUserData() instanceof EnemyOne){
                b = (EnemyOne) fixA.getUserData();
                a = (HeroSword) fixB.getUserData();
            }else{
                b = (EnemyOne) fixB.getUserData();
                a = (HeroSword) fixA.getUserData();
            }

            b.enemyHitted();
            b.enemyFly();


        }


        if (isSlimeLeftt(fixA,fixB)){
            WorldSensorLeft a;
            EnemyOne b;

            if (fixA.getUserData() instanceof EnemyOne){
                b = (EnemyOne) fixA.getUserData();
                a = (WorldSensorLeft) fixB.getUserData();
            }else{
                b = (EnemyOne) fixB.getUserData();
                a = (WorldSensorLeft) fixA.getUserData();
            }

            b.enemyFly();


        }


    }

    @Override
    public void endContact(Contact contact) {


    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    private Boolean isSlimeContact(Fixture a, Fixture b){

        if(a.getUserData() instanceof EnemyOne || b.getUserData() instanceof EnemyOne){
            if (a.getUserData() instanceof HeroSword || b.getUserData() instanceof HeroSword){
                return true;
            }
        }

        return false;
    }

    private Boolean isSlimeLeftt(Fixture a, Fixture b){

        if(a.getUserData() instanceof EnemyOne || b.getUserData() instanceof EnemyOne){
            if (a.getUserData() instanceof WorldSensorLeft || b.getUserData() instanceof WorldSensorLeft){
                return true;
            }
        }

        return false;
    }


}
