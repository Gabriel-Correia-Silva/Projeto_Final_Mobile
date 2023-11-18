package com.example.projeto_final_mobile.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto_final_mobile.R
import com.example.projeto_final_mobile.models.Marmita

class PratoMarmitariaAdapter(private val myDataset: List<Marmita>, private val onClick: (Marmita) -> Unit) :
    RecyclerView.Adapter<PratoMarmitariaAdapter.MyViewHolder>() {

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val nome: TextView = view.findViewById(R.id.nameMarmita)
        val preco: TextView = view.findViewById(R.id.priceMarmita)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_item_marmita, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val marmita = myDataset[position]
        holder.nome.text = marmita.nome
        holder.preco.text = marmita.preco.toString()
        holder.itemView.setOnClickListener { onClick(marmita) }
    }

    override fun getItemCount() = myDataset.size
}