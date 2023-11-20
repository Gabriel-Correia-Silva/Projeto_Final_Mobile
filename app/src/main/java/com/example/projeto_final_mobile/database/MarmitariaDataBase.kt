package com.projeto_final_mobile.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.projeto_final_mobile.database.daos.MarmitariaDao
import com.projeto_final_mobile.database.models.Marmitaria

// Definindo a classe abstrata MarmitariaDatabase que herda de RoomDatabase
@Database(entities = [Marmitaria::class], version = 2)
abstract class MarmitariaDatabase : RoomDatabase() {

    // Método abstrato para obter o DAO da entidade Marmitaria
    abstract fun marmitariaDao(): MarmitariaDao

    companion object {
        private const val DATABASE_NAME = "marmitaria-database"
        @Volatile
        private var INSTANCE: MarmitariaDatabase? = null

        // Método estático para obter a instância do banco de dados
        fun getInstance(context: Context): MarmitariaDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        // Método privado para construir o banco de dados
        private fun buildDatabase(context: Context): MarmitariaDatabase {
            return Room.databaseBuilder(context, MarmitariaDatabase::class.java, DATABASE_NAME)
                .build()
        }

        // Método estático para obter o banco de dados
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