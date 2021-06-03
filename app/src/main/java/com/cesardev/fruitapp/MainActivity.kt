package com.cesardev.fruitapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
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
        adapter = FruitAdapter(fruitList!!, this, this)
        recycle_view.adapter = adapter
        recycle_view.layoutManager = LinearLayoutManager(this)
        recycle_view.setHasFixedSize(true)

        val itemTouchHelper = ItemTouchHelper(FruitItemTouchHelperCallBack(adapter!!, this))
        itemTouchHelper.attachToRecyclerView(recycle_view)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add_fruit, menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {

        R.id.menu_add_fruit -> {

            val intent : Intent =  Intent(this, SecondActivity::class.java)
            startActivityForResult(intent, 1)
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 1 && resultCode == Activity.RESULT_OK){
            val newItem = data?.getParcelableExtra<Fruit>("FRUIT")
            if (newItem != null) {

                var existItem:Boolean = false;


                fruitList?.forEach {
                    if(it.name.equals(newItem.name, ignoreCase = true)) existItem = true
                }

                if(!existItem){
                    fruitList?.add(newItem)
                    var listIndex = fruitList?.lastIndex
                    adapter?.notifyItemInserted(fruitList!!.lastIndex)
                    Toast.makeText(this, resources.getString(R.string.add_item), Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, resources.getString(R.string.item_already_exist), Toast.LENGTH_SHORT).show()
                }

            }
        }

        if(requestCode == 2 && resultCode == Activity.RESULT_OK){

                fruitList?.removeAt(selectedPosition!!)
                adapter?.notifyItemRemoved(selectedPosition!!)
            Toast.makeText(this, resources.getString(R.string.removed_item), Toast.LENGTH_SHORT).show()

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
