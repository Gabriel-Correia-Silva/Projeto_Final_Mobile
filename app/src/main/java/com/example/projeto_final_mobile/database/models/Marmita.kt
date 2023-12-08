package com.projeto_final_mobile.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class Marmita(
    @PrimaryKey val marmitaId: String = UUID.randomUUID().toString(),
    val nome: String,
    val descricao: String, // Adicionado descrição
    val preco: Float,
    var imagem_marmita: String?,
    val marmitariaEmail: String
){
    // Construtor secundário sem argumentos
    constructor() : this(
        nome = "",
        descricao = "",
        preco = 0.0f,
        imagem_marmita = null,
        marmitariaEmail = ""
    )
}