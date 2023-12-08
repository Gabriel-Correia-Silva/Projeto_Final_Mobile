// Este é o código da classe AppDatabase, responsável por criar e gerenciar o banco de dados da aplicação.
package com.projeto_final_mobile.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.projeto_final_mobile.database.daos.MarmitaDao
import com.projeto_final_mobile.database.daos.MarmitariaDao
import com.projeto_final_mobile.database.daos.UserDao
import com.projeto_final_mobile.database.models.Marmita
import com.projeto_final_mobile.database.models.Marmitaria
import com.projeto_final_mobile.database.models.User

@Database(entities = [User::class, Marmitaria::class, Marmita::class], version = 4)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun marmitariaDao(): MarmitariaDao
    abstract fun marmitaDao(): MarmitaDao

    companion object {
        private const val DATABASE_NAME = "app-database"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Método para obter a instância do banco de dados
        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        // Método para construir o banco de dados
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}