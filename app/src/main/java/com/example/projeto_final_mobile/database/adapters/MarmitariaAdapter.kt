package com.example.projeto_final_mobile.database.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.room.PrimaryKey
import com.bumptech.glide.Glide
import com.example.projeto_final_mobile.R
import com.projeto_final_mobile.database.models.Marmitaria
import com.squareup.picasso.Picasso

class MarmitariaAdapter(private val onItemClickListener: (Marmitaria) -> Unit) : RecyclerView.Adapter<MarmitariaAdapter.MarmitariaViewHolder>() {
    private var itens_marmitaria: List<Marmitaria> = ArrayList()

    inner class MarmitariaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nome_marmitaria = itemView.findViewById<TextView>(R.id.itemtTextViewmarmitaria)
        val preco_medio_marmitaria = itemView.findViewById<TextView>(R.id.itemtTextViewmarmitariaPrice)
        val imagem_marmitaria = itemView.findViewById<ImageView>(R.id.itemImageViewmarmitaria)

        fun bind(marmitaria: Marmitaria){
            nome_marmitaria.text = marmitaria.nome
            preco_medio_marmitaria.text = marmitaria.precomedio.toString()
            Glide.with(itemView)
                .load(marmitaria.imagelogo)
                .into(imagem_marmitaria)
            itemView.setOnClickListener {
                onItemClickListener(marmitaria)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarmitariaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_para_mostrar_marmitarias, parent, false)
        return MarmitariaViewHolder(view)
    }

    override fun onBindViewHolder(holder: MarmitariaViewHolder, position: Int) {
        holder.bind(itens_marmitaria[position])
    }

    override fun getItemCount(): Int {
        return itens_marmitaria.size
    }

    fun setDataSet(marmitaria: List<Marmitaria>){
        this.itens_marmitaria = marmitaria
        notifyDataSetChanged()
    }
}