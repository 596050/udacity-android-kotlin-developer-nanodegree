package com.udacity.asteroidradar

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class PictureOfDay(
    val media_type: String,
    val title: String,
    @PrimaryKey val url: String
    ): Parcelable