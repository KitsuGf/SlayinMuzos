package com.salyin.muzos

import android.content.Intent
import android.os.Bundle
import android.os.Process
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import com.badlogic.gdx.Game
import kotlinx.android.synthetic.main.activity_main_menu.*

class MainMenu : AppCompatActivity() {
    private val manager : FragmentManager by lazy {this.supportFragmentManager}
    private val menuTu : MenuTutorial by lazy { MenuTutorial()}
    private val menuGame : GameMode by lazy { GameMode()}


    private var closeFrag : Boolean = false
    private var nSlimes : Int = 20
    private var nSlimesMadness : Int = 5000000
    private lateinit var btGame : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
    }

    //This Method is a button function,
    //what send the user to game normal mode
    fun goPlay(view: View?) {

        var transaction: FragmentTransaction =manager.beginTransaction()
        //Method with if to open and close the tutorial with the same button.
        //If closeFrag is flase remove the Fragment.
        if (closeFrag){
            transaction.remove(menuGame) // Remove the frag
        }else{
            //if closeFrag is true, create fragment again.
            transaction.replace(R.id.frameGameMode,menuGame,"menu_game")
            transaction.addToBackStack("menu_game")
        }
        //set the boolean again false to clear the boolean and
        // always get false at the start of the click
        closeFrag = !closeFrag
        //Commit the transaction of the fragment to show it
        transaction.commit()
    }
    //This Method is a button function,
    //what send the user to game madness mode


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

    fun goTutorial(view: View?) {
        //res to GameModeButton
        btGame = findViewById(R.id.btGameMode)
        //This make the GameModeButton GONE when
        //TutorialButton is clicked.
        btGame.visibility = View.GONE


        var transaction: FragmentTransaction =manager.beginTransaction()
        //Method with if to open and close the tutorial with the same button.
        //If closeFrag is flase remove the Fragment.
        if (closeFrag){
            transaction.remove(menuTu) // Remove the frag
            btGame.visibility = View.VISIBLE // Make the GameModeButton visible again.
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

    //Method what send the nSlimes normal as 20 to the game.
    fun normalMode(view: View) {

        val i = Intent(this, AndroidLauncher::class.java)
        var bundle: Bundle = Bundle()
        bundle.putInt("nSlime", nSlimes)
        i.putExtras(bundle)
        this.startActivity(i)
    }

    //Method what send the nSlimes normal as  5000000 to the game.
    fun madnessMode(view: View) {

        val i = Intent(this, AndroidLauncher::class.java)
        var bundle: Bundle = Bundle()
        bundle.putInt("nSlime", nSlimesMadness)
        i.putExtras(bundle)
        this.startActivity(i)

    }


}