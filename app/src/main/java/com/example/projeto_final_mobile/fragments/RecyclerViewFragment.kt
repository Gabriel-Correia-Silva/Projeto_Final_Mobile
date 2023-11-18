package com.example.projeto_final_mobile.fragments

import MarmitariaAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto_final_mobile.Dao.MarmitariaDAO
import com.example.projeto_final_mobile.R
import com.example.projeto_final_mobile.utils.DBHelper
import com.example.projeto_final_mobile.models.Marmitaria

class RecyclerViewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_recycler_view_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configura o RecyclerView
        val dbHelper = DBHelper(requireContext())
        val marmitariaDAO = MarmitariaDAO(dbHelper)
        marmitariaDAO.inicializarDados()
        val dados = marmitariaDAO.obterMarmitarias()

        Log.d("MarmitariaFragment", "Dados: $dados")

        val meuRecyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewListfragment)
        meuRecyclerView?.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = MarmitariaAdapter(requireContext(), dados, object : MarmitariaAdapter.OnItemClickListener {
                override fun onItemClick(marmitaria: Marmitaria) {
                    Log.d("MarmitariaFragment", "Clicou no item: $marmitaria")
                    val intent = Intent(requireContext(), ItemMarmita::class.java)

                    // Adiciona o objeto Marmitaria como um extra
                    intent.putExtra("marmitaria", marmitaria)

                    // Inicia a atividade ItemMarmita
                    startActivity(intent)
                }
            })
        }
    }
}