package com.udacity.shoestore

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.udacity.shoestore.databinding.ActivityMainBinding
import com.udacity.shoestore.models.ShoeListViewModel
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private val shoeListViewModel: ShoeListViewModel by viewModels()
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

//                val ids = arrayOf(
//                    R.id.loginFragment,
//                    R.id.welcomeFragment
//                )
                val listener  = NavController.OnDestinationChangedListener { controller, destination, arguments ->
//                    if (ids.contains(destination.id)) {
//                        Log.d("LOGGINGINGINGING", destination.id.toString())
//                    }
                    if (destination.id == R.id.shoeListFragment && binding.toolbar.menu.findItem(R.menu.menu_shoes_list) == null) {
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
//
//    override fun onStart() {
//        super.onStart()
//        val listener  = NavController.OnDestinationChangedListener { controller, destination, arguments ->
//            Log.d("controller.toString", controller.toString())
//            Log.d("destination change", destination.toString())
//            Log.d("arguments.toString", arguments.toString())
//
//        }
//        controller.addOnDestinationChangedListener(listener)
//    }

}
