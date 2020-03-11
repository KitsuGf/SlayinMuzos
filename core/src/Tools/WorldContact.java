package Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.salyin.muzos.Main;

import Scenes.Hud;
import Screens.PlayScreen;
import Sprites.EnemyOne;
import Sprites.HeroSword;
import bdd.GameDataBase;


/**
 *
 * @Author Kitsu ( Juan Miguel )
 *
 * WoldContact is a class to check collisions betweent objects/bodys in game.
 *
 */
public class WorldContact implements ContactListener {
    public static int count = 0;
    public boolean bol;
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


    public static void setCount(int count) {
        WorldContact.count = count;
    }

    //Constructors to get DataBase info.
    public WorldContact(GameDataBase gameDataBase) {

        bdd = gameDataBase;
        this.bol = bol;
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
            PlayScreen ps = new PlayScreen();
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
                PlayScreen.setGameOver(true); //set true to set the GameOverScreen
                c = 0; //set the counter again to 0
                //count = 0; //reset the counter of the enemy hitted
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
            this.count++;
            b.enemyHitted(1);

            //Comparison to get the ComboSoundEffects
            //Depends how much slimes kills the user, differents sounds will play.
            if (count == 15 || count == 100 || count == 150){
                comboOne.play();
            }if (count == 30 || count == 300 || count == 350 ){
                comboTwo.play();
            }if (count == 45 || count == 400 || count == 450){
                comboThree.play();
            }if (count == 60 || count == 500 || count == 550){
                comboFour.play();
            }if (count == 75 || count == 600 || count == 650){
                comboFive.play();
            }if (count == 90 || count == 700 || count == 750){
                comboSix.play();
            }if (count == 100 || count == 900 || count == 989) {
                comboSeven.play();

            }
            //if count equals 100 then save the score 100 to database
            if (count == 100){
                PlayScreen.setWinner(true); //set the winner screent
                bdd.saveScore(100); //set the final score
                count = 0; //reset the counter
                c = 0; //reset the counter of the hero hitted
            }
            //if count equals 1000 in madness mode save the score to 1000 to database
            if (count == 1000){
                PlayScreen.setWinner(true);//set the winner screent
                bdd.saveScore(1000);//set the final score
                count = 0;//reset the counter of the slimes hitted
                c = 0;//reset the counter of the hero hitted
            }



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
