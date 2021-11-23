package com.udacity.shoestore

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.udacity.shoestore.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.plant(Timber.DebugTree())
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        findViewById<Toolbar>(R.id.toolbar).setupWithNavController(
            navController,
            appBarConfiguration
        )
        setupLogOutMenu(navController)
    }

    private fun setupLogOutMenu(navController: NavController) {
        val listener  = NavController.OnDestinationChangedListener { _, destination, _ ->
            if (binding.toolbar.menu.hasVisibleItems()) {
                binding.toolbar.menu.clear()
            }
            if (destination.id == R.id.shoeListFragment) {
                binding.toolbar.inflateMenu(R.menu.menu_shoes_list)
                binding.toolbar.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.log_out -> {
                            Toast.makeText(this, "Log out", Toast.LENGTH_SHORT).show()
                            val action = ShoeListFragmentDirections.actionShoeListFragmentToLoginFragment()
                            navController.navigate(action)
                            true
                        }
                        else -> throw UnknownError()
                    }
                }
            }
        }
        navController.addOnDestinationChangedListener(listener)
    }
}