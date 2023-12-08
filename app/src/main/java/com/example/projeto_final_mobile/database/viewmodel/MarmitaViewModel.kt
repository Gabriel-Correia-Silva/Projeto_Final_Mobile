package com.example.projeto_final_mobile.database.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.lifecycleScope
import com.projeto_final_mobile.database.AppDatabase
import com.projeto_final_mobile.database.daos.MarmitaDao
import com.projeto_final_mobile.database.models.Marmita
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MarmitaViewModel(application: Application) : AndroidViewModel(application) {
    private val marmitaDao: MarmitaDao = AppDatabase.getInstance(application).marmitaDao()

    suspend fun insertMarmita(marmita: Marmita) {
        Log.d("MarmitaViewModel", "insertMarmita: $marmita")
        val existingMarmita = marmitaDao.getMarmitaByNameAndDescription(marmita.nome, marmita.descricao)
        if (existingMarmita == null) {
            marmitaDao.insert(marmita)
        } else {
            // Handle the case where the marmita already exists
        }
    }

    suspend fun getMarmitasByMarmitaria(marmitariaEmail: String): List<Marmita> {
        return marmitaDao.getMarmitasByMarmitaria(marmitariaEmail)   
    }
}