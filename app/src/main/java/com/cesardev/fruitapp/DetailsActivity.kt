package com.cesardev.fruitapp

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val fruit = intent.getParcelableExtra<Fruit>(MainActivity.DETAILS_ACTIVITY_FRUIT_EXTRA_ID)
        val deleteButton = findViewById<Button>(R.id.deleteButton)
        val title = findViewById<TextView>(R.id.details_title_text_view)
        val description = findViewById<TextView>(R.id.details_description_text_view)
        val image = findViewById<ImageView>(R.id.details_image_view)

        deleteButton.setOnClickListener { deleteItem() }
        title.text = fruit?.name
        description.text = fruit?.benefits

        if(fruit?.BitmapImage != null){
            image.setImageBitmap(fruit.BitmapImage)

        }else if(fruit?.dummyImage != 0){
            image.setImageResource(fruit!!.dummyImage)
        }else{
            image.setImageResource(R.drawable.ic_android)
        }

    }

    //Deleta o item na lista da MainActivity
    private fun deleteItem(){

        val builder = AlertDialog.Builder(this)

        builder.setTitle("Deletar")
        builder.setMessage("Você realmente deseja deletar este item ?")
        builder.setPositiveButton("Sim"){dialog, it ->
            intent = Intent(this, MainActivity::class.java)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
        builder.setNegativeButton("Não"){dialog, it ->
            Toast.makeText(this,"Ação cancelada",Toast.LENGTH_SHORT).show()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()


    }

    override fun onBackPressed() {
        super.onBackPressed()
        intent = Intent(this, MainActivity::class.java)
        setResult(Activity.RESULT_CANCELED, intent)
        finish()
    }






}