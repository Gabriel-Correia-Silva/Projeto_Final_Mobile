package com.projeto_final_mobile.database

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.projeto_final_mobile.database.daos.UserDao
import com.projeto_final_mobile.database.models.User

// Definindo a classe AppDatabase como uma subclasse de RoomDatabase
@Database(entities = [User::class], version = 1)
abstract class AppDatabase() : RoomDatabase(), Parcelable {
    constructor(parcel: Parcel) : this() {
    }

    abstract fun userDao(): UserDao

    // Método para obter uma instância da AppDatabase
    companion object {
        // Nome do banco de dados
        private const val DATABASE_NAME = "app-database"
        // Variável volátil para garantir a consistência da instância em threads diferentes
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Método para obter a instância da AppDatabase
        fun getInstance(context: Context): AppDatabase {
            // Verifica se a instância já foi criada
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        // Método para construir o banco de dados
        private fun buildDatabase(context: Context): AppDatabase {
            // Cria o banco de dados utilizando o Room.databaseBuilder
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .build()
        }

        // Método para obter o banco de dados
        fun getDatabase(context: Context): AppDatabase {
            // Verifica se a instância já foi criada
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            // Sincroniza o acesso ao bloco de código para evitar que várias threads criem instâncias simultaneamente
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    /*private companion object CREATOR : Parcelable.Creator<AppDatabase> {
        override fun createFromParcel(parcel: Parcel): AppDatabase {
            return AppDatabase(parcel)
        }

        override fun newArray(size: Int): Array<AppDatabase?> {
            return arrayOfNulls(size)
        }
    }*/
}
