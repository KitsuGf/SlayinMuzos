package com.salyin.muzos

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import bdd.AndroidDataBase
import service.MyService

/**
 * @Author Kitsu ( Juan Miguel )
 *
 * Main Menu from Android what have all the methods to communicate with Core
 *
 */
class MainMenu : AppCompatActivity() {

    private val manager : FragmentManager by lazy {this.supportFragmentManager} //Define FragmentManager
    private val menuTu : MenuTutorial by lazy { MenuTutorial()} //Define MenuTutorial
    private val menuGame : GameMode by lazy { GameMode()}  //Define MenuGame
    private val menuScore : ScoreMenu by lazy { ScoreMenu()} //Define ScoreMenu
    private var closeFrag : Boolean = false //Define ClooseFragment a boolean to close the fragment Tutorial
    private var nSlimes : Int = 100 //Define the gameMode to normal and put 100 slimes on it
    private var nSlimesMadness : Int = 1000 //Define the gameMode to Madness and put 1000 slimes on it
    private lateinit var mediaPlayer : MediaPlayer //Define the mediaPlayer to music game
    private val base : AndroidDataBase by lazy { AndroidDataBase(this) } //Define database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        //Fragment of the game buttons.
        var transaction: FragmentTransaction =manager.beginTransaction()
        transaction.replace(R.id.frameMenuTuto,menuGame,"menu_game")
        transaction.addToBackStack("menu_game")
        transaction.commit()

        //Intent for the Service
        var intent : Intent = Intent(this, MyService::class.java)
        startService(intent)
        stopService(intent)



    }
    /**
     * @Author Kitsu ( Juan Miguel )
     *
     *Overray the onBackPressed to get alert
     *
     */
    override fun onBackPressed() {
        //Variables to get string from resfile.
        var title : String = this.getString(R.string.alertTitle)
        var msg : String = this.getString(R.string.alertMsg)
        var yes : String = this.getString(R.string.yes)
        var no : String = this.getString(R.string.no)

        //Create Alert
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title) //Make the Title of the alert
        //Make the message from the alert create two options
        //Option yes is an exit of the app
        builder.setMessage(msg).setPositiveButton(yes) {
            dialog, id -> finish()  //Process.killProcess(Process.myPid()) <- This is one method i want to ask my teacher.
            System.exit(1)
        //Set the negative option making nothing, this just clear the alert.
        }.setNegativeButton(no) { dialog, id -> }
        //Create the alert finally
        builder.create()
        //Show the alert
        builder.show()
    }

    /**
     * @Author Kitsu ( Juan Miguel )
     *
     * this fun get the onClick of layout tutorial and
     * go to tutorial fragment.
     *
     * If you click again you, the fragment is closed.
     *
     */
    fun goTutorial(view: View?) {
        var tutAni : Button = findViewById(R.id.btTuto)
        val animation = AnimationUtils.loadAnimation(this, R.anim.anim)
        tutAni.startAnimation(animation)

        var transaction: FragmentTransaction =manager.beginTransaction()
        //Method with if to open and close the tutorial with the same button.
        if (closeFrag){
            var transaction: FragmentTransaction =manager.beginTransaction()
            transaction.replace(R.id.frameMenuTuto,menuGame,"menu_game")
            transaction.addToBackStack("menu_game")
            transaction.commit()
        }else{
            //if closeFrag is true, create fragment again.
            transaction.replace(R.id.frameMenuTuto,menuTu,"menu_tutorial")
            transaction.addToBackStack("menu_tutorial")
        }
        //set the boolean again false to clear the boolean and
        // always get false at the start of the click
        closeFrag = !closeFrag
        //Commit the transaction of the fragment to show it
        transaction.commit()
    }

    /**
     * @Author Kitsu ( Juan Miguel )
     *
     *
     * Set the mode with a intent and bundle with extras
     * to the game.
     *
     * in this case is 100
     *
     */
    fun normalMode(view: View) {

        //Little button animation
        var tutAni : Button = findViewById(R.id.btNormal)
        val animation = AnimationUtils.loadAnimation(this, R.anim.anim)
        tutAni.startAnimation(animation)
        //Send the intent with the extras to the launcher
        val i = Intent(this, AndroidLauncher::class.java)
        var bundle: Bundle = Bundle()
        bundle.putInt("nSlime", nSlimes)
        i.putExtras(bundle)
        this.startActivity(i)
    }

    /**
     * @Author Kitsu ( Juan Miguel )
     *
     *
     * Set the mode with a intent and bundle with extras
     * to the game.
     *
     * in this case is 1000
     *
     */
    fun madnessMode(view: View) {

        //Little button animation
        var tutAni : Button = findViewById(R.id.btMadness)
        val animation = AnimationUtils.loadAnimation(this, R.anim.anim)
        tutAni.startAnimation(animation)
        //Send the intent with the extras to the launcher
        val i = Intent(this, AndroidLauncher::class.java)
        var bundle: Bundle = Bundle()
        bundle.putInt("nSlime", nSlimesMadness)
        i.putExtras(bundle)
        this.startActivity(i)

    }

    /**
     * @Author Kitsu ( Juan Miguel )
     *
     *
     * On click to the fragment leaderScore
     *
     */
    fun leaderScore(view: View) {
        var tutAni : Button = findViewById(R.id.btLeader)
        val animation = AnimationUtils.loadAnimation(this, R.anim.anim)
        tutAni.startAnimation(animation)

        var transaction: FragmentTransaction =manager.beginTransaction()
        transaction.replace(R.id.frameMenuTuto,menuScore,"menu_score")
        transaction.addToBackStack("menu_score")
        transaction.commit()
    }

    /**
     * @Author Kitsu ( Juan Miguel )
     *
     *
     * Button back if you open the leaderscore in the fragment.
     *
     */
    fun backMenu(view: View) {
        var tutAni : Button = findViewById(R.id.btBack)
        val animation = AnimationUtils.loadAnimation(this, R.anim.anim)
        tutAni.startAnimation(animation)
        //Fragment of the game buttons.
        var transaction: FragmentTransaction =manager.beginTransaction()
        transaction.replace(R.id.frameMenuTuto,menuGame,"menu_game")
        transaction.addToBackStack("menu_game")
        transaction.commit()

    }

    /**
     * @Author Kitsu ( Juan Miguel )
     *
     * I added here music stop and release to
     * stop it when app is not on second plane
     *
     */
    override fun onPause() {
        super.onPause()
        mediaPlayer.stop()
        mediaPlayer.release()
    }

    /**
     * @Author Kitsu ( Juan Miguel )
     *
     * And here restart the song
     * if the app go to second plane and comeback.
     *
     */
    override fun onResume() {
        super.onResume()
        mediaPlayer = MediaPlayer.create(this, R.raw.title)
        mediaPlayer.start()

    }



}