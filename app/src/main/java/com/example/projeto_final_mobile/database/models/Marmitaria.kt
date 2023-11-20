package com.projeto_final_mobile.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Marmitaria(
    val nome: String,
    val proprietario: String,
    @PrimaryKey
    val email: String,
    val senha: String?,
    val endereco: String?,
    val imagelogo: String?,
    val telefone: String?,
    val precomedio: Double,
)

