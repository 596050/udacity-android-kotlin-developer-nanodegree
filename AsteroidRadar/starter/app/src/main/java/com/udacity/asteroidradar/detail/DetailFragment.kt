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
        val asteroid = asteroidItem.close_approach_data?.get(0).let {
            it?.close_approach_date?.let { it1 ->
                asteroidItem.absolute_magnitude_h?.let { it2 ->
                    asteroidItem.estimated_diameter?.kilometers?.let { it3 ->
                        asteroidItem.close_approach_data?.get(0)?.relative_velocity?.kilometers_per_second?.let { it4 ->
                            asteroidItem.is_potentially_hazardous_asteroid?.let { it5 ->
                                Asteroid(
                                    id = asteroidItem.id.toLong(),
                                    codename = asteroidItem.name,
                                    closeApproachDate = it1,
                                    absoluteMagnitude = it2,
                                    estimatedDiameter = it3.estimated_diameter_max,
                                    relativeVelocity = it4.toDouble(),
                                    distanceFromEarth = asteroidItem.close_approach_data?.get(0)?.miss_distance?.astronomical.toDouble(),
                                    isPotentiallyHazardous = it5
                                )
                            }
                        }
                    }
                }
            }
        }
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
