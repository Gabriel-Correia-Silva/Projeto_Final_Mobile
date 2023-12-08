package com.projeto_final_mobile.database.models

import androidx.room.Entity
import java.util.Date
import java.util.UUID
@Entity
data class Suggestion(
    val idSugestao: UUID,
    val nome: String,
    val endereco: String,
    val descricao: String,
    val idUsuario: UUID,
    var photo_id: UUID?,
    var date: Date
)