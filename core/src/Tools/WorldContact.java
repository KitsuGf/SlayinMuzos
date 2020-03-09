package Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import Scenes.Hud;
import Sprites.EnemyOne;
import Sprites.HeroSword;
import bdd.GameDataBase;


/**
 *
 * @Author Kitsu ( Juan Miguel )
 *
 * //TODO RECUERDA COMENTAR LA CLASE Y SUS METODOS
 *
 */
public class WorldContact implements ContactListener {
    private int count = 0;
    private boolean bol = false;
    //Hit soundeffects
    private Sound enemyHitted = Gdx.audio.newSound(Gdx.files.internal("sounds/effects/slimehitted.wav"));
    private Sound heroHitted = Gdx.audio.newSound(Gdx.files.internal("sounds/effects/herohitted.wav"));
    //Combo soundseffects
    private Sound comboOne = Gdx.audio.newSound(Gdx.files.internal("sounds/combo/MultiKill.wav"));
    private Sound comboTwo = Gdx.audio.newSound(Gdx.files.internal("sounds/combo/MegaKill.wav"));
    private Sound comboThree = Gdx.audio.newSound(Gdx.files.internal("sounds/combo/KillingSpree.wav"));
    private Sound comboFour = Gdx.audio.newSound(Gdx.files.internal("sounds/combo/Rampage.wav"));
    private Sound comboFive = Gdx.audio.newSound(Gdx.files.internal("sounds/combo/MonsterKill.wav"));
    private Sound comboSix = Gdx.audio.newSound(Gdx.files.internal("sounds/combo/Godlike.wav"));
    private Sound comboSeven = Gdx.audio.newSound(Gdx.files.internal("sounds/combo/HolyShit.wav"));
    //Vars neded
    GameDataBase bdd;
    int c = 0;

    //Constructors to get DataBase info.
    public WorldContact(GameDataBase gameDataBase) {
        bdd = gameDataBase;
    }

    //Getter and setter for count
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


        //Detect the collision from Slime body and Hero body
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

            //if hero is hitted play this effect.
            heroHitted.play();
            c++;

            //Method to make "health" simulator to player
            //If the player get 6 hits, die.
            if (c == 6){
                a.heroDie();
            }

            //If the slime collide to hit the Hero,
            //Slime will change the direction and flip the texture.
            b.direction = !b.direction;
            b.flip(true, false);
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


        //Detect collide between slime and wall right
        if (isSword(fixA,fixB)){
            Hud hd = new Hud();
            EnemyOne b;

           if (fixA.getUserData().equals("Sword")){
               b = (EnemyOne) fixB.getUserData();
               fixA.getUserData().equals("Sword");
           }else{
               b = (EnemyOne) fixA.getUserData();
               fixB.getUserData().equals("Sword");
           }

            //if slime is hitted play this effect.
            enemyHitted.play();
           //get the number of hits from the sword to slime
            count++;
            b.enemyHitted(1);

            //Comparison to get the ComboSoundEffects
            //Depends how much slimes kills the user, differents sounds will play.
            if (count == 10 || count >= 250){
                comboOne.play();
            }if (count == 15 || count >= 350 ){
                comboTwo.play();
            }if (count == 20 || count >= 450){
                comboThree.play();
            }if (count == 25 || count >= 550){
                comboFour.play();
            }if (count == 30 || count >= 650){
                comboFive.play();
            }if (count == 35 || count >= 750){
                comboSix.play();
            }if (count == 40 || count >= 950) {
                comboSeven.play();
            }
            //Database save the count of the slimes
            bdd.saveScore(count);
        }

    }

    @Override
    public void endContact(Contact contact) { }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) { }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) { }


    //region Methods to compare Fixdata


    /**
     * @Author Kitsu ( Juan Miguel )
     *
     * This method is maded to compare the collision between slime and the left wall.
     *
     * @param a = Fixture A
     * @param b = Fixture B
     *
     * if some of this fixtures collide:
     * @return True
     * if some of this fixtures not collide
     * @return False
     *
     */

    private Boolean isSlimeLeft(Fixture a, Fixture b){

        if(a.getUserData() instanceof EnemyOne || b.getUserData() instanceof EnemyOne){
            if (a.getUserData() instanceof WorldSensorLeft || b.getUserData() instanceof WorldSensorLeft){
                return true;
            }
        }
        return false;
    }



    /**
     * @Author Kitsu ( Juan Miguel )
     *
     * This method is maded to compare the collision between slime and the right wall
     *
     * @param a = Fixture A
     * @param b = Fixture B
     *
     * if some of this fixtures collide:
     * @return True
     * if some of this fixtures not collide
     * @return False
     *
     */

    private Boolean isSlimeRight(Fixture a, Fixture b){

        if(a.getUserData() instanceof EnemyOne || b.getUserData() instanceof EnemyOne){
            if (a.getUserData() instanceof WorldSensorRight || b.getUserData() instanceof WorldSensorRight){
                return true;
            }
        }

        return false;
    }


    /**
     * @Author Kitsu ( Juan Miguel )
     *
     * This method is maded to compare the collision between Slime and Slime
     *
     * @param a = Fixture A
     * @param b = Fixture B
     *
     * if some of this fixtures collide:
     * @return True
     * if some of this fixtures not collide
     * @return False
     *
     */
    private Boolean isSlimeCollisionSlime(Fixture a, Fixture b){

        if(a.getUserData() instanceof EnemyOne && b.getUserData() instanceof EnemyOne){
            return true;
        }

        return false;
    }

    /**
     * @Author Kitsu ( Juan Miguel )
     *
     * This method is maded to compare the collision between slime and hero body
     *
     * @param a = Fixture A
     * @param b = Fixture B
     *
     * if some of this fixtures collide:
     * @return True
     * if some of this fixtures not collide
     * @return False
     *
     */
    private Boolean isHeroDie(Fixture a, Fixture b){

        if(a.getUserData() instanceof EnemyOne || b.getUserData() instanceof EnemyOne){
            if (a.getUserData() instanceof HeroSword || b.getUserData() instanceof HeroSword){
                return true;
            }
        }

        return false;
    }

    /**
     * @Author Kitsu ( Juan Miguel )
     *
     * This method is maded to compare the collision between slime and hero sword
     *
     * @param a = Fixture A
     * @param b = Fixture B
     *
     * if some of this fixtures collide:
     * @return True
     * if some of this fixtures not collide
     * @return False
     *
     */
    private Boolean isSword(Fixture a, Fixture b){

        if(a.getUserData() instanceof EnemyOne || b.getUserData() instanceof EnemyOne){
            if (a.getUserData().equals("Sword") || b.getUserData().equals("Sword")){
                return true;
            }
        }

        return false;
    }
    //endregion
}
