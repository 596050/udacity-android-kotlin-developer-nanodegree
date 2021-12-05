package com.udacity.asteroidradar.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AsteroidImageOfTheDayResponseModel(
    val media_type: String,
    val title: String,
    val url: String
)
