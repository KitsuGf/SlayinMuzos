package com.salyin.muzos

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import bdd.AndroidDataBase
import kotlin.collections.ArrayList

/**
 * @Author Kitsu ( Juan Miguel )
 *
 * Fragment with List of the score resul from database.
 *
 */
class ScoreMenu : Fragment() {
    //private val from database
    private val base : AndroidDataBase by lazy { AndroidDataBase(context) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        //list getting arraystring from database.
        //ArrayList of ints
        val list: ArrayList<Int> = ArrayList()
        //List of Strings
        val listStr : ArrayList<String> = ArrayList()
        //For to Iterate in the in the Int list
        for (i in 0..base.allScore().size){
            try{
                //iterate for database all Score getting all the info.
                list.add(Integer.parseInt(base.allScore().get(i).toString()))
            } catch (e : IndexOutOfBoundsException){ }
        }
        //sorted it to get ordered the list.
        list.sort()
        //reverse it because dont know why dont sort by descending
        list.reverse()
        //iterate from the database all score
        for (i in 0..base.allScore().size){
            try{
                //add now the data from list to the list will show in adapter.
                listStr.add("SliMuzos Killed: " + list.get(i).toString())
            } catch (e : IndexOutOfBoundsException){ }
        }


        //create a view from the layout so i can put programatically text in fragment
        var view : View =inflater.inflate(R.layout.leader_layout,container,false) //<- Thanks StackOverFlow
        //listview from fragment
        var lista : ListView = view.findViewById(R.id.listScore)
        //arrayadapter to show the scoreList in the fragment.
        //Sometimes give an error but never crash. "ArrayAdapter" get red with warning but always works.
        // I dont know why this happens sometimes but it works always.
        var ad: ArrayAdapter<String> = ArrayAdapter<String>(activity!!, android.R.layout.simple_list_item_1, listStr)
        //add the list to adapter.
        lista.adapter = ad
        //return view to see the result of the score.
        return view

    }



}