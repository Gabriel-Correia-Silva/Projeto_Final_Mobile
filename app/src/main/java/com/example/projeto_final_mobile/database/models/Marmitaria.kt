package com.projeto_final_mobile.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Marmitaria")
data class Marmitaria(
    val nome: String,
    val proprietario: String,
    @PrimaryKey val email: String,
    val senha: String?,
    val endereco: String?,
    val imagelogo: String?,
    val telefone: String?,
    val precomedio: Double,
) {
    // Construtor sem argumentos exigido pelo Firestore
    constructor() : this(
        nome = "",
        proprietario = "",
        email = "",
        senha = null,
        endereco = null,
        imagelogo = null,
        telefone = null,
        precomedio = 0.0
    )
}