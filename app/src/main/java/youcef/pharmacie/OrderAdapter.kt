package  youcef.pharmacie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class Order(
    val cmd: String,
    val name: String,
    val medications: String,
    val time: String
)

// ✅ Adapter واحد فقط
class OrderAdapter(
    private val orders: List<Order>,
    private val onAction: (Order, String) -> Unit
) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCmd: TextView = itemView.findViewById(R.id.tvCmd)
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvMedications: TextView = itemView.findViewById(R.id.tvMedications)
        val tvTime: TextView = itemView.findViewById(R.id.tvTime)
        val btnAccept: Button = itemView.findViewById(R.id.btnAccept)
        val btnReject: Button = itemView.findViewById(R.id.btnReject)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]

        holder.tvCmd.text = order.cmd
        holder.tvName.text = order.name
        holder.tvMedications.text = order.medications
        holder.tvTime.text = order.time

        holder.btnAccept.setOnClickListener {
            onAction(order, "accept")
        }

        holder.btnReject.setOnClickListener {
            onAction(order, "reject")
        }
    }

    override fun getItemCount(): Int = orders.size
}