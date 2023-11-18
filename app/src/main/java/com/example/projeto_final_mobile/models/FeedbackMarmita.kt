package com.example.projeto_final_mobile.models

data class FeedbackMarmita(
    val idFeedBackMarmita: Long,
    val textFeedBackMarmita: String,
    var photo_id: UUID?
){
    constructor(idFeedBackMarmita: Long, textFeedBackMarmita: String) 
        : this(idFeedBackMarmita, textFeedBackMarmita, null)
}

