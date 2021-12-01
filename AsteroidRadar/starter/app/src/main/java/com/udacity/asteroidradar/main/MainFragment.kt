package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.databinding.ItemAsteroidBinding
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
        viewModel.getAsteroidsListLiveData.observe(viewLifecycleOwner) { asteroids ->
            updateUI(asteroids)
        }
    }

    private fun updateUI(asteroids: List<Asteroid>) {
        adapter = AsteroidAdapter(asteroids)
        asteroid_recycler.adapter = adapter
    }


    private inner class AsteroidViewHolder(
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

        override fun onClick(p0: View?) {
            Toast.makeText(context, "Clicked ${asteroid.codename}", Toast.LENGTH_SHORT).show()
        }
    }

    private inner class AsteroidAdapter(var asteroids: List<Asteroid>) :
        RecyclerView.Adapter<AsteroidViewHolder>() {
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
    }
}
