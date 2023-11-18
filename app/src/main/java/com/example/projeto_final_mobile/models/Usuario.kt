package com.example.projeto_final_mobile.models

data class Usuario(
    val user_id: Long,
    val nome: String,
    val sobrenome: String,
    val endereco: String,
    val email: String,
    val senha: String,
    var photo_id: UUID?
) {
    constructor(user_id: Long, nome: String, sobrenome: String, endereco: String, email: String, senha: String) 
        : this(user_id, nome, sobrenome, endereco, email, senha, null)
}