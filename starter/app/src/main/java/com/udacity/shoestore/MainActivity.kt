package com.udacity.shoestore

import android.os.Bundle
import android.widget.Toolbar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.udacity.shoestore.models.ShoeListViewModel
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private val shoeListViewModel: ShoeListViewModel by viewModels()

    //    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.plant(Timber.DebugTree())
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(R.layout.activity_main)
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        findViewById<Toolbar>(R.id.toolbar).setupWithNavController(navController, appBarConfiguration)

    }
//
//    override fun onStart() {
//        super.onStart()
////        val navigationController = this.findNavController(R.id.myNavHostFragment)
////        NavigationUI.setupWithNavController(binding.toolbar, navigationController, AppBarConfiguration(navigationController.graph))
//    }
}
