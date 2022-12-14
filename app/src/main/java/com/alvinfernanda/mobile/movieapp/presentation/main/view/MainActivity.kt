package com.alvinfernanda.mobile.movieapp.presentation.main.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.alvinfernanda.mobile.movieapp.R
import com.alvinfernanda.mobile.movieapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup navigation controller for fragment
        val navController = findNavController(R.id.fragmentMain)
        binding.navView.setupWithNavController(navController)
    }

}