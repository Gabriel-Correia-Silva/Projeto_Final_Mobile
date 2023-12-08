package com.example.projeto_final_mobile.fragments


import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projeto_final_mobile.R
import com.example.projeto_final_mobile.database.adapters.FeedbackAdapter
import com.example.projeto_final_mobile.databinding.FragmentDescricaoBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.projeto_final_mobile.database.models.FeedbackMarmita

class DescricaoFragment : Fragment() {
    private var _binding: FragmentDescricaoBinding? = null
    private val binding get() = _binding!!
    private val feedbacks = mutableListOf<FeedbackMarmita>()
    private lateinit var adapter: FeedbackAdapter
    private lateinit var marmitaId: String // Variável para armazenar o ID da marmita
    private lateinit var marmitaNome: String
    private lateinit var marmitaDescricao: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDescricaoBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun addFeedbackToDatabase(feedback: FeedbackMarmita) {
        val db = FirebaseFirestore.getInstance()
        db.collection("feedbacks")
            .add(feedback)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "Feedback adicionado com ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Erro ao adicionar feedback", e)
            }
    }

    private fun editFeedbackInDatabase(feedback: FeedbackMarmita) {
        val db = FirebaseFirestore.getInstance()
        db.collection("feedbacks").document(feedback.idFeedBackMarmita)
            .set(feedback)
            .addOnSuccessListener {
                Log.d(TAG, "Feedback atualizado com sucesso.")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Erro ao atualizar feedback", e)
            }
    }

    private fun deleteFeedback(feedback: FeedbackMarmita) {
        val index = feedbacks.indexOf(feedback)
        feedbacks.removeAt(index)
        adapter.notifyItemRemoved(index)
        deleteFeedbackFromDatabase(feedback)
    }

    private fun deleteFeedbackFromDatabase(feedback: FeedbackMarmita) {
        val db = FirebaseFirestore.getInstance()
        db.collection("feedbacks").document(feedback.idFeedBackMarmita)
            .delete()
            .addOnSuccessListener {
                Log.d(TAG, "Feedback excluído com sucesso.")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Erro ao excluir feedback", e)
            }
    }

    private fun loadFeedbacksFromDatabase() {
        val db = FirebaseFirestore.getInstance()
        db.collection("feedbacks")
            .whereEqualTo("marmitaId", marmitaId)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val feedback = document.toObject(FeedbackMarmita::class.java)
                    feedbacks.add(feedback)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Erro ao carregar feedbacks", exception)
            }
    }

    private fun getCurrentUser(): Pair<String, String>? {
        val sharedPreferences = requireContext().getSharedPreferences(
            "com.example.projeto_final_mobile",
            Context.MODE_PRIVATE
        )

        val email = sharedPreferences.getString("email", null)
        val nome = sharedPreferences.getString("nome", null)

        // Logs para verificar se os dados de login estão chegando
        Log.d("LoginDataCheck", "Email: $email")
        Log.d("LoginDataCheck", "Nome: $nome")

        return if (email != null && nome != null) {
            Pair(nome, email)
        } else {
            null
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        marmitaId = arguments?.getString("marmitaId") ?: ""
        marmitaNome = arguments?.getString("marmitaNome") ?:""
        marmitaDescricao = arguments?.getString("marmitaDescricao")?:""
        loadFeedbacksFromDatabase()

        val recyclerView = binding.feedbackRecyclerView
        recyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager

        adapter = FeedbackAdapter(feedbacks) { feedback ->
            val feedbackInput = EditText(requireContext()).apply {
                setText(feedback.textFeedBackMarmita)
            }
            AlertDialog.Builder(requireContext())
                .setTitle("Editar Feedback")
                .setView(feedbackInput)
                .setPositiveButton("Salvar") { dialog, _ ->
                    val newFeedbackText = feedbackInput.text.toString()
                    if (newFeedbackText.isNotBlank()) {
                        feedback.textFeedBackMarmita = newFeedbackText
                        val feedbackIndex = feedbacks.indexOf(feedback)
                        adapter.notifyItemChanged(feedbackIndex)
                        Toast.makeText(requireContext(), "Feedback atualizado!", Toast.LENGTH_SHORT)
                            .show()
                        editFeedbackInDatabase(feedback)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Por favor, insira um feedback.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                .setNegativeButton("Cancelar") { dialog, _ ->
                    dialog.cancel()
                }
                .show()
        }
        binding.feedbackRecyclerView.adapter = adapter

        val feedbacks =
            arguments?.getParcelableArray("feedbacks")?.toList() as? List<FeedbackMarmita>
        if (feedbacks != null) {
            this.feedbacks.clear()
            this.feedbacks.addAll(feedbacks)
            adapter.notifyDataSetChanged()
        }
        binding.nomeMarmita.text = marmitaNome
        binding.descricaoMarmita.text = marmitaDescricao

        // Log para verificar se um usuário está logado
        val currentUser = getCurrentUser()
        if (currentUser != null) {
            Log.d("UserStatus", "Usuário logado: ${currentUser.second}")
        } else {
            Log.d("UserStatus", "Nenhum usuário logado")
        }

        binding.submitFeedbackButton.setOnClickListener {
            val feedbackInput = EditText(requireContext())
            val currentUser = getCurrentUser()
            if (currentUser != null) {
                AlertDialog.Builder(requireContext())
                    .setTitle("Feedback")
                    .setView(feedbackInput)
                    .setPositiveButton("Enviar") { dialog, _ ->
                        val feedbackText = feedbackInput.text.toString()
                        if (feedbackText.isNotBlank()) {
                            val newFeedback = FeedbackMarmita(
                                textFeedBackMarmita = feedbackText,
                                quardaIdUser = currentUser.first,
                                quardaIdmarmita = marmitaId
                            )
                            this.feedbacks.add(newFeedback)
                            adapter.notifyItemInserted(this.feedbacks.size - 1)
                            addFeedbackToDatabase(newFeedback)
                            Toast.makeText(
                                requireContext(),
                                "Feedback enviado!",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Por favor, insira um feedback.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    .setNegativeButton("Cancelar") { dialog, _ ->
                        dialog.cancel()
                    }
                    .show()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Por favor, faça login para enviar feedback.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
