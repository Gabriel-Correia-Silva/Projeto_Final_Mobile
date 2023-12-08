package com.example.projeto_final_mobile.database

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.projeto_final_mobile.database.MarmitaDatabase
import com.projeto_final_mobile.database.daos.FeedbackMarmitaDao
import com.projeto_final_mobile.database.models.FeedbackMarmita

@Database(entities = [FeedbackMarmita::class], version = 1)
abstract class FeedbackDatabase() : RoomDatabase() {


    abstract fun feedbackDao(): FeedbackMarmitaDao

    companion object {
        @Volatile
        private var INSTANCE: FeedbackDatabase? = null

        // Obtém uma instância do banco de dados
        fun getDatabase(context: Context): FeedbackDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FeedbackDatabase::class.java,
                    "marmitaria_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}
