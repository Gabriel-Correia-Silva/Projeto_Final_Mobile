



package com.projeto_final_mobile.utils

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto_final_mobile.R

import com.projeto_final_mobile.database.models.Marmita
import menu.projeto_final_mobile.adapter.MarmitaAdapter

class MarmitariaScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marmitaria_screen)

        val botao = findViewById<Button>(R.id.botao_view_conteudo_marmitaria)
        botao.setOnClickListener {
            finish()
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view_conteudo_marmitaria)
        recyclerView.layoutManager = LinearLayoutManager(this)

// Cria uma lista de 20 marmitas
val marmitaList = List(20) { Marmita("Nome marmita", 22, ""  ) } // Passa "22,00" como uma String

        // Passa a lista para o Adapter
        recyclerView.adapter = MarmitaAdapter(marmitaList)
    }
}