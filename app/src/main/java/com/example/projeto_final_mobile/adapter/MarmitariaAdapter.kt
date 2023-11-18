// Importe suas bibliotecas necessárias
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto_final_mobile.R
import com.example.projeto_final_mobile.models.Marmitaria


// Crie uma classe Adapter para a RecyclerView
class MarmitariaAdapter(
    public val context: Context,
    public val listaDeMarmitarias: List<Marmitaria>,
    public val listener: OnItemClickListener
) : RecyclerView.Adapter<MarmitariaAdapter.MarmitariaViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(marmitaria: Marmitaria)
    }

    class MarmitariaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewNome: TextView = itemView.findViewById(R.id.itemtTextViewFragment)
        val textViewPrecoMedio: TextView = itemView.findViewById(R.id.itemtTextViewFragmentPrice)
        val imageViewMarmitaria: ImageView = itemView.findViewById(R.id.itemImageViewFragment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarmitariaViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_item_recycle_view_fragment, parent, false)
        return MarmitariaViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MarmitariaViewHolder, position: Int) {
        val marmitaria = listaDeMarmitarias[position]
        holder.itemView.setOnClickListener {
            listener.onItemClick(marmitaria)
        }
        holder.textViewNome.text = marmitaria.nome.toString()
        holder.textViewPrecoMedio.text = marmitaria.precoMedio.toString()
    }

    override fun getItemCount(): Int {
        return listaDeMarmitarias.size
    }
}
