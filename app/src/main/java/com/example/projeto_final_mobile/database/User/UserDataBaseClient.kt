package menu.projeto_final_mobile.database.User

import android.content.Context
import com.projeto_final_mobile.database.AppDatabase

object DatabaseClient {

    private var appDatabase: AppDatabase? = null

    fun getAppDatabase(context: Context): AppDatabase {
        if (appDatabase == null) {
            appDatabase = AppDatabase.getInstance(context)
        }
        return appDatabase!!
    }
}