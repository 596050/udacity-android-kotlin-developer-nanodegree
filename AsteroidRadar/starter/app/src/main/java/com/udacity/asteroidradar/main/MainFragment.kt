package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.repository.AsteroidRepository

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Binding is null"
        }
    private val viewModel by viewModels<MainViewModel>()
    private val asteroids: MutableList<Asteroid> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.asteroidRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.asteroidRecycler.adapter = AsteroidListSelectionRecyclerAdapter(asteroids)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenCreated {
            updateAsteroidsList()
        }
    }

    private suspend fun updateAsteroidsList() {
        val repo = AsteroidRepository(requireContext())
        val newAsteroids = repo.getAsteroidsFeedList()
        newAsteroids?.observe(viewLifecycleOwner) {
            it?.let {
                if (it != null) {
                    asteroids.addAll(it)
                }
            }
        }
        binding.asteroidRecycler.adapter?.notifyDataSetChanged()
    }
}