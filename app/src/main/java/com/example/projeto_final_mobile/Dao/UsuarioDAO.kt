package com.example.projeto_final_mobile.Dao

import android.content.ContentValues
import com.example.projeto_final_mobile.models.Usuario
import com.example.projeto_final_mobile.utils.DBHelper

// Classe UsuarioDAO para gerenciar as operações de banco de dados para a classe Usuario
class UsuarioDAO {
    // Método para inserir um novo usuário na lista de usuários
    // Retorna false se o e-mail do novo usuário já existir na lista
    fun insert(novoUsuario: Usuario): Boolean {
        for (usuario in listaUsuarios) {
            if (novoUsuario.email == usuario.email)
                return false
        }
        listaUsuarios.add(novoUsuario)
        return true
    }

    // Método para encontrar um usuário na lista de usuários usando o e-mail e a senha
    // Retorna o usuário se encontrado, ou null se não encontrado
    fun find(email: String, password: String): Usuario? {
        for (usuario in listaUsuarios) {
            if (usuario.email == email && usuario.password == password)
                return usuario
        }
        return null
    }

    // Método para verificar se um usuário existe na lista de usuários usando o id
    // Retorna true se o usuário existir, ou false se não existir
    fun find(idUsuario: UUID): Boolean {
        for (usuario in listaUsuarios) {
            if (usuario.idUsuario == idUsuario) {
                return true
            }
        }
        return false
    }

    // Método para inserir uma foto para um usuário
    // Retorna true se a foto foi inserida com sucesso, ou false se o usuário não existir
    fun insertFoto(usuario: Usuario, foto: Foto): Boolean {
        if (find(usuario.idUsuario)) {
            usuario.idFoto = foto.idFoto
            return true
        }
        return false
    }

    // Método para atualizar um usuário na lista de usuários
    // Não retorna nada
    fun setUsuario(_usuario: Usuario) {
        for (usuario in listaUsuarios) {
            if (usuario.idUsuario == _usuario.idUsuario) {
                usuario.nome = _usuario.nome
                usuario.sobrenome = _usuario.sobrenome
                usuario.email = _usuario.email
            }
        }
    }

    // Método para obter um usuário da lista de usuários usando o id
    // Retorna o usuário se encontrado, ou null se não encontrado
    fun getById(idUsuario: UUID?): Usuario? {
        for (usuario in listaUsuarios) {
            if (usuario.idUsuario == idUsuario) {
                return usuario
            }
        }
        return null
    }

    // Método para verificar se um e-mail já existe na lista de usuários
    // Retorna true se o e-mail existir, ou false se não existir
    fun emailExists(email: String): Boolean {
        for (usuario in listaUsuarios) {
            if (usuario.email == email)
                return true
        }
        return false
    }

    // Objeto companion que contém a lista de usuários
    companion object {
        val listaUsuarios: MutableList<Usuario> = mutableListOf()
    }
}

// Classe de dados para representar um usuário
data class Usuario(
    var idUsuario: UUID,
    var email: String,
    var nome: String,
    var sobrenome: String?,
    var password: String,
    var idFoto: UUID?
)

// Classe de dados para representar uma foto
data class Foto(
    var idFoto: UUID
)