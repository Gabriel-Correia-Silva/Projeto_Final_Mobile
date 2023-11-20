package com.projeto_final_mobile.database.models

import androidx.room.Entity
@Entity
data class Marmita(
    val nome: String,
    val preco: Int, // Adicionado preço
    var photo_id: String? // Adicionado photo_id
)