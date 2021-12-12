package com.udacity.asteroidradar.main

import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.ItemAsteroidBinding

class AsteroidListSelectionViewHolder(
    private val binding: ItemAsteroidBinding
) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var asteroid: Asteroid

    fun bind(asteroid: Asteroid) {
        this.asteroid = asteroid
        binding.codename.text = asteroid.codename
        binding.date.text = asteroid.closeApproachDate
        binding.icon.setImageResource(
            when (asteroid.isPotentiallyHazardous) {
                true -> R.drawable.ic_status_potentially_hazardous
                false -> R.drawable.ic_status_normal
            }
        )

        binding.root.setOnClickListener {
            itemView.findNavController().navigate(MainFragmentDirections.actionShowDetail(asteroid))
        }
    }
}