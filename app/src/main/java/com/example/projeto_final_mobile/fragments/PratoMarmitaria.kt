package com.example.projeto_final_mobile.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto_final_mobile.R
import com.example.projeto_final_mobile.adapter.ComentariosAdapter
import com.example.projeto_final_mobile.adapter.ComentariosUsuario

class PratoMarmitaria : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prato_marmitaria)

        val botaoRetornarPratoMarmitaria = findViewById<Button>(R.id.botao_retornar_prato_marmitaria)
        botaoRetornarPratoMarmitaria.setOnClickListener {
            // Destrua esta atividade
            finish()
        }

        // Configura o RecyclerView
        val comentarios = listOf(
            ComentariosUsuario("Usuário 1", "Comentário 1"),
            ComentariosUsuario("Usuário 2", "Comentário 2"),
            // Adicione mais comentários aqui...
        )
        val adapter = ComentariosAdapter(comentarios)
        val meuRecyclerView = findViewById<RecyclerView>(R.id.recycler_view_comentarios)
        meuRecyclerView?.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@PratoMarmitaria)
            this.adapter = adapter
        }


    }
}