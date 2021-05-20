package com.cesardev.fruitapp

import android.graphics.Bitmap
import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Fruit(
        val name:String,
        val benefits:String,
        val dummyImage:Int,
        val BitmapImage:Bitmap?) : Parcelable