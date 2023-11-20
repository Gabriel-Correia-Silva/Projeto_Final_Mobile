package com.projeto_final_mobile.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.projeto_final_mobile.database.models.Marmitaria

@Dao
interface MarmitariaDao {
    @Insert
    fun insertMarmitaria(marmitaria: Marmitaria)

    @Query("SELECT * FROM Marmitaria WHERE email = :email AND senha = :senha")
    fun getMarmitariaByEmailAndSenha(email: String, senha: String): LiveData<List<Marmitaria>>

    @Query("SELECT * FROM Marmitaria WHERE email = :email")
    fun getMarmitariaByEmail(email: String): LiveData<Marmitaria>

    @Delete
    fun deleteMarmitaria(marmitaria: Marmitaria)

    @Query("DELETE FROM Marmitaria WHERE email = :email")
    fun deleteMarmitariaById(email: String): Int

    @Query("SELECT * FROM Marmitaria WHERE email = :email")
    fun getMarmitaria(email: String): LiveData<Marmitaria>

    @Update
    fun updateMarmitaria(marmitaria: Marmitaria)

    @Query("SELECT * FROM Marmitaria")
    fun getAllMarmitarias(): LiveData<List<Marmitaria>>

}