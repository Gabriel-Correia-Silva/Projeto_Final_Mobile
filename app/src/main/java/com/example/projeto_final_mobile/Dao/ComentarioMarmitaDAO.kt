package com.example.projeto_final_mobile.Dao

import android.content.ContentValues
import com.example.projeto_final_mobile.models.ComentarioMarmita
import com.example.projeto_final_mobile.utils.DBHelper

class ComentarioMarmitaDAO(private val dbHelper: DBHelper) {

    fun associarComentarioMarmita(comentarioMarmita: ComentarioMarmita) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("id_marmita", comentarioMarmita.idMarmita)
            put("id_comentario", comentarioMarmita.idComentario)
        }
        db.insert("ComentariosMarmitas", null, values)
        db.close()
    }

    fun obterAssociacoes(): List<ComentarioMarmita> {
        val associacoes = mutableListOf<ComentarioMarmita>()
        val db = dbHelper.readableDatabase
        val cursor = db.query("ComentariosMarmitas", null, null, null, null, null, null)

        while (cursor.moveToNext()) {
            val idComentarioMarmita = cursor.getLong(cursor.getColumnIndex("id_comentario_marmita"))
            val idMarmita = cursor.getLong(cursor.getColumnIndex("id_marmita"))
            val idComentario = cursor.getLong(cursor.getColumnIndex("id_comentario"))

            val associacao = ComentarioMarmita(idComentarioMarmita, idMarmita, idComentario)
            associacoes.add(associacao)
        }

        cursor.close()
        db.close()
        return associacoes
    }

    fun atualizarAssociacao(comentarioMarmita: ComentarioMarmita) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("id_marmita", comentarioMarmita.idMarmita)
            put("id_comentario", comentarioMarmita.idComentario)
        }

        db.update("ComentariosMarmitas", values, "id_comentario_marmita=?", arrayOf(comentarioMarmita.idComentarioMarmita.toString()))
        db.close()
    }

    fun excluirAssociacao(comentarioMarmita: ComentarioMarmita) {
        val db = dbHelper.writableDatabase
        db.delete("ComentariosMarmitas", "id_comentario_marmita=?", arrayOf(comentarioMarmita.idComentarioMarmita.toString()))
        db.close()
    }
}
