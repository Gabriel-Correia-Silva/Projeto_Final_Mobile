package com.example.projeto_final_mobile.models

import java.util.UUID

data class Marmita(
    val id: Long,
    val nome: String,
    val descricao: String,
    val preco: Double, // Adicionado preço
    val marmitariaId: Long,
    var photo_id: UUID? // Adicionado photo_id
) {
    constructor(id: Long, nome: String, descricao: String, preco: Double, marmitariaId: Long) 
        : this(id, nome, descricao, preco, marmitariaId, null)
}