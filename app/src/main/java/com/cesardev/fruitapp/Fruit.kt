package com.cesardev.fruitapp

import android.graphics.Bitmap
import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Fruit(var name:String, var description:String, var image:Int, var Bitmap:Bitmap?) : Parcelable