package com.example.projeto_final_mobile.models

data class Sugestao(
    val idSugestao: Long,
    val nome: String,
    val endereco: String,
    val texto: String,
    val idUsuario: Long,
    val caminhoImagem: String // Adicione esta propriedade para armazenar o caminho da imagem
)
