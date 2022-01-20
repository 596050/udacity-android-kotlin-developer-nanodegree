package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.udacity.asteroidradar.NetworkResult
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.adapters.AsteroidsAdapter
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {
    private lateinit var mainViewModel: MainViewModel
    private var _binding: FragmentMainBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Binding is null"
        }

    //    private val viewModel by viewModels<MainViewModel>()
    private val mAdapter by lazy {
        AsteroidsAdapter()
    }
//    private lateinit var mView: View
//    private val asteroids: MutableList<Asteroid> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModels()
    }

    private fun setupViewModels() {
//        val asteroidFeedService = AsteroidFeedResponseModelItemService.instance
//        viewModel.asteroidRepository = AsteroidRepository(viewModel.nasaDao, asteroidFeedService)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        mView = inflater.inflate(R.layout.fragment_main, container, false)
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        setHasOptionsMenu(true)
        setupRecyclerView()
        requestAPIData()
        return binding.root

//        return mView
    }

    private fun requestAPIData() {
        mainViewModel.getAsteroids()
        mainViewModel.asteroidsResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let {
                        mAdapter.setData(it)
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

//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
////        viewModel.viewModelScope.launch {
////            val newAsteroids = viewModel.getAsteroids()
////            asteroids.addAll(newAsteroids)
////            Log.i("ASTEROIDS", asteroids.toString())
////            binding.asteroidRecycler.adapter?.notifyDataSetChanged()
////            newAsteroids?.observe(viewLifecycleOwner) {
////                it.let {
////                    if (it != null && it.isNotEmpty()) {
////                        asteroids.addAll(it)
////                        Log.i("ASTEROIDS", asteroids.toString())
////                    } else {
////                        viewModel.saveAsteroids()
////
////                    }
////                    binding.asteroidRecycler.adapter?.notifyDataSetChanged()
////                }
////            }
//        }
//    }

}
