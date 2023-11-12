package com.example.projeto_final_mobile.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto_final_mobile.fragments.ConteudoMarmitaria
import com.example.projeto_final_mobile.R
import com.example.projeto_final_mobile.models.Marmitaria

class MarmitariaAdapterAdapter(private val marmitarias: List<Marmitaria>) :
    RecyclerView.Adapter<MarmitariaAdapterAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.textView)
        // Adicione mais views se necessário
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_item_layout_marmitaria, parent, false)
        return MyViewHolder(textView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val marmitaria = marmitarias[position]
        holder.textView.text = marmitaria.nome.toString()

        // Adiciona um OnClickListener
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, ConteudoMarmitaria::class.java).apply {
                putExtra("ITEM_INDEX", position)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = marmitarias.size
}