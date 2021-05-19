package com.cesardev.fruitapp

import android.R.drawable
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.ByteArrayOutputStream


class MainActivity : AppCompatActivity(), FruitAdapter.OnItemClickListenner {

    companion object{
        const val DETAILS_ACTIVITY_FRUIT_EXTRA_ID = "Fruit_details"
        const val OUT_STATE_FRUIT_ARRAY_EXTRA_ID = "FRUIT_STATE"

    }

    private val FILE_RQ = 101
    private var fruitList:ArrayList<Fruit>? = null
    private var adapter:FruitAdapter? = null
    private var selectedPosition:Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            val saved = savedInstanceState.getParcelableArrayList<Fruit>(OUT_STATE_FRUIT_ARRAY_EXTRA_ID)
            fruitList = saved as ArrayList<Fruit>

        }else{
            fruitList = dummyList()
        }

        val recycle_view : RecyclerView = findViewById(R.id.rview)
        adapter = FruitAdapter(fruitList!!, this)
        recycle_view.adapter = adapter
        recycle_view.layoutManager = LinearLayoutManager(this)
        recycle_view.setHasFixedSize(true)

        checkForPermissios(android.Manifest.permission.READ_EXTERNAL_STORAGE, "imagens", FILE_RQ)
    }

    private fun checkForPermissios(permission: String, name: String, requestCode: Int){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            when{
                ContextCompat.checkSelfPermission(applicationContext, permission) == PackageManager.PERMISSION_GRANTED -> {
                    Toast.makeText(this, "$name premissão concebida", Toast.LENGTH_SHORT).show()
                }
                shouldShowRequestPermissionRationale(permission) -> showDialog(permission, name, requestCode)

                else -> ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
            }
        }
    }

    private fun showDialog(permission: String, name: String, requestCode: Int){
        val builder = AlertDialog.Builder(this)
        builder.apply {
            setMessage("Permissão para acessar $name ")
            setTitle("Permissão requisitada")
            setPositiveButton("OK"){ dialog, witch ->
                ActivityCompat.requestPermissions(this@MainActivity, arrayOf(permission), requestCode)
            }
        }
        val dialog : AlertDialog = builder.create()
        dialog.show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        fun innerCheck(name: String){
            if(grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED){
                Toast.makeText(applicationContext, "Permissao para $name foi recusada", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(applicationContext, "Permissao para $name foi aceita", Toast.LENGTH_SHORT).show()
            }
        }
        when(requestCode){
            FILE_RQ -> innerCheck("arquivos")
        }


    }

    fun insertItem(v: View){

        val intent : Intent =  Intent(this, AddActivity::class.java)
        startActivityForResult(intent, 1)

        //fruitList.add(item)
       // adapter.notifyItemInserted(fruitList.lastIndex)
    }


    private fun dummyList() : ArrayList<Fruit>{
        val list = ArrayList<Fruit>()
        list.add(Fruit("Maça", "Maça descrição", R.drawable.marca, null))
        list.add(Fruit("Uva", "Uva descrição", R.drawable.uvas, null))
        list.add(Fruit("Morango", "Morango descrição", R.drawable.morango, null))
        return list
    }

    override fun onItemClick(position: Int) {

        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(DETAILS_ACTIVITY_FRUIT_EXTRA_ID, fruitList!![position])
        selectedPosition = position
        startActivityForResult(intent, 2)
        //Toast.makeText(this,"Item $position Clicked",Toast.LENGTH_SHORT).show()
        //val clickedItem : Fruit = fruitList[position]
        //adapter.notifyDataSetChanged()
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
       val retored = savedInstanceState.getParcelableArrayList<Fruit>(OUT_STATE_FRUIT_ARRAY_EXTRA_ID)
        if (retored != null) {
            fruitList = retored
        }
    }
}
