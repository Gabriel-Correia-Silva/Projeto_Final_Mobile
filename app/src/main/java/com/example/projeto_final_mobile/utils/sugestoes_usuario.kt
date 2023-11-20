package com.projeto_final_mobile.utils

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.projeto_final_mobile.R

class sugestoes_usuario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sugestoes_usuario)

        val botaoRetornar = findViewById<Button>(R.id.botao_retornar_abc)
        botaoRetornar.setOnClickListener {
            finish()
        }
        }
    }
