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
            put("preco", marmita.preco) // Salva o preço
        }
        db.insert("Marmitas", null, values)
        db.close()
    }

    fun obterMarmitas(): List<Marmita> {
        val marmitas = mutableListOf<Marmita>()
        val db = dbHelper.readableDatabase
        val cursor = db.query("Marmitas", null, null, null, null, null, null)

        while (cursor.moveToNext()) {
            val id = cursor.getLong(cursor.getColumnIndexOrThrow("id"))
            val nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"))
            val ingredientes = cursor.getString(cursor.getColumnIndexOrThrow("ingredientes"))
            val caminhoImagem = cursor.getString(cursor.getColumnIndexOrThrow("imagem"))
            val idMarmitaria = cursor.getInt(cursor.getColumnIndexOrThrow("id_Marmitaria")).toLong()
            val preco = cursor.getDouble(cursor.getColumnIndexOrThrow("preco"))

            val marmita = Marmita(id, nome, ingredientes, caminhoImagem, idMarmitaria, preco)
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

    fun getMarmitasByMarmitariaId(idMarmitaria: Int): List<Marmita> {
        return getMarmitasByMarmitariaId(idMarmitaria.toLong())
    }

    fun getMarmitasByMarmitariaId(idMarmitaria: Long): List<Marmita> {
        val marmitas = mutableListOf<Marmita>()
        val db = dbHelper.readableDatabase

        val cursor = db.query(
            "Marmitas", // Nome da tabela
            null, // Colunas para retornar (null para todas)
            "id_Marmitaria = ?", // Colunas para a cláusula WHERE
            arrayOf(idMarmitaria.toString()), // Valores para a cláusula WHERE
            null, // group by
            null, // having
            null // order by
        )

        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow("id"))
                val nome = getString(getColumnIndexOrThrow("nome"))
                val ingredientes = getString(getColumnIndexOrThrow("ingredientes"))
                val imagem = getString(getColumnIndexOrThrow("imagem"))
                val preco = getDouble(getColumnIndexOrThrow("preco"))
                marmitas.add(Marmita(id, nome, ingredientes, imagem, idMarmitaria, preco))
            }
        }

        cursor.close()
        db.close()

        return marmitas
    }
    fun inicializarDados() {
        val Marmita = listOf(
            Marmita( 1,"Bucho 1", "tripa", "https://www.google.com/url?sa=i&url=http%3A%2F%2Fcbissn.ibict.br%2Findex.php%2Fimagens%2F1-galeria-de-imagens-01%2Fdetail%2F3-imagem-3-titulo-com-ate-45-caracteres&psig=AOvVaw1MwgQ2FvvmMEUS1-0j27hK&ust=1699896620127000&source=images&cd=vfe&ved=0CBEQjRxqFwoTCLDvkMP-voIDFQAAAAAdAAAAABAE",12,1.0),
            Marmita( 2,"arroz 12", "arroz", "https://www.google.com/url?sa=i&url=http%3A%2F%2Fcbissn.ibict.br%2Findex.php%2Fimagens%2F1-galeria-de-imagens-01%2Fdetail%2F3-imagem-3-titulo-com-ate-45-caracteres&psig=AOvVaw1MwgQ2FvvmMEUS1-0j27hK&ust=1699896620127000&source=images&cd=vfe&ved=0CBEQjRxqFwoTCLDvkMP-voIDFQAAAAAdAAAAABAE", 11, 1.0),
            Marmita( 3,"Marmitaria 3", "marmita", "https://www.google.com/url?sa=i&url=http%3A%2F%2Fcbissn.ibict.br%2Findex.php%2Fimagens%2F1-galeria-de-imagens-01%2Fdetail%2F3-imagem-3-titulo-com-ate-45-caracteres&psig=AOvVaw1MwgQ2FvvmMEUS1-0j27hK&ust=1699896620127000&source=images&cd=vfe&ved=0CBEQjRxqFwoTCLDvkMP-voIDFQAAAAAdAAAAABAE", 10,1.0)
            // Adicione mais marmitarias se necessário
        )

        for (marmita in Marmita) {
            inserirMarmita(marmita)
        }
    }

}
