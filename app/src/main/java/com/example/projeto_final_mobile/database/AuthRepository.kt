package com.example.projeto_final_mobile.database

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import com.projeto_final_mobile.database.daos.UserDao
import com.projeto_final_mobile.database.models.User

class AuthRepository(private val userDao: UserDao) {
    // Obtém o usuário atualmente autenticado
    val currentUser: FirebaseUser? = Firebase.auth.currentUser

    // Verifica se há um usuário autenticado
    fun hasUser(): Boolean = Firebase.auth.currentUser != null

    // Obtém o ID do usuário autenticado
    fun getUserId(): String = Firebase.auth.currentUser?.uid.orEmpty()

    // Cria um novo usuário com o email e senha fornecidos
    // e insere as informações do usuário no banco de dados local
    suspend fun createUser(
        email: String,
        password: String,
        onComplete: (Boolean) -> Unit
    ) = withContext(Dispatchers.IO) {
        Firebase.auth
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    // Insere as informações do usuário no banco de dados local
                    userDao.insert(
                        User(
                            nome = "",
                            sobrenome = "",
                            email = email,
                            senha = password,
                            endereco = "",
                            foto = ""
                        )
                    )
                    onComplete.invoke(true)
                } else {
                    onComplete.invoke(false)
                }
            }.await()
    }

    // Realiza o login do usuário com o email e senha fornecidos
    // e verifica se o usuário existe no banco de dados local
    // Caso não exista, insere as informações do usuário no banco de dados local
    suspend fun loginUser(
        email: String,
        password: String,
        onComplete: (Boolean) -> Unit
    ) = withContext(Dispatchers.IO) {
        Firebase.auth
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    // Autenticação bem-sucedida, agora verifique o banco de dados local
                    val localUser = userDao.getUserByEmail(email)
                    if (localUser == null) {
                        // O usuário não existe no banco de dados local, então insira
                        val newUser = User(
                            nome = "",
                            sobrenome = "",
                            email = email,
                            senha = password,
                            endereco = "",
                            foto = ""
                        )
                        userDao.insert(newUser)
                    }
                    onComplete.invoke(true)
                } else {
                    onComplete.invoke(false)
                }
            }.await()
    }
}