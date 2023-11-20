package com.projeto_final_mobile.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projeto_final_mobile.R
import com.projeto_final_mobile.database.models.Marmitaria


class MarmitariaAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items : List<Marmitaria> = arrayListOf()

    // Cria uma nova instância do ViewHolder inflando o layout do item da lista
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MarmitaViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.activity_item_recycle_view_marmitaria,parent,false)
        )
    }

    // Vincula os dados do item da lista ao ViewHolder
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is MarmitaViewHolder ->{
                holder.bind(items[position])
            }
        }
    }

    // Retorna o número de itens na lista
    override fun getItemCount(): Int {
        return items.size
    }

    // Define a lista de itens do adapter
    fun setList(marmitarialist: List<Marmitaria>) {
        this.items = marmitarialist
    }

    // ViewHolder que representa cada item da lista
    class MarmitaViewHolder constructor(
        itemView: View
    ): RecyclerView.ViewHolder(itemView){
        private val nomeMarmitaria: TextView = itemView.findViewById(R.id.itemtTextViewFragment)
        private val precoMedio: TextView = itemView.findViewById(R.id.priceTextViewFragment)
        private val logo: ImageView = itemView.findViewById(R.id.itemImageViewFragment)

        // Vincula os dados da Marmitaria ao ViewHolder
        fun bind(marmitaria: Marmitaria){
            nomeMarmitaria.text = marmitaria.nome
            precoMedio.text = marmitaria.precomedio.toString()
            Glide.with(itemView.context).load(marmitaria.imagelogo).into(logo)
        }
    }
}