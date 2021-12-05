package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.databinding.ItemAsteroidBinding

class AsteroidListSelectionRecyclerAdapter :
    RecyclerView.Adapter<AsteroidListSelectionViewHolder>() {
    val asteroidsList = arrayOf(Asteroid(
        id=1,
        codename="codename",
        closeApproachDate="2020",
        absoluteMagnitude=1.0,
        estimatedDiameter=1.0,
        relativeVelocity=1.0,
        distanceFromEarth=1.0,
        isPotentiallyHazardous=true
    ))

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): AsteroidListSelectionViewHolder {
        val binding =
            ItemAsteroidBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        return AsteroidListSelectionViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: AsteroidListSelectionViewHolder,
        position: Int
    ) {
        holder.bind(asteroidsList[position])
    }

    override fun getItemCount(): Int {
        return asteroidsList.size
    }
}