package com.projeto_final_mobile.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class FeedbackMarmita(
    @PrimaryKey val idFeedBackMarmita: String = UUID.randomUUID().toString(),
    var textFeedBackMarmita: String,
    var quardaIdUser: String,
    var quardaIdmarmita: String
)