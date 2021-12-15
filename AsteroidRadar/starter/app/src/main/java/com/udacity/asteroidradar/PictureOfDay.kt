package com.udacity.asteroidradar

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class PictureOfDay(
    val media_type: String,
    val title: String,
    @PrimaryKey val url: String
    )