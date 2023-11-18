package com.example.projeto_final_mobile.models

data class Suggestion(
    val idSugestao: UUID,
    val nome: String,
    val endereco: String,
    val descricao: String,
    val idUsuario: UUID,
    var photo_id: UUID?,
    var date: Date
){
    constructor(idSugestao: UUID, nome: String, endereco: String, descricao: String, idUsuario: UUID, date: Date) 
        : this(idSugestao, nome, endereco, descricao, idUsuario, null, date)
}


