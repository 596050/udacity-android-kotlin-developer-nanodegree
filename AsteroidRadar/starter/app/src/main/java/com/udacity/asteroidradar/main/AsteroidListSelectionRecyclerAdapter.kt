package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.databinding.ItemAsteroidBinding

class AsteroidListSelectionRecyclerAdapter(
    private val asteroids: List<Asteroid>
) :
    RecyclerView.Adapter<AsteroidListSelectionViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): AsteroidListSelectionViewHolder {
        val binding = ItemAsteroidBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        return AsteroidListSelectionViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: AsteroidListSelectionViewHolder,
        position: Int
    ) {
        holder.bind(asteroids[position])
    }

    override fun getItemCount(): Int {
        return asteroids.size
    }
}