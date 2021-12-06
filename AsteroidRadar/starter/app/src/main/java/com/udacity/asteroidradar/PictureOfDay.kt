package com.udacity.asteroidradar

import kotlinx.serialization.Serializable

@Serializable
data class PictureOfDay(val media_type: String, val title: String,
                        val url: String)