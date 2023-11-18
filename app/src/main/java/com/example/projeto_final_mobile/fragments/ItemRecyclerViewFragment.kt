package com.example.projeto_final_mobile.fragments

import MarmitariaAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto_final_mobile.R
import com.example.projeto_final_mobile.Dao.MarmitariaDAO
import com.example.projeto_final_mobile.R.id.recyclerViewListfragment
import com.example.projeto_final_mobile.utils.DBHelper
import com.example.projeto_final_mobile.models.Marmitaria
import android.content.Intent

class ItemRecyclerViewFragment : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_recycle_view_fragment)

        val dbHelper = DBHelper(this)
        val marmitariaDAO = MarmitariaDAO(dbHelper)

        marmitariaDAO.inicializarDados()

        val marmitarias = marmitariaDAO.obterMarmitarias()

        val adapter = MarmitariaAdapter(this, marmitarias, object : MarmitariaAdapter.OnItemClickListener {
            override fun onItemClick(marmitaria: Marmitaria) {
                val intent = Intent(this@ItemRecyclerViewFragment, ItemMarmita::class.java)
                intent.putExtra("marmitaria", marmitaria)
                startActivity(intent)
            }
        })

        val meuRecyclerView: RecyclerView = findViewById(recyclerViewListfragment)
        meuRecyclerView.adapter = adapter
    }
}