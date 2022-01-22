package com.udacity.asteroidradar.bindingadapters

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load

class ImageOfTheDayBinding {
    companion object {
        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, imageUrl: String) {
            Log.i("imageUrl", imageUrl.toString())
            if(!imageUrl.isNullOrEmpty()) {
            imageView.load(imageUrl) {
                crossfade(600)
            }
            }
        }
    }
}