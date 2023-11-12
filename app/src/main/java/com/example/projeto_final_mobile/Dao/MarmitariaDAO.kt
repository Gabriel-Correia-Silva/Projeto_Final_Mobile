package com.example.projeto_final_mobile.Dao

import android.annotation.SuppressLint
import android.content.ContentValues
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto_final_mobile.R
import com.example.projeto_final_mobile.adapter.MarmitariaAdapter
import com.example.projeto_final_mobile.models.Marmitaria
import com.example.projeto_final_mobile.utils.DBHelper



class MarmitariaDAO(private val dbHelper: DBHelper) {

    fun inserirMarmitaria(marmitaria: Marmitaria) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put ("nome", marmitaria.nome)
            put("endereco", marmitaria.endereco)
            put("precoMedio", marmitaria.precoMedio)
            put("imagem", marmitaria.caminhoImagem) // Salva o caminho da imagem
        }
        db.insert("Marmitarias", null, values)
        db.close()
    }

    @SuppressLint("Range")
    fun obterMarmitarias(): List<Marmitaria> {
        val marmitarias = mutableListOf<Marmitaria>()
        val db = dbHelper.readableDatabase
        val cursor = db.query("Marmitarias", null, null, null, null, null, null)

        while (cursor.moveToNext()) {
            val nome = cursor.getString(cursor.getColumnIndex("nome"))
            val id = cursor.getLong(cursor.getColumnIndex("id"))
            val endereco = cursor.getString(cursor.getColumnIndex("endereco"));
            val precoMedio = cursor.getDouble(cursor.getColumnIndex("precoMedio"))
            val caminhoImagem = cursor.getString(cursor.getColumnIndex("imagem"))

            val marmitaria = Marmitaria(nome,id, endereco, precoMedio, caminhoImagem)
            marmitarias.add(marmitaria)
        }

        cursor.close()
        db.close()
        return marmitarias
    }

    fun atualizarMarmitaria(marmitaria: Marmitaria) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nome", marmitaria.nome)
            put("endereco", marmitaria.endereco)
            put("precoMedio", marmitaria.precoMedio)
            put("imagem", marmitaria.caminhoImagem) // Salva o caminho da imagem
        }

        db.update("Marmitarias", values, "id=?", arrayOf(marmitaria.id.toString()))
        db.close()
    }

    fun excluirMarmitaria(marmitaria: Marmitaria) {
        val db = dbHelper.writableDatabase
        db.delete("Marmitarias", "id=?", arrayOf(marmitaria.id.toString()))
        db.close()
    }

    fun inicializarDados() {
        val marmitarias = listOf(
            Marmitaria("Marmitaria 1", 1, "Rua 1", 10.0,"https://www.google.com/url?sa=i&url=http%3A%2F%2Fcbissn.ibict.br%2Findex.php%2Fimagens%2F1-galeria-de-imagens-01%2Fdetail%2F3-imagem-3-titulo-com-ate-45-caracteres&psig=AOvVaw1MwgQ2FvvmMEUS1-0j27hK&ust=1699896620127000&source=images&cd=vfe&ved=0CBEQjRxqFwoTCLDvkMP-voIDFQAAAAAdAAAAABAE"),
            Marmitaria("Marmitaria 12", 1, "Rua 1", 15.0, "https://www.google.com/url?sa=i&url=http%3A%2F%2Fcbissn.ibict.br%2Findex.php%2Fimagens%2F1-galeria-de-imagens-01%2Fdetail%2F3-imagem-3-titulo-com-ate-45-caracteres&psig=AOvVaw1MwgQ2FvvmMEUS1-0j27hK&ust=1699896620127000&source=images&cd=vfe&ved=0CBEQjRxqFwoTCLDvkMP-voIDFQAAAAAdAAAAABAE"),
            Marmitaria("Marmitaria 3", 3, "Rua 3", 20.0, "https://www.google.com/url?sa=i&url=http%3A%2F%2Fcbissn.ibict.br%2Findex.php%2Fimagens%2F1-galeria-de-imagens-01%2Fdetail%2F3-imagem-3-titulo-com-ate-45-caracteres&psig=AOvVaw1MwgQ2FvvmMEUS1-0j27hK&ust=1699896620127000&source=images&cd=vfe&ved=0CBEQjRxqFwoTCLDvkMP-voIDFQAAAAAdAAAAABAE")
            // Adicione mais marmitarias se necessário
        )

        for (marmitaria in marmitarias) {
            inserirMarmitaria(marmitaria)
        }
    }
}
