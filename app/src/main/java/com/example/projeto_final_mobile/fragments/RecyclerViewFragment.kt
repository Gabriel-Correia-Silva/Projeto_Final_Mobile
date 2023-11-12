package com.example.projeto_final_mobile.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto_final_mobile.Dao.MarmitariaDAO
import com.example.projeto_final_mobile.R
import com.example.projeto_final_mobile.adapter.MarmitariaAdapter
import com.example.projeto_final_mobile.utils.DBHelper


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
        val dbHelper = DBHelper(context ?: return)
        val marmitariaDAO = MarmitariaDAO(dbHelper)
        val dados = marmitariaDAO.obterMarmitarias()

        val meuRecyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewListfragment)
        meuRecyclerView?.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = MarmitariaAdapter(dados)
        }
    }
}
