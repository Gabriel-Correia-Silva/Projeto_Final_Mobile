package com.projeto_final_mobile.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.projeto_final_mobile.database.daos.MarmitaDao
import com.projeto_final_mobile.database.models.Marmita

// Define a classe MarmitariaDatabase como um banco de dados Room
@Database(entities = [Marmita::class], version = 1, exportSchema = false)
abstract class MarmitaDatabase : RoomDatabase() {

    // Define o DAO (Data Access Object) para acessar as operações do banco de dados
    abstract fun marmitaDao(): MarmitaDao

    companion object {
        @Volatile
        private var INSTANCE: MarmitaDatabase? = null

        // Obtém uma instância do banco de dados
        fun getDatabase(context: Context): MarmitaDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MarmitaDatabase::class.java,
                    "marmitaria_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}