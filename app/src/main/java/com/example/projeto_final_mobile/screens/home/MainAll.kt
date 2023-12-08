package com.example.projeto_final_mobile.screens.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.navigation.ui.AppBarConfiguration
import com.example.projeto_final_mobile.R
import com.example.projeto_final_mobile.databinding.ActivityMainAllBinding

class MainAll : AppCompatActivity() {

    private lateinit var _binding: ActivityMainAllBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainAllBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(navController.graph)
        _binding.toolbar.setupWithNavController(navController, appBarConfiguration)
    }
}