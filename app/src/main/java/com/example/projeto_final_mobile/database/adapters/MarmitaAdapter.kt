package com.example.projeto_final_mobile.database.adapters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projeto_final_mobile.fragments.DescricaoFragment
import com.example.projeto_final_mobile.R
import com.projeto_final_mobile.database.models.Marmita

// Adaptador para o RecyclerView de Marmitas
class MarmitaAdapter(
    private val fragmentManager: FragmentManager,
    private val onItemClickListener: (Marmita) -> Unit
) : RecyclerView.Adapter<MarmitaAdapter.MarmitaViewHolder>() {
   private var itens_marmitas: List<Marmita> = ArrayList()

    // ViewHolder para cada item da lista de Marmitas
    inner class MarmitaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nomeMarmita = itemView.findViewById<TextView>(R.id.itemtTextViewmarmita)
        val precoMarmita = itemView.findViewById<TextView>(R.id.itemtTextViewmarmitaPrice)
        val imagemMarmita = itemView.findViewById<ImageView>(R.id.itemImageViewmarmitaria)

        // Método para vincular os dados de uma Marmita ao ViewHolder
        fun bind(marmita: Marmita){
            nomeMarmita.text = marmita.nome
            precoMarmita.text = marmita.preco.toString()
            Glide.with(itemView)
                .load(marmita.imagem_marmita)
                .into(imagemMarmita)
            itemView.setOnClickListener {
                Log.d("Testando Marmita Id MarmitaAdapter",marmita.marmitaId)
                onItemClickListener(marmita)
            }
        }
    }

    // Método para criar o ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarmitaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_da_lista_marmita, parent, false)
        return MarmitaViewHolder(view)
    }

    // Método para vincular os dados de uma Marmita ao ViewHolder
    override fun onBindViewHolder(holder: MarmitaViewHolder, position: Int) {
        holder.bind(itens_marmitas[position])
    }

    // Método para obter a quantidade de itens na lista de Marmitas
    override fun getItemCount(): Int {
        return itens_marmitas.size
    }

    // Método para definir o conjunto de dados das Marmitas
    fun setDataSet(marmitas: List<Marmita>){
        this.itens_marmitas = marmitas
        notifyDataSetChanged()
    }
}