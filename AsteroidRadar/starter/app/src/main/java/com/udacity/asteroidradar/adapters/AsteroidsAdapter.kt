package com.udacity.asteroidradar.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.AsteroidsDiffUtil
import com.udacity.asteroidradar.databinding.ItemAsteroidBinding
import com.udacity.asteroidradar.main.MainFragmentDirections
import com.udacity.asteroidradar.models.AsteroidFeed
import com.udacity.asteroidradar.models.AsteroidFeedResponseModelItem

class AsteroidsAdapter : RecyclerView.Adapter<AsteroidsAdapter.AsteroidViewHolder>() {

    private var asteroids = emptyList<AsteroidFeedResponseModelItem>()

    class AsteroidViewHolder(private val binding: ItemAsteroidBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(asteroidFeedItem: AsteroidFeedResponseModelItem) {
            binding.asteroid = asteroidFeedItem
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): AsteroidViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemAsteroidBinding.inflate(layoutInflater, parent, false)
                return AsteroidViewHolder(binding)
            }
        }

        override fun onClick(view: View?) {
            binding.asteroid?.let {
                itemView.findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        return AsteroidViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val currentAsteroid = asteroids[position]
        holder.bind(currentAsteroid)
    }

    override fun getItemCount(): Int {
        return asteroids.size
    }

    fun setData(newData: AsteroidFeed) {
        val newAsteroids = newData.near_earth_objects.values?.toList()[0]
        val asteroidsDiffUtil = AsteroidsDiffUtil(asteroids, newAsteroids)
        val diffUtilResult = DiffUtil.calculateDiff(asteroidsDiffUtil)
        asteroids = newAsteroids
        diffUtilResult.dispatchUpdatesTo(this)
    }
}
