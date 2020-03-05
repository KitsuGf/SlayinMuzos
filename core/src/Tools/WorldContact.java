package Tools;


import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import Scenes.Hud;
import Sprites.EnemyOne;
import Sprites.HeroSword;


public class WorldContact implements ContactListener {
    private int count = 0;
    private boolean bol = false;


    public boolean isBol() {
        return bol;
    }

    public void setBol(boolean bol) {
        this.bol = bol;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public void beginContact(Contact contact) {

        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        //Just a make sure if null return.
        if (fixA == null || fixB == null) return;
        if (fixA.getUserData() == null || fixB.getUserData() == null) return;


        //Detect collide Player and Slime on favour to player.
        if (isSlimeContact(fixA,fixB)){
            //Declare a new Hero and Enemy
            HeroSword a;
            EnemyOne b;
            Hud hd = new Hud();
            //Instanciate fixture to enemyOne
            //Test if the enemyOne is fixture A or B and get the
            //Fixture user data from it to EnemyOne and Player.
            if (fixA.getUserData() instanceof EnemyOne){
                b = (EnemyOne) fixA.getUserData();
                a = (HeroSword) fixB.getUserData();
            }else{
                b = (EnemyOne) fixB.getUserData();
                a = (HeroSword) fixA.getUserData();
            }

            //Method in Enemy when the enemy is hitted.
            count++;

            b.enemyHitted(count);

        }



        //Detect collide Player and Slime on favour to player.
        if (isHeroDie(fixA,fixB)){
            //Declare a new Hero and Enemy
            HeroSword a;
            EnemyOne b;

            //Instanciate fixture to enemyOne
            //Test if the enemyOne is fixture A or B and get the
            //Fixture user data from it to EnemyOne and Player.
            if (fixA.getUserData() instanceof HeroSword){
                b = (EnemyOne) fixB.getUserData();
                a = (HeroSword) fixA.getUserData();
            }else{
                b = (EnemyOne) fixA.getUserData();
                a = (HeroSword) fixB.getUserData();
            }

            //Method in Enemy when the enemy is hitted.
            a.heroHitted();


        }



        //Detect collide between slime and wall left
        if (isSlimeLeft(fixA,fixB)){
            WorldSensorLeft a;
            EnemyOne b;

            //Instanciate fixture to enemyOne
            //Test if the enemyOne is fixture A or B and get the
            //Fixture user data from it to EnemyOne and WorldSensorLeft.
            if (fixA.getUserData() instanceof EnemyOne){
                b = (EnemyOne) fixA.getUserData();
                a = (WorldSensorLeft) fixB.getUserData();
            }else{
                b = (EnemyOne) fixB.getUserData();
                a = (WorldSensorLeft) fixA.getUserData();
            }

            b.enemyLeftCollide();


        }

        //Detect collide between slime and wall right
        if (isSlimeRight(fixA,fixB)){
            WorldSensorRight a;
            EnemyOne b;

            //Instanciate fixture to enemyOne
            //Test if the enemyOne is fixture A or B and get the
            //Fixture user data from it to EnemyOne and WorldSensorRight.
            if (fixA.getUserData() instanceof EnemyOne){
                b = (EnemyOne) fixA.getUserData();
                a = (WorldSensorRight) fixB.getUserData();
            }else{
                b = (EnemyOne) fixB.getUserData();
                a = (WorldSensorRight) fixA.getUserData();
            }

            b.enemyRightCollide();

        }



        //Detect collide between Slimes
        if (isSlimeCollisionSlime(fixA,fixB)){
            EnemyOne a;
            EnemyOne b;

            //Instanciate fixture to enemyOne
            //Test if the enemyOne is fixture A or B and get the
            //Fixture user data from it to all Enemies.
            if (fixA.getUserData() instanceof EnemyOne){
                b = (EnemyOne) fixA.getUserData();
                a = (EnemyOne) fixB.getUserData();
            }else{
                b = (EnemyOne) fixB.getUserData();
                a = (EnemyOne) fixA.getUserData();
            }
            //Method to change the direction of the slime when is running to left or
            //right, this make a flip the texture too.
            a.direction = !a.direction;
            a.flip(true, false);
            a.b2body.applyLinearImpulse(1,1,1,1,false);
            b.direction = !b.direction;
            b.flip(true, false);

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

    private Boolean isSlimeLeft(Fixture a, Fixture b){

        if(a.getUserData() instanceof EnemyOne || b.getUserData() instanceof EnemyOne){
            if (a.getUserData() instanceof WorldSensorLeft || b.getUserData() instanceof WorldSensorLeft){
                return true;
            }
        }

        return false;
    }


    private Boolean isSlimeRight(Fixture a, Fixture b){

        if(a.getUserData() instanceof EnemyOne || b.getUserData() instanceof EnemyOne){
            if (a.getUserData() instanceof WorldSensorRight || b.getUserData() instanceof WorldSensorRight){
                return true;
            }
        }

        return false;
    }

    private Boolean isSlimeCollisionSlime(Fixture a, Fixture b){

        if(a.getUserData() instanceof EnemyOne && b.getUserData() instanceof EnemyOne){
            return true;
        }

        return false;
    }

    private Boolean isHeroDie(Fixture a, Fixture b){

        if(a.getUserData() instanceof EnemyOne || b.getUserData() instanceof EnemyOne){
            if (a.getUserData() instanceof HeroSword || b.getUserData() instanceof HeroSword){
                return true;
            }
        }

        return false;
    }

}
