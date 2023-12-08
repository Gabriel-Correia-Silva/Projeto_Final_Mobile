package com.example.projeto_final_mobile.database

import com.google.firebase.firestore.FirebaseFirestore
import com.projeto_final_mobile.database.models.Marmitaria
import kotlinx.coroutines.tasks.await

class MarmitariaRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val collection = firestore.collection("marmitarias")

    // Insere uma marmitaria no banco de dados
    fun insertMarmitaria(marmitaria: Marmitaria) {
        marmitaria.email?.let { collection.document(it).set(marmitaria) }
    }

    // Obtém uma marmitaria pelo email
    suspend fun getMarmitariaByEmail(email: String): Marmitaria? {
        val snapshot = collection.document(email).get().await()
        return snapshot.toObject(Marmitaria::class.java)
    }

    // Deleta uma marmitaria pelo email
    suspend fun deleteMarmitaria(email: String) {
        collection.document(email).delete().await()
    }

    // Atualiza uma marmitaria no banco de dados
    suspend fun updateMarmitaria(marmitaria: Marmitaria) {
        marmitaria.email?.let { collection.document(it).set(marmitaria).await() }
    }

    // Obtém todas as marmitarias do banco de dados
    suspend fun getAllMarmitarias(): List<Marmitaria> {
        val snapshot = collection.get().await()
        return snapshot.toObjects(Marmitaria::class.java)
    }
}