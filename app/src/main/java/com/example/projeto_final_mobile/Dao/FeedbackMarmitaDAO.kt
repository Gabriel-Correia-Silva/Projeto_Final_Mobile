package com.example.projeto_final_mobile.Dao



class FeedbackMarmitaDAO{

fun insert(newComentarioMarmita: ComentarioMarmita, comentarioMarmitaList: MutableList<ComentarioMarmita>): Boolean {
    for (comentarioMarmita in comentarioMarmitaList) {
        if (newComentarioMarmita.idComentarioMarmita == comentarioMarmita.idComentarioMarmita)
            return false
    }
    comentarioMarmitaList.add(newComentarioMarmita)
    return true
}


}
