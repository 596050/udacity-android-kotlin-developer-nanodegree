package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Binding is null"
        }

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    //    private val viewModel: MainViewModel by viewModels()
    private var adapter: AsteroidAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setHasOptionsMenu(true)
        asteroid_recycler.layoutManager = LinearLayoutManager(context)
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
        adapter = null
    }
    // companion object {
    //     fun newInstance(): MainFragment {
    //         return MainFragment()
    //     }
    // }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.getAsteroidsListLiveData().observe(viewLifecycleOwner) {
                updateUI(it)
            }
        }
    }
    private fun updateUI(asteroids: List<Asteroid>) {
        adapter = AsteroidAdapter(asteroids)
        asteroid_recycler.adapter = adapter
    }
}
