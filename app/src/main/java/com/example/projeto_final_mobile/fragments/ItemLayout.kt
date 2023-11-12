package com.example.projeto_final_mobile.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto_final_mobile.R
import com.example.projeto_final_mobile.Dao.MarmitariaDAO
import com.example.projeto_final_mobile.R.id.recyclerViewListfragment
import com.example.projeto_final_mobile.adapter.MarmitariaAdapterAdapter
import com.example.projeto_final_mobile.utils.DBHelper


class ItemLayout : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_layout_marmitaria)

        val dbHelper = DBHelper(this)
        val marmitariaDAO = MarmitariaDAO(dbHelper)

        marmitariaDAO.inicializarDados()

        val marmitarias = marmitariaDAO.obterMarmitarias()

        val adapter = MarmitariaAdapterAdapter(marmitarias)
        val meuRecyclerView: RecyclerView = findViewById(recyclerViewListfragment) // Altere o id para 'recyclerView'
        meuRecyclerView.adapter = adapter
    }
}