package com.example.projeto_final_mobile.models


import java.util.UUID

data class Marmitaria(
    val id: Long,
    val nome: String,
    val endereco: String,
    val precoMedio: Double,
    var photo_id: UUID? // Adicionado photo_id
) {
    constructor(id: Long, nome: String, endereco: String, precoMedio: Double) 
        : this(id, nome, endereco, precoMedio, null)
}