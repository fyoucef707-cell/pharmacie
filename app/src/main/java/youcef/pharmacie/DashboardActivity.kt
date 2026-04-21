package youcef.pharmacie

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat

class DashboardActivity : AppCompatActivity() {

    private lateinit var btnVentes: Button
    private lateinit var btnProduits: Button
    private lateinit var btnTraitement: Button
    private lateinit var btnProfil: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vente)

        // ================= TEXT =================
        findViewById<TextView>(R.id.titleDashboard).text = "Tableau de bord"
        findViewById<TextView>(R.id.tvRecette).text = "12 590 DA"

        // ================= MENU =================
        val bottomMenu = findViewById<LinearLayout>(R.id.bottomMenu)

        btnVentes = bottomMenu.findViewById(R.id.btn_ventes)
        btnProduits = bottomMenu.findViewById(R.id.btn_produits)
        btnTraitement = bottomMenu.findViewById(R.id.btn_traitement)
        btnProfil = bottomMenu.findViewById(R.id.btn_profil)

        setActiveMenu(btnVentes)

        btnProduits.setOnClickListener {
            startActivity(Intent(this, ProduitsActivity::class.java))
            slide()
        }

        btnTraitement.setOnClickListener {
            startActivity(Intent(this, tratmentActivity::class.java))
            slide()
        }

        btnProfil.setOnClickListener {
            startActivity(Intent(this, pharmacieProfile::class.java))
            slide()
        }

        // ================= CLIENTS =================
        val clientCard = findViewById<CardView>(R.id.clientCard)
        val clientsSection = findViewById<LinearLayout>(R.id.clientsSection)
        val listClients = findViewById<ListView>(R.id.listClients)

        val clients = listOf(
            "Ahmed" to "0770 11 22 33",
            "Yacine" to "0555 44 33 22",
            "Sara" to "0666 88 77 11",
            "Mohamed" to "0555 12 34 56"
        )

        val adapter = ClientAdapter(this, clients)
        listClients.adapter = adapter

        listClients.setOnItemClickListener { _, _, position, _ ->

            val client = clients[position]

            AlertDialog.Builder(this)
                .setTitle("👤 ${client.first}")
                .setMessage("📞 ${client.second}")
                .setPositiveButton("Appeler") { _, _ ->
                    Toast.makeText(this, "Calling ${client.second}", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Fermer", null)
                .show()
        }

        clientCard.setOnClickListener {
            clientsSection.visibility =
                if (clientsSection.visibility == View.GONE) View.VISIBLE else View.GONE
        }

        // ================= RECETTE POPUP =================
        val recetteCard = findViewById<CardView>(R.id.recetteCard)

        recetteCard.setOnClickListener {

            val dialogView = layoutInflater.inflate(R.layout.dialog_recette, null)

            val totalRecette = dialogView.findViewById<TextView>(R.id.totalRecette)
            val nbVentes = dialogView.findViewById<TextView>(R.id.nbVentes)
            val benefice = dialogView.findViewById<TextView>(R.id.benefice)
            val btnClose = dialogView.findViewById<Button>(R.id.btnClose)

            totalRecette.text = "12 590 DA"
            nbVentes.text = "18 ventes"
            benefice.text = "3 200 DA"

            val dialog = AlertDialog.Builder(this)
                .setView(dialogView)
                .create()

            dialog.show()

            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

            dialog.window?.setLayout(
                (resources.displayMetrics.widthPixels * 0.85).toInt(),
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            btnClose.setOnClickListener {
                dialog.dismiss()
            }
        }
    }

    // ================= UTIL =================

    private fun slide() {
        overridePendingTransition(
            android.R.anim.slide_in_left,
            android.R.anim.slide_out_right
        )
    }

    private fun setActiveMenu(active: Button) {

        val green = ContextCompat.getColor(this, R.color.green)
        val gray = ContextCompat.getColor(this, android.R.color.darker_gray)

        listOf(btnVentes, btnProduits, btnTraitement, btnProfil)
            .forEach { it.setTextColor(gray) }

        active.setTextColor(green)
    }
}
class ClientAdapter(
    private val context: Context,
    private val clients: List<Pair<String, String>>
) : ArrayAdapter<Pair<String, String>>(context, 0, clients) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.popup_clients, parent, false)

        val name = view.findViewById<TextView>(R.id.clientName)
        val phone = view.findViewById<TextView>(R.id.clientPhone)

        val client = clients[position]

        name.text = client.first
        phone.text = client.second

        return view
    }
}