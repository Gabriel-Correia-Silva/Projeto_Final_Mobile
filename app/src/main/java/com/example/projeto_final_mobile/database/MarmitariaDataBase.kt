package com.projeto_final_mobile.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.projeto_final_mobile.database.daos.MarmitariaDao
import com.projeto_final_mobile.database.models.Marmitaria

// Define a classe MarmitariaDatabase como um banco de dados Room
@Database(entities = [Marmitaria::class], version = 1, exportSchema = false)
abstract class MarmitariaDatabase : RoomDatabase() {

    // Define o DAO (Data Access Object) para acessar as operações do banco de dados
    abstract fun marmitariaDao(): MarmitariaDao

    companion object {
        @Volatile
        private var INSTANCE: MarmitariaDatabase? = null

        // Obtém uma instância do banco de dados
        fun getDatabase(context: Context): MarmitariaDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MarmitariaDatabase::class.java,
                    "marmitaria_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}