package com.udacity.asteroidradar.main

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.udacity.asteroidradar.NetworkResult
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.adapters.AsteroidsAdapter
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.models.*
import com.udacity.asteroidradar.observeOnce
import com.udacity.asteroidradar.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {
    private lateinit var mainViewModel: MainViewModel
    private var _binding: FragmentMainBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Binding is null"
        }

    private val mAdapter by lazy {
        AsteroidsAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        setHasOptionsMenu(true)
        setupRecyclerView()
        readDatabase()
        requestImageOfTheDayAPIData()
        return binding.root
    }

    private fun loadDataFromCache() {
        lifecycleScope.launch {
            mainViewModel.readAsteroids.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    val l = mutableListOf<AsteroidFeedResponseModelItem>()
                    database.forEach { asteroid ->
                        val item = AsteroidFeedResponseModelItem(
                            id = asteroid.id.toString(),
                            name = asteroid.codename,
                            close_approach_data = listOf(
                                CloseApproachData(
                                    relative_velocity = RelativeVelocity(
                                        kilometers_per_second = asteroid.relativeVelocity.toString()
                                    ),
                                    miss_distance = MissDistance(
                                        astronomical = asteroid.distanceFromEarth.toString()
                                    ),
                                    close_approach_date = asteroid.closeApproachDate
                                )
                            ),
                            absolute_magnitude_h = asteroid.absoluteMagnitude,
                            estimated_diameter = EstimatedDiameter(
                                kilometers = Kilometers(
                                    estimated_diameter_max = asteroid.estimatedDiameter
                                )
                            ),
                            is_potentially_hazardous_asteroid = asteroid.isPotentiallyHazardous,
                            nasa_jpl_url = ""
                        )
                        l.add(item)
                    }
                    mAdapter.setData(
                        AsteroidFeed(
                            near_earth_objects = mapOf("1" to l)
                        )
                    )
                }
            }
        }
    }

    private fun readDatabase() {
        lifecycleScope.launch {
            mainViewModel.readAsteroids.observeOnce(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    val l = mutableListOf<AsteroidFeedResponseModelItem>()
                    database.forEach { asteroid ->
                        val item = AsteroidFeedResponseModelItem(
                            id = asteroid.id.toString(),
                            name = asteroid.codename,
                            close_approach_data = listOf(
                                CloseApproachData(
                                    relative_velocity = RelativeVelocity(
                                        kilometers_per_second = asteroid.relativeVelocity.toString()
                                    ),
                                    miss_distance = MissDistance(
                                        astronomical = asteroid.distanceFromEarth.toString()
                                    ),
                                    close_approach_date = asteroid.closeApproachDate
                                )
                            ),
                            absolute_magnitude_h = asteroid.absoluteMagnitude,
                            estimated_diameter = EstimatedDiameter(
                                kilometers = Kilometers(
                                    estimated_diameter_max = asteroid.estimatedDiameter
                                )
                            ),
                            is_potentially_hazardous_asteroid = asteroid.isPotentiallyHazardous,
                            nasa_jpl_url = ""
                        )
                        l.add(item)
                    }
                    mAdapter.setData(
                        AsteroidFeed(
                            near_earth_objects = mapOf("1" to l)
                        )
                    )
                } else {
                    requestAsteroidsAPIData()
                }
            }
        }
    }

    private fun requestImageOfTheDayAPIData() {
        mainViewModel.getImageOfTheDay()
        mainViewModel.imageOfTheDayResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let {
                        Log.i("IMAGE IMAGE", it.toString())
                        if (it.media_type == "image") {
                            binding.activityMainImageOfTheDay.load(it.url) {
                                crossfade(true)
                            }
                            binding.activityMainImageOfTheDay.contentDescription = it.title
                        }
                    }
                }
                is NetworkResult.Error -> {
                    Toast.makeText(
                        requireContext(),
                        "Error getting asteroids",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    Toast.makeText(
                        requireContext(),
                        "Loading Asteroids",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun requestAsteroidsAPIData() {
        mainViewModel.getAsteroids()
        mainViewModel.asteroidsResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let {
                        mAdapter.setData(it)
                    }
                }
                is NetworkResult.Error -> {
                    loadDataFromCache()
                    Toast.makeText(
                        requireContext(),
                        "Error getting asteroids",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    Toast.makeText(
                        requireContext(),
                        "Loading Asteroids",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.asteroidRecycler.adapter = mAdapter
        binding.asteroidRecycler.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}
