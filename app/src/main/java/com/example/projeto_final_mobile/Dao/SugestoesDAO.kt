package com.example.projeto_final_mobile.Dao

import android.content.ContentValues
import com.example.projeto_final_mobile.models.Sugestao
import com.example.projeto_final_mobile.utils.DBHelper

class SugestaoDAO(private val dbHelper: DBHelper) {

    fun inserirSugestao(sugestao: Sugestao) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nome", sugestao.nome)
            put("endereco", sugestao.endereco)
            put("texto", sugestao.texto)
            put("id_usuario", sugestao.idUsuario)
            put("imagem", sugestao.caminhoImagem) // Salva o caminho da imagem
        }
        db.insert("Sugestoes", null, values)
        db.close()
    }

    fun obterSugestoes(): List<Sugestao> {
        val sugestoes = mutableListOf<Sugestao>()
        val db = dbHelper.readableDatabase
        val cursor = db.query("Sugestoes", null, null, null, null, null, null)

        while (cursor.moveToNext()) {
            val idSugestao = cursor.getLong(cursor.getColumnIndex("id_sugestao"))
            val nome = cursor.getString(cursor.getColumnIndex("nome"))
            val endereco = cursor.getString(cursor.getColumnIndex("endereco"))
            val texto = cursor.getString(cursor.getColumnIndex("texto"))
            val idUsuario = cursor.getLong(cursor.getColumnIndex("id_usuario"))
            val caminhoImagem = cursor.getString(cursor.getColumnIndex("imagem"))

            val sugestao = Sugestao(idSugestao, nome, endereco, texto, idUsuario, caminhoImagem)
            sugestoes.add(sugestao)
        }

        cursor.close()
        db.close()
        return sugestoes
    }

    fun atualizarSugestao(sugestao: Sugestao) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nome", sugestao.nome)
            put("endereco", sugestao.endereco)
            put("texto", sugestao.texto)
            put("id_usuario", sugestao.idUsuario)
            put("imagem", sugestao.caminhoImagem) // Salva o caminho da imagem
        }

        db.update("Sugestoes", values, "id_sugestao=?", arrayOf(sugestao.idSugestao.toString()))
        db.close()
    }

    fun excluirSugestao(sugestao: Sugestao) {
        val db = dbHelper.writableDatabase
        db.delete("Sugestoes", "id_sugestao=?", arrayOf(sugestao.idSugestao.toString()))
        db.close()
    }
}
