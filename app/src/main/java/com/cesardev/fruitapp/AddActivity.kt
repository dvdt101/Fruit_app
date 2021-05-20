package com.cesardev.fruitapp

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import java.io.ByteArrayOutputStream


class AddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        //Funcionando normalmente


        val image = findViewById<ImageView>(R.id.selected_image_view)
        image.setImageResource(R.drawable.image)
        image.setOnClickListener { handleSelectedImage() }
        val addBtn = findViewById<Button>(R.id.addButton)
        addBtn.setOnClickListener { handleAddFruit() }

    }

    companion object{
        const val ADD_ACTIVITY_FRUIT_EXTRA_ID = "FRUIT"
        const val ADD_ACTIVITY_BITMAP_EXTRA_ID = "IMAGE"
        var image_uri:Uri? = null
    }

    //Funcionando normalmente
    fun handleSelectedImage(){

        val selectImage = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(Intent.createChooser(selectImage, "Escolha uma imagem"), 1)
    }

    fun handleAddFruit(){
        //Funcionando
        val title = findViewById<EditText>(R.id.input_fruit_name).text
        val description = findViewById<EditText>(R.id.input_fruit_description).text
        val image = findViewById<ImageView>(R.id.selected_image_view)

        val drawableImage = image.getDrawable()
        val bitmap:Bitmap = drawableImage.toBitmap()
        //  val url = image_uri
       // var image2 = BitmapFactory.decodeResource(getBaseContext().getResources(),)

        val reduceBitmap:Bitmap = getResizedBitmap(bitmap, 200)

        val fruit = Fruit(title.toString(), description.toString(), 0, reduceBitmap )

        intent = Intent(this, MainActivity::class.java)
        intent.putExtra(ADD_ACTIVITY_FRUIT_EXTRA_ID, fruit)
        setResult(Activity.RESULT_OK, intent)
        finish()

    }



    //Funcionando normalmente
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var image = findViewById<ImageView>(R.id.selected_image_view)
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == 1){
                if(data != null){
                    val uri = data.data
                    //image.background(R.drawable.whiteBackground)
                    image.setImageURI(uri)
                    image_uri = uri
                }
            }
        }

    }


    fun getResizedBitmap(image: Bitmap, maxSize: Int): Bitmap {
        var width = image.width
        var height = image.height
        val bitmapRatio = width.toFloat() / height.toFloat()
        if (bitmapRatio > 1) {
            width = maxSize
            height = (width / bitmapRatio).toInt()
        } else {
            height = maxSize
            width = (height * bitmapRatio).toInt()
        }
        return Bitmap.createScaledBitmap(image, width, height, true)
    }

}