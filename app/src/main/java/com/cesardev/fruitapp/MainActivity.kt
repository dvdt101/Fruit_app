package com.cesardev.fruitapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recycle_view : RecyclerView = findViewById(R.id.rview)
        val initalList = generateDummyList()

        recycle_view.adapter = FruitAdapter(initalList)
        recycle_view.layoutManager = LinearLayoutManager(this)
        recycle_view.setHasFixedSize(true)
    }

    private fun generateDummyList() : List<Fruit>{
        val list = ArrayList<Fruit>()
        list.add(Fruit("Marça", "Marça descrição", R.drawable.marca))
        list.add(Fruit("Uva", "Uva descrição", R.drawable.uvas))
        list.add(Fruit("Morango", "Morango descrição", R.drawable.morango))
        return list
    }

}