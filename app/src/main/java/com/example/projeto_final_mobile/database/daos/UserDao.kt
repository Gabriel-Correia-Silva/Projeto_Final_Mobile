package com.projeto_final_mobile.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.projeto_final_mobile.database.models.User

// Esta interface representa os comandos necesarios para manipular a tabela user no banco de dados

@Dao
interface UserDao {
    @Insert
    fun insert(user: User)

    @Query("SELECT * FROM user WHERE email = :email AND senha = :senha")
    fun getUserByEmailAndSenha(email: String, senha: String): LiveData<List<User>>

    @Query("SELECT * FROM user WHERE email = :email")
    fun getUserByEmail(email: String): LiveData<User>

    @Delete
    fun delete(user: User)

    @Query("DELETE FROM user WHERE email = :email")
    fun deleteUserById(email: String): Int

    @Query("SELECT * FROM user WHERE email = :email")
    fun getUser(email: String): LiveData<User>

    @Update
    fun updateUser(user: User)

    @Query("SELECT * FROM user WHERE email = :email LIMIT 1")
    fun getUserqByEmail(email: String): User

    companion object {
        fun insert(user: User) {

        }
    }
}