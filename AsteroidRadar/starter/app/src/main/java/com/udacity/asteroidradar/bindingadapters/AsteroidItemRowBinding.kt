package com.udacity.asteroidradar.bindingadapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.udacity.asteroidradar.R

class AsteroidItemRowBinding {
    companion object {
        @BindingAdapter("setIsPotentiallyHazardousIcon")
        @JvmStatic
        fun setIsPotentiallyHazardousIcon(imageView: ImageView, isPotentiallyHazardous: Boolean) {
            imageView.setImageResource(
                when (isPotentiallyHazardous) {
                    true -> R.drawable.ic_status_potentially_hazardous
                    false -> R.drawable.ic_status_normal
                }
            )
        }
    }
}