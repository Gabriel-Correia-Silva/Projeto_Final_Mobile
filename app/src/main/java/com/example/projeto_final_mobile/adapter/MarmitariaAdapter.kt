package com.example.projeto_final_mobile.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto_final_mobile.Dao.MarmitariaDAO
import com.example.projeto_final_mobile.R
import com.example.projeto_final_mobile.models.Marmitaria

class MarmitariaAdapter(private val marmitarias: List<Marmitaria>) : RecyclerView.Adapter<MarmitariaAdapter.MarmitariaViewHolder>() {

    class MarmitariaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nomeTextView: TextView = itemView.findViewById(R.id.nameMarmitaria) // Altere o id para 'textView'
        // Adicione mais views se necessário
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarmitariaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_layout_marmitaria, parent, false)
        return MarmitariaViewHolder(view)
    }

    override fun onBindViewHolder(holder: MarmitariaViewHolder, position: Int) {
        val marmitaria = marmitarias[position]
        holder.nomeTextView.text = marmitaria.nome.toString()
        // Configure mais views se necessário
    }

    override fun getItemCount() = marmitarias.size
}

