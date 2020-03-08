package com.salyin.muzos

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import bdd.AndroidDataBase


class ScoreMenu : Fragment() {
    private val base : AndroidDataBase by lazy { AndroidDataBase(context) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        val list = arrayListOf<String>(base.allScore().toString().replace("[","Player: ").replace("]",""))
        var view : View =inflater.inflate(R.layout.leader_layout,container,false)

        var lista : ListView = view.findViewById(R.id.listScore)
        var score = arrayOf("Nosencuantos","Nosencuantos","Nosencuantos","Nosencuantos","Nosencuantos")
        var ad: ArrayAdapter<String> = ArrayAdapter<String>(activity!!, android.R.layout.simple_list_item_1, list)
        lista.adapter = ad

        return view

    }



}