package com.example.projeto_final_mobile.database

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.projeto_final_mobile.database.models.Marmita
import kotlinx.coroutines.tasks.await

class MarmitaRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val collection = firestore.collection("marmitas")

    // Insere uma marmita no banco de dados
    fun insertMarmita(marmita: Marmita) {
        marmita.marmitaId?.let { collection.document(it).set(marmita) }
    }

    // Obtém uma marmita pelo id
    suspend fun getMarmitaById(id: String): Marmita? {
        val snapshot = collection.document(id).get().await()
        return snapshot.toObject(Marmita::class.java)
    }

    // Deleta uma marmita pelo id
    suspend fun deleteMarmita(marmitaId: String) {
        collection.document(marmitaId).delete().await()
    }

    // Atualiza uma marmita no banco de dados
    suspend fun updateMarmita(marmita: Marmita) {
        marmita.marmitaId?.let { collection.document(it).set(marmita).await() }
    }

    // Obtém todas as marmitas do banco de dados
    suspend fun getAllMarmitas(): MutableList<DocumentSnapshot> {
        val _snapshot = collection.get().await()
        val snapshot = _snapshot.documents
        Log.d("MarmitaRepository", snapshot.toString())
        return snapshot
    }
}