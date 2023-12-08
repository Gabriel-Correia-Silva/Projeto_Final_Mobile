package com.projeto_final_mobile.database.daos

import androidx.lifecycle.LiveData
import java.util.UUID
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.projeto_final_mobile.database.models.Marmita
import com.projeto_final_mobile.database.models.Marmitaria


@Dao
interface MarmitaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(marmita: Marmita)

    @Query("SELECT * FROM marmita WHERE nome = :nome AND descricao = :descricao LIMIT 1")
    suspend fun getMarmitaByNameAndDescription(nome: String, descricao: String): Marmita?

    @Query("SELECT * FROM Marmita WHERE marmitaId = :id")
    fun getMarmitaById(id: UUID): Marmita?

    @Query("SELECT * FROM Marmita WHERE marmitariaEmail = :marmitariaEmail")
    suspend fun getMarmitasByMarmitaria(marmitariaEmail: String): List<Marmita>

    @Query("SELECT * FROM Marmita")
    suspend fun getAllMarmitas(): List<Marmita>

    @Query("DELETE FROM Marmita WHERE marmitaId = :id")
    fun deleteMarmitaById(id: UUID)





}