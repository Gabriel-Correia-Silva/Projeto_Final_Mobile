package com.example.projeto_final_mobile.screens.drawer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.projeto_final_mobile.R
import com.example.projeto_final_mobile.databinding.ActivityNavegationDrawerBinding


class NavegationDrawerActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityNavegationDrawerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityNavegationDrawerBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        val navHostFragment = (supportFragmentManager.findFragmentById(R.id.fragmentContainerView)) as NavHostFragment
        val navController = navHostFragment.navController

        _binding.navViewDrawer.setupWithNavController(navController)
        val appBarConfiguration = AppBarConfiguration(navController.graph, _binding.drawerLayout)
        _binding.toolbar2.setupWithNavController(navController, appBarConfiguration)
    }
}