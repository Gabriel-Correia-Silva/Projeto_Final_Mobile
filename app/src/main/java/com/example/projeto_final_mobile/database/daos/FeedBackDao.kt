package com.projeto_final_mobile.database.daos

import com.projeto_final_mobile.database.models.FeedbackMarmita
import androidx.room.*

@Dao
interface FeedbackMarmitaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(feedback: FeedbackMarmita)

    @Update
    suspend fun update(feedback: FeedbackMarmita)

    @Delete
    suspend fun delete(feedback: FeedbackMarmita)

    @Query("SELECT * FROM FeedbackMarmita WHERE idFeedBackMarmita = :id")
    suspend fun getFeedback(id: Long): FeedbackMarmita

    @Query("SELECT * FROM FeedbackMarmita")
    suspend fun getAllFeedbacks(): List<FeedbackMarmita>
}