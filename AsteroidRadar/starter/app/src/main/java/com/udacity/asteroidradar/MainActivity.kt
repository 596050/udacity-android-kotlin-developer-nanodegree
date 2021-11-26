package com.udacity.asteroidradar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        if (currentFragment == null) {
//            val fragment = MainFragment.newInstance()
//            supportFragmentManager.beginTransaction()
//                .add(R.id.fragment_container, fragment)
//                .commit()
//        }
    }
}