package com.udacity.asteroidradar.detail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val asteroidItem = DetailFragmentArgs.fromBundle(requireArguments()).selectedAsteroid
        val asteroid = Asteroid(
            id = asteroidItem.id.toLong(),
            codename = asteroidItem.name,
            closeApproachDate = asteroidItem.close_approach_data[0].close_approach_date,
            absoluteMagnitude = asteroidItem.absolute_magnitude_h,
            estimatedDiameter = asteroidItem.estimated_diameter.kilometers.estimated_diameter_max,
            relativeVelocity = asteroidItem.close_approach_data[0].relative_velocity.kilometers_per_second.toDouble(),
            distanceFromEarth = asteroidItem.close_approach_data[0].miss_distance.astronomical.toDouble(),
            isPotentiallyHazardous = asteroidItem.is_potentially_hazardous_asteroid
        )
        binding.asteroid = asteroid

        binding.helpButton.setOnClickListener {
            displayAstronomicalUnitExplanationDialog()
        }

        return binding.root
    }

    private fun displayAstronomicalUnitExplanationDialog() {
        val builder = AlertDialog.Builder(requireActivity())
            .setMessage(getString(R.string.astronomica_unit_explanation))
            .setPositiveButton(android.R.string.ok, null)
        builder.create().show()
    }
}
