package com.example.projeto_final_mobile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ComentariosAdapter(private val myDataset: List<ComentariosUsuario>) :
    RecyclerView.Adapter<ComentariosAdapter.MyViewHolder>() {

    class MyViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false) as TextView
        return MyViewHolder(textView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val comentario = myDataset[position]
        holder.textView.text = "${comentario.nomeUsuario}: ${comentario.comentario}"
    }

    override fun getItemCount() = myDataset.size
}