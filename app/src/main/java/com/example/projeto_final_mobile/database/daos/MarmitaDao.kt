package com.projeto_final_mobile.database.daos

import java.util.UUID
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.projeto_final_mobile.database.models.Marmita


@Dao
interface MarmitaDao {
    @Insert
    suspend fun insert(marmita: Marmita)

    @Query("SELECT * FROM Marmita")
    suspend fun getAllMarmitas(): List<Marmita>

    @Query("SELECT * FROM Marmita WHERE id = :id")
    suspend fun getMarmitaById(id: UUID): Marmita?

    @Query("DELETE FROM Marmita WHERE id = :id")
    suspend fun deleteMarmitaById(id: UUID)
}