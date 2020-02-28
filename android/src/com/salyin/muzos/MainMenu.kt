package com.salyin.muzos

import android.content.Intent
import android.os.Bundle
import android.os.Process
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View

class MainMenu : AppCompatActivity() {
    private val manager : FragmentManager by lazy {this.supportFragmentManager}
    private val menuTu : MenuTutorial by lazy { MenuTutorial()}
    private var closeFrag : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
    }

    fun goPlay(view: View?) {
        val i = Intent(this, AndroidLauncher::class.java)
        this.startActivity(i)
    }

    override fun onBackPressed() { //TODO CAMBIAR LOS STRINGS POR RESOURCES
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Salir del juego")
        builder.setMessage("¿Estás seguro de que quieres salir del juego?").setPositiveButton("Si") { dialog, id ->
            Process.killProcess(Process.myPid())
            System.exit(1)
        }.setNegativeButton("No") { dialog, id ->
            // User cancelled the dialog
        }
        builder.create()
        builder.show()
    }

    fun goTutorial(view: View?) {

        var transaction: FragmentTransaction =manager.beginTransaction()
        //Method with if to open and close the tutorial with the same button.
        //If closeFrag is flase remove the Fragment.
        if (closeFrag){
            transaction.remove(menuTu)
        }else{
            //if closeFrag is true, create fragment again.
            transaction.replace(R.id.frameMenuTuto,menuTu,"menu_tutorial")
            transaction.addToBackStack("menu_tutorial")
        }
        closeFrag = !closeFrag
        transaction.commit()

    }
}