package com.udacity.asteroidradar.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AsteroidFeed(
    @SerializedName("near_earth_objects")
    val near_earth_objects: Map<String, List<AsteroidFeedResponseModelItem>>,
): Parcelable

@Parcelize
data class AsteroidFeedResponseModelItem(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("absolute_magnitude_h")
    val absolute_magnitude_h: Double,
    @SerializedName("estimated_diameter")
    val estimated_diameter: EstimatedDiameter,
    @SerializedName("is_potentially_hazardous_asteroid")
    val is_potentially_hazardous_asteroid: Boolean,
    @SerializedName("close_approach_data")
    val close_approach_data: List<CloseApproachData>,
    @SerializedName("nasa_jpl_url")
    val nasa_jpl_url: String
): Parcelable

@Parcelize
data class EstimatedDiameter(
    @SerializedName("kilometers")
    val kilometers: Kilometers
): Parcelable

@Parcelize
data class Kilometers(
    @SerializedName("estimated_diameter_max")
    val estimated_diameter_max: Double
): Parcelable

@Parcelize
data class CloseApproachData(
    @SerializedName("relative_velocity")
    val relative_velocity: RelativeVelocity,
    @SerializedName("miss_distance")
    val miss_distance: MissDistance,
    @SerializedName("close_approach_date")
    val close_approach_date: String
): Parcelable

@Parcelize
data class RelativeVelocity(
    @SerializedName("kilometers_per_second")
    val kilometers_per_second: String
): Parcelable

@Parcelize
data class MissDistance(
    @SerializedName("astronomical")
    val astronomical: String
): Parcelable