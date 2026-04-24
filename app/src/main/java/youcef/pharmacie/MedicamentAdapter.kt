package youcef.pharmacie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MedicamentAdapter(
    private val list: List<Medicament>,
    private val onClick: (Medicament) -> Unit
) : RecyclerView.Adapter<MedicamentAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.tvName)
        val price: TextView = view.findViewById(R.id.tvPrice)
        val image: ImageView = view.findViewById(R.id.imgMed)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_medicament, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val medicament = list[position]

        holder.name.text = medicament.nom
        holder.price.text = "${medicament.prix} DA"
        holder.image.setImageResource(medicament.image)

        holder.itemView.setOnClickListener {
            onClick(medicament)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}