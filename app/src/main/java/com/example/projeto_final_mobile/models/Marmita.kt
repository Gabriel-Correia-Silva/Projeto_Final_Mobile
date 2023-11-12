package com.example.projeto_final_mobile.models


data class Marmita(
    val id: Long,
    val nome: String,
    val ingredientes: String,
    val caminhoImagem: String, // Adicione esta propriedade para armazenar o caminho da imagem
    val idMarmitaria: Long
)
