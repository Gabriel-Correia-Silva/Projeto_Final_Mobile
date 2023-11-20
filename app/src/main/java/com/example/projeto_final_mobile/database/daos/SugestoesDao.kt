package com.projeto_final_mobile.database.daos

import androidx.room.*
import com.projeto_final_mobile.database.models.Suggestion
import java.util.UUID

@Dao
interface SugestoesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(suggestion: Suggestion)

    @Update
    suspend fun update(suggestion: Suggestion)

    @Delete
    suspend fun delete(suggestion: Suggestion)

    @Query("SELECT * FROM suggestion WHERE idSugestao = :id")
    suspend fun getSuggestion(id: UUID): Suggestion

    @Query("SELECT * FROM suggestion")
    suspend fun getAllSuggestions(): List<Suggestion>
}