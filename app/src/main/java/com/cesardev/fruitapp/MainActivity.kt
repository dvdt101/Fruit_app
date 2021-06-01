package com.cesardev.fruitapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity(), FruitAdapter.OnItemClickListenner {

    companion object{
        const val DETAILS_ACTIVITY_FRUIT_EXTRA_ID = "Fruit_details"
        const val OUT_STATE_FRUIT_ARRAY_EXTRA_ID = "FRUIT_STATE"

    }


    private var fruitList:ArrayList<Fruit>? = null
    private var adapter:FruitAdapter? = null
    private var selectedPosition:Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            val saved = savedInstanceState
                    .getParcelableArrayList<Fruit>(OUT_STATE_FRUIT_ARRAY_EXTRA_ID)
            fruitList = saved as ArrayList<Fruit>

        }else{
            fruitList = dummyList()
        }

        val recycle_view : RecyclerView = findViewById(R.id.rview)
        adapter = FruitAdapter(fruitList!!, this)
        recycle_view.adapter = adapter
        recycle_view.layoutManager = LinearLayoutManager(this)
        recycle_view.setHasFixedSize(true)


    }
        //Chama SecondActivity para adicionar o item
    fun insertItem(v: View){
        val intent : Intent =  Intent(this, SecondActivity::class.java)
        startActivityForResult(intent, 1)
    }

        //Cria lista inicial
    private fun dummyList() : ArrayList<Fruit>{
        val list = ArrayList<Fruit>()

        list.add(Fruit(resources.getString(R.string.dummy_apple),
                resources.getString(R.string.dummy_apple_benefits),
               R.drawable.maca, null))

        list.add(Fruit(resources.getString(R.string.dummy_gapes),
                resources.getString(R.string.dummy_gapes_benefits),
                R.drawable.uvas, null))

        list.add(Fruit(resources.getString(R.string.dummy_strawberry),
                resources.getString(R.string.dummy_strawberry_benefits)
                , R.drawable.morango, null))

        return list
    }
        // Chama DetailActivity ao clicar no item
    override fun onItemClick(position: Int) {

        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(DETAILS_ACTIVITY_FRUIT_EXTRA_ID, fruitList!![position])
        selectedPosition = position
        startActivityForResult(intent, 2)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 1 && resultCode == Activity.RESULT_OK){
            val newItem = data?.getParcelableExtra<Fruit>("FRUIT")
            if (newItem != null) {
                fruitList?.add(newItem)
                var listIndex = fruitList?.lastIndex
                adapter?.notifyItemInserted(fruitList!!.lastIndex)
            }
        }

        if(requestCode == 2 && resultCode == Activity.RESULT_OK){

                fruitList?.removeAt(selectedPosition!!)
                adapter?.notifyItemRemoved(selectedPosition!!)

        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putParcelableArrayList(OUT_STATE_FRUIT_ARRAY_EXTRA_ID, fruitList)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val retored = savedInstanceState
                .getParcelableArrayList<Fruit>(OUT_STATE_FRUIT_ARRAY_EXTRA_ID)

        if (retored != null) {
            fruitList = retored
        }
    }
}
