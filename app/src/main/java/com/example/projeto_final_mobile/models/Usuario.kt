package com.example.projeto_final_mobile.models

data class Usuario(
    val id: Long,
    val nome: String,
    val sobrenome: String,
    val endereco: String,
    val email: String,
    val senha: String,
    val caminhoImagem: String? // Agora pode aceitar null
)
