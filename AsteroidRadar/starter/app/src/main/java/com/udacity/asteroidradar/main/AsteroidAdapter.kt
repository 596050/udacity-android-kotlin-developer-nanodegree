package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.ItemAsteroidBinding

class AsteroidAdapter(var asteroids: List<Asteroid>) :
    RecyclerView.Adapter<AsteroidAdapter.AsteroidViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAsteroidBinding.inflate(inflater, parent, false)
        return AsteroidViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val asteroid = asteroids[position]
        holder.bind(asteroid)
    }

    override fun getItemCount(): Int {
        return asteroids.size
    }

    class AsteroidViewHolder(
        val binding: ItemAsteroidBinding
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        private lateinit var asteroid: Asteroid

        init {
            binding.root.setOnClickListener(this)
        }

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
        }

        override fun onClick(view: View?) {
            Toast.makeText(view?.context, "Clicked ${asteroid.codename}", Toast.LENGTH_SHORT).show()
        }
    }

}