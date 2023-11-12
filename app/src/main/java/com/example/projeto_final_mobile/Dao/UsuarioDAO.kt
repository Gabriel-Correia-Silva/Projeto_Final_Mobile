package com.example.projeto_final_mobile.Dao

import android.content.ContentValues
import com.example.projeto_final_mobile.models.Usuario
import com.example.projeto_final_mobile.utils.DBHelper

class UsuarioDAO(private val dbHelper: DBHelper) {

    fun inserirUsuario(usuario: Usuario): Long {
    val db = dbHelper.writableDatabase
    val values = ContentValues().apply {
        put("nameUser", usuario.nome)
        put("sobrenomeUser", usuario.sobrenome)
        put("enderecoUser", usuario.endereco)
        put("emailUser", usuario.email)
        put("passwordUser", usuario.senha)
        put("imagem", usuario.caminhoImagem) // Salva o caminho da imagem
    }
    val id = db.insert("Usuarios", null, values)
    db.close()
    return id
}   
    
fun getUsuarioByEmailAndSenha(email: String, senha: String): Usuario? {
    val db = dbHelper.readableDatabase
    val cursor = db.query("Usuarios", null, "emailUser=? AND passwordUser=?", arrayOf(email, senha), null, null, null)

    var usuario: Usuario? = null
    if (cursor.moveToFirst()) {
        val id = cursor.getLong(cursor.getColumnIndex("id"))
        val nome = cursor.getString(cursor.getColumnIndex("nameUser"))
        val sobrenome = cursor.getString(cursor.getColumnIndex("sobrenomeUser"))
        val endereco = cursor.getString(cursor.getColumnIndex("enderecoUser"))
        val caminhoImagem = cursor.getString(cursor.getColumnIndex("imagem")) // Obtém o caminho da imagem

        usuario = Usuario(id, nome, sobrenome, endereco, email, senha, caminhoImagem)
    }

    cursor.close()
    db.close()
    return usuario
}
    


    fun obterUsuarios(): List<Usuario> {
        val usuarios = mutableListOf<Usuario>()
        val db = dbHelper.readableDatabase
        val cursor = db.query("Usuarios", null, null, null, null, null, null)

        while (cursor.moveToNext()) {
            val id = cursor.getLong(cursor.getColumnIndex("id"))
            val nome = cursor.getString(cursor.getColumnIndex("nameUser"))
            val sobrenome = cursor.getString(cursor.getColumnIndex("sobrenomeUser"))
            val endereco = cursor.getString(cursor.getColumnIndex("enderecoUser"))
            val email = cursor.getString(cursor.getColumnIndex("emailUser"))
            val senha = cursor.getString(cursor.getColumnIndex("passwordUser"))
            val caminhoImagem = cursor.getString(cursor.getColumnIndex("imagem")) // Obtém o caminho da imagem

            val usuario = Usuario(id, nome, sobrenome, endereco, email, senha, caminhoImagem)
            usuarios.add(usuario)
        }

        cursor.close()
        db.close()
        return usuarios
    }

    fun atualizarUsuario(usuario: Usuario) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nameUser", usuario.nome)
            put("sobrenomeUser", usuario.sobrenome)
            put("enderecoUser", usuario.endereco)
            put("emailUser", usuario.email)
            put("passwordUser", usuario.senha)
            put("imagem", usuario.caminhoImagem)
            // Coloque aqui a lógica para lidar com a imagem, se necessário
        }

        db.update("Usuarios", values, "id=?", arrayOf(usuario.id.toString()))
        db.close()
    }

    fun excluirUsuario(usuario: Usuario) {
        val db = dbHelper.writableDatabase
        db.delete("Usuarios", "id=?", arrayOf(usuario.id.toString()))
        db.close()
    }
}
