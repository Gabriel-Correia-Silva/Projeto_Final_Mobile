package com.example.projeto_final_mobile.fragments

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto_final_mobile.adapter.PratoMarmitariaAdapter
import com.example.projeto_final_mobile.R
import com.example.projeto_final_mobile.Dao.MarmitaDAO
import com.example.projeto_final_mobile.utils.DBHelper

class ConteudoMarmitaria : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conteudo_marmitaria)

        val idMarmitaria = intent.getIntExtra("idMarmitaria",0)
        val dbHelper = DBHelper(this)
        val marmitaDao = MarmitaDAO(dbHelper)
        val marmitas = marmitaDao.getMarmitasByMarmitariaId(idMarmitaria)

        val meuRecyclerView = findViewById<RecyclerView>(R.id.recycler_view_conteudo_marmitaria)
        meuRecyclerView?.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@ConteudoMarmitaria)
            adapter = PratoMarmitariaAdapter(marmitas) { item ->
                val intent = Intent(this@ConteudoMarmitaria, PratoMarmitaria::class.java)
                intent.putExtra("idMarmita", item.id)
                startActivity(intent)
            }
        }

        val botao = findViewById<Button>(R.id.botao_view_conteudo_marmitaria)
        botao.setOnClickListener {
            finish()
        }
    }
}