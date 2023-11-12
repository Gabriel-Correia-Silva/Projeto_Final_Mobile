package com.example.projeto_final_mobile.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto_final_mobile.R

class PratoMarmitariaAdapter(private val myDataset: Array<String>, private val onClick: (String) -> Unit) :
    RecyclerView.Adapter<PratoMarmitariaAdapter.MyViewHolder>() {

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_item_marmita, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = myDataset[position]
        // Aqui você pode configurar a view do seu item usando holder.view
        holder.itemView.setOnClickListener { onClick(item) }
    }

    override fun getItemCount() = myDataset.size
}