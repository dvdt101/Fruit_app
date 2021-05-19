package com.cesardev.fruitapp

import android.app.Activity
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

        deleteButton.setOnClickListener {deleteItem()}
        title.text = fruit?.name
        description.text = fruit?.description

        if(fruit?.Bitmap != null){
            image.setImageBitmap(fruit.Bitmap)
            //Toast.makeText(this,fruit.toString(),Toast.LENGTH_SHORT).show()
            //description.text = fruit.toString()
        }else if(fruit?.image != 0){
            image.setImageResource(fruit!!.image)
        }else{
            image.setImageResource(R.drawable.ic_android)
        }

    }


    private fun deleteItem(){
        intent = Intent(this, MainActivity::class.java)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        intent = Intent(this, MainActivity::class.java)
        setResult(Activity.RESULT_CANCELED, intent)
        finish()
    }






}