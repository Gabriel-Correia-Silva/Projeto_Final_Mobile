package com.projeto_final_mobile.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto_final_mobile.R
import com.projeto_final_mobile.adapter.MarmitariaAdapter


class RecyclerViewFragment : Fragment() {

    private lateinit var marmitariaAdapter: MarmitariaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_recycler_view_marmitaria, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.marmitariaAdapter = MarmitariaAdapter()

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewListfragment)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = this.marmitariaAdapter

        addDataSource()
    }

    private fun addDataSource() {
        val dataSource = DataSource.createDataSet()
        marmitariaAdapter.setList(dataSource)
    }
}