package com.example.projeto_final_mobile.database.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.projeto_final_mobile.database.MarmitariaDatabase
import com.projeto_final_mobile.database.daos.MarmitariaDao
import com.projeto_final_mobile.database.models.Marmitaria
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MarmitariaViewModel(application: Application) : AndroidViewModel(application) {
    private val marmitariaDao: MarmitariaDao =
        MarmitariaDatabase.getDatabase(application).marmitariaDao()

    suspend fun getAllMarmitarias(): List<Marmitaria> {
        return marmitariaDao.getAllMarmitaria()
    }

    fun insert(marmitaria: Marmitaria) {
        viewModelScope.launch(Dispatchers.IO) {
            val existingMarmitaria = marmitariaDao.getMarmitaria(marmitaria.email)
            if (existingMarmitaria == null) {
                marmitariaDao.insertMarmitaria(marmitaria)
            } else {
                marmitariaDao.updateMarmitaria(marmitaria)
            }
        }
    }

    fun update(marmitaria: Marmitaria) {
        viewModelScope.launch(Dispatchers.IO) {
            marmitariaDao.updateMarmitaria(marmitaria)
        }
    }

    fun delete(marmitaria: Marmitaria) {
        viewModelScope.launch(Dispatchers.IO) {
            marmitariaDao.deleteMarmitaria(marmitaria)
        }
    }

    suspend fun logAllMarmitarias() {
        val allMarmitarias: List<Marmitaria> = marmitariaDao.getAllMarmitaria()
        allMarmitarias.forEach() {  marmitaria ->
                Log.d("MarmitariaViewModel", "Nome: ${marmitaria.nome}, Preço Médio: ${marmitaria.precomedio}, Imagem: ${marmitaria.imagelogo}")
            }
        }
    suspend fun getMarmitariaByEmail(email: String): Marmitaria? {
        return marmitariaDao.getMarmitariaByEmail(email)
    }
}