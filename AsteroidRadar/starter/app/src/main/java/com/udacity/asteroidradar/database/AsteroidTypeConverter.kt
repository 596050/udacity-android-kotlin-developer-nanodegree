package com.udacity.asteroidradar.database

import androidx.room.TypeConverter


// How to use https://github.com/Kotlin/kotlinx.serialization
class AsteroidTypeConverter {
    @TypeConverter
    fun fromEstimatedDiameter(estimatedDiameter: EstimatedDiameter): String {
        return Gson().toJson(estimatedDiameter)
    }

    @TypeConverter
    fun toEstimatedDiameter(json: String): EstimatedDiameter {
        return Gson().fromJson(json, EstimatedDiameter::class.java)
    }

    @TypeConverter
    fun fromCloseApproachData(closeApproachData: List<CloseApproachData>): String {
        return Gson().toJson(closeApproachData)
    }

    @TypeConverter
    fun toCloseApproachData(json: String): List<CloseApproachData> {
        return Gson().fromJson(json, Array<CloseApproachData>::class.java).toList()
    }
}