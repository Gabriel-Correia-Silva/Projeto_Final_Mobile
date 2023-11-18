package com.example.projeto_final_mobile.fragments

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.projeto_final_mobile.R
import com.example.projeto_final_mobile.models.Marmita
import com.example.projeto_final_mobile.models.Marmitaria


class ItemMarmita : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_item_marmita)

    // Recupera o objeto Marmita passado como extra
    val marmita = intent.getSerializableExtra("marmita") as? Marmita

    val nameMarmita = findViewById<TextView>(R.id.nameMarmita)
    val precoMarmita = findViewById<TextView>(R.id.priceMarmita)

    // Verifica se 'marmita' não é null antes de usá-lo
    if (marmita != null) {
        // Define o texto dos TextViews para os dados da marmita
        nameMarmita.text = marmita.nome
        precoMarmita.text = "Preço: R$ ${marmita.preco}"
    } else {
        // Trate o caso em que 'marmita' é null
    }
}
}