package com.example.projeto_final_mobile.Dao

import android.content.ContentValues
import com.example.projeto_final_mobile.models.Marmita
import com.example.projeto_final_mobile.utils.DBHelper

class MarmitaDAO(private val dbHelper: DBHelper) {

    fun inserirMarmita(marmita: Marmita) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nome", marmita.nome)
            put("ingredientes", marmita.ingredientes)
            put("imagem", marmita.caminhoImagem) // Salva o caminho da imagem
            put("id_Marmitaria", marmita.idMarmitaria)
        }
        db.insert("Marmitas", null, values)
        db.close()
    }

    fun obterMarmitas(): List<Marmita> {
        val marmitas = mutableListOf<Marmita>()
        val db = dbHelper.readableDatabase
        val cursor = db.query("Marmitas", null, null, null, null, null, null)

        while (cursor.moveToNext()) {
            val id = cursor.getLong(cursor.getColumnIndex("id"))
            val nome = cursor.getString(cursor.getColumnIndex("nome"))
            val ingredientes = cursor.getString(cursor.getColumnIndex("ingredientes"))
            val caminhoImagem = cursor.getString(cursor.getColumnIndex("imagem"))
            val idMarmitaria = cursor.getLong(cursor.getColumnIndex("id_Marmitaria"))

            val marmita = Marmita(id, nome, ingredientes, caminhoImagem, idMarmitaria)
            marmitas.add(marmita)
        }

        cursor.close()
        db.close()
        return marmitas
    }

    fun atualizarMarmita(marmita: Marmita) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nome", marmita.nome)
            put("ingredientes", marmita.ingredientes)
            put("imagem", marmita.caminhoImagem) // Salva o caminho da imagem
            put("id_Marmitaria", marmita.idMarmitaria)
        }

        db.update("Marmitas", values, "id=?", arrayOf(marmita.id.toString()))
        db.close()
    }

    fun excluirMarmita(marmita: Marmita) {
        val db = dbHelper.writableDatabase
        db.delete("Marmitas", "id=?", arrayOf(marmita.id.toString()))
        db.close()
    }
}
