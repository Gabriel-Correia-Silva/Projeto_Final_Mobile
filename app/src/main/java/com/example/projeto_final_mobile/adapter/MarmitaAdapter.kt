package menu.projeto_final_mobile.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto_final_mobile.R
import com.projeto_final_mobile.database.models.Marmita

class MarmitaAdapter(private val marmitaList: List<Marmita>) : RecyclerView.Adapter<MarmitaAdapter.MarmitaViewHolder>() {

    class MarmitaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewMarmita: ImageView = itemView.findViewById(R.id.imageViewmarmita)
        val nameMarmita: TextView = itemView.findViewById(R.id.nameMarmita)
        val priceMarmita: TextView = itemView.findViewById(R.id.priceMarmita)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarmitaViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_marmita, parent, false)
        return MarmitaViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MarmitaViewHolder, position: Int) {
        val currentItem = marmitaList[position]
        // Aqui você pode definir a imagem para imageViewMarmita
        holder.nameMarmita.text = currentItem.nome
        holder.priceMarmita.text = currentItem.preco.toString()
    }

    override fun getItemCount() = marmitaList.size
}