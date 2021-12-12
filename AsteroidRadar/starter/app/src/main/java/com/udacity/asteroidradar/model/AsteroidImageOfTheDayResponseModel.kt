package com.udacity.asteroidradar.model

import kotlinx.serialization.Serializable

@Serializable
data class AsteroidImageOfTheDayResponse(
    val media_type: String,
    val title: String,
    val url: String
)
