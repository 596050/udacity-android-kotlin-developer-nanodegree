package com.udacity.asteroidradar.model

@kotlinx.serialization.Serializable
data class AsteroidFeedResponse(
    val near_earth_objects: Map<String, List<AsteroidFeedResponseModelItem>>
) {
    @kotlinx.serialization.Serializable
    data class AsteroidFeedResponseModelItem(
        val id: String,
        val name: String,
        val absolute_magnitude_h: Double,
        val estimated_diameter: EstimatedDiameter,
        val is_potentially_hazardous_asteroid: Boolean,
        val close_approach_data: List<CloseApproachData>,
        val nasa_jpl_url: String
    )

    @kotlinx.serialization.Serializable
    data class EstimatedDiameter(
        val kilometers: Kilometers
    )

    @kotlinx.serialization.Serializable
    data class Kilometers(
        val estimated_diameter_max: Double
    )

    @kotlinx.serialization.Serializable
    data class CloseApproachData(
        val relative_velocity: RelativeVelocity,
        val miss_distance: MissDistance,
        val close_approach_date: String
    )

    @kotlinx.serialization.Serializable
    data class RelativeVelocity(
        val kilometers_per_second: String
    )

    @kotlinx.serialization.Serializable
    data class MissDistance(
        val astronomical: String
    )
}
