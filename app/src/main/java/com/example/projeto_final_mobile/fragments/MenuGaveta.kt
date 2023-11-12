package com.example.projeto_final_mobile.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.projeto_final_mobile.R
import com.google.android.material.navigation.NavigationView

class MenuGaveta : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_gaveta)

        val navView = findViewById<NavigationView>(R.id.nav_view)
        navView.setNavigationItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
        R.id.nav_home -> {
            // Código para ir para a tela inicial
        }
        R.id.nav_new_item -> {
            // Código para o novo item
        }
    }
    true
}
    }
}