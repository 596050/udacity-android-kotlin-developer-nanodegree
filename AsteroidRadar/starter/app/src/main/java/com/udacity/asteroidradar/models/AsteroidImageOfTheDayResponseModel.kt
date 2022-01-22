package com.udacity.asteroidradar.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AsteroidImageOfTheDayResponse(
    @SerializedName("media_type")
    val media_type: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String
): Parcelable
