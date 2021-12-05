package com.udacity.asteroidradar.models

import android.util.Log
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonClass

class AsteroidFeedResponseModelAdapter {
    @FromJson fun fromJson(json: AsteroidFeedResponse): List<AsteroidFeedResponse.AsteroidFeedResponseModelItem> {
        Log.i("FROM JSON", json.toString())
        return json.near_earth_objects.values.toList()[0]
    }
}

data class AsteroidFeedResponse(
    val near_earth_objects: Map<String, List<AsteroidFeedResponseModelItem>>
) {
    @JsonClass(generateAdapter = true)
    data class AsteroidFeedResponseModelItem(
        val id: String,
        val absolute_magnitude: Double,
        val estimated_diameter: EstimatedDiameter,
        val is_potentially_hazardous_asteroid: Boolean,
        val close_approach_data: List<CloseApproachData>
    )

    @JsonClass(generateAdapter = true)
    data class EstimatedDiameter(
        val kilometers: Kilometers
    )

    @JsonClass(generateAdapter = true)
    data class Kilometers(
        val estimated_diameter_max: Double
    )

    @JsonClass(generateAdapter = true)
    data class CloseApproachData(
        val relative_velocity: RelativeVelocity,
        val miss_distance: MissDistance,
        val close_approach_date: String
    )

    @JsonClass(generateAdapter = true)
    data class RelativeVelocity(
        val kilometers_per_second: String
    )

    @JsonClass(generateAdapter = true)
    data class MissDistance(
        val astronomical: String
    )
}
