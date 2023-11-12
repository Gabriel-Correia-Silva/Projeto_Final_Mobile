package com.example.projeto_final_mobile.models

data class Marmitaria(
    val id: String,
    val nome: Long,
    val endereco: String,
    val precoMedio: Double,
    val caminhoImagem: String // Adicione esta propriedade para armazenar o caminho da imagem
)
