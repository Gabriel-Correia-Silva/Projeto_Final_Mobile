package com.projeto_final_mobile.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

// esta classe representa a tabela user no banco de dados
@Entity
data class User(
    val nome: String,
    val sobrenome: String,
    @PrimaryKey val email: String,
    val senha: String,
    val endereco: String,
    val foto: String // Supondo que você armazenará o caminho ou URI da foto
)