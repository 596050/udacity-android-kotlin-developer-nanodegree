package com.udacity.asteroidradar

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.models.AsteroidFeedResponseModelItem
import kotlinx.parcelize.Parcelize

//@Parcelize
//@Entity(tableName="Asteroid")
//class Asteroid(
//        var asteroid: Any
//    ) {
//    @PrimaryKey(autoGenerate = false)
//    var id: Int = 0
//}

@Parcelize
@Entity(tableName="Asteroid")
data class Asteroid(
    @PrimaryKey val id: Long,
    val codename: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
) : Parcelable