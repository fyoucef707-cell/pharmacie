package youcef.pharmacie

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ProduitsActivity : AppCompatActivity() {

    private lateinit var container: LinearLayout
    private var editingView: View? = null

    private val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->

        if (result.resultCode == RESULT_OK) {

            val data = result.data

            val nom = data?.getStringExtra("nom") ?: ""
            val desc = data?.getStringExtra("desc") ?: ""
            val prix = data?.getStringExtra("prix") ?: ""
            val stock = data?.getStringExtra("stock") ?: ""

            if (editingView != null) {

                val tvNom = editingView!!.findViewById<TextView>(R.id.tvNom)
                val tvDesc = editingView!!.findViewById<TextView>(R.id.tvDesc)
                val tvPrix = editingView!!.findViewById<TextView>(R.id.tvPrix)
                val tvStock = editingView!!.findViewById<TextView>(R.id.tvStock)

                tvNom.text = nom
                tvDesc.text = desc
                tvPrix.text = "$prix DA"
                tvStock.text = "Stock: $stock"

                editingView = null

            } else {
                addProduit(nom, desc, prix, stock)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produits)

        container = findViewById(R.id.containerProduits)

        val fab = findViewById<FloatingActionButton>(R.id.btnAddProduit)

        val menu = findViewById<View>(R.id.bottomMenu)

        val btnVentes = menu.findViewById<Button>(R.id.btn_ventes)
        val btnProduits = menu.findViewById<Button>(R.id.btn_produits)
        val btnTraitement = menu.findViewById<Button>(R.id.btn_traitement)
        val btnProfil = menu.findViewById<Button>(R.id.btn_profil)

        // 🔥 Active menu
        setActiveMenu(btnProduits)

        fab.setOnClickListener {
            editingView = null
            val intent = Intent(this, AddMedicamentActivity::class.java)
            launcher.launch(intent)
        }

        btnVentes.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
            slide()
        }

        btnTraitement.setOnClickListener {
            startActivity(Intent(this, tratmentActivity::class.java))
            slide()
        }

        btnProduits.setOnClickListener {
            Toast.makeText(this, "Déjà sur Produits", Toast.LENGTH_SHORT).show()
        }

        btnProfil.setOnClickListener {
            startActivity(Intent(this, pharmacieProfile::class.java))
            slide()
        }
    }

    private fun addProduit(nom: String, desc: String, prix: String, stock: String) {

        val view = layoutInflater.inflate(R.layout.item_produit, container, false)

        val tvNom = view.findViewById<TextView>(R.id.tvNom)
        val tvDesc = view.findViewById<TextView>(R.id.tvDesc)
        val tvPrix = view.findViewById<TextView>(R.id.tvPrix)
        val tvStock = view.findViewById<TextView>(R.id.tvStock)

        val btnEdit = view.findViewById<Button>(R.id.btnEdit)
        val btnDelete = view.findViewById<Button>(R.id.btnDelete)

        tvNom.text = nom
        tvDesc.text = desc
        tvPrix.text = "$prix DA"
        tvStock.text = "Stock: $stock"

        btnDelete.setOnClickListener {
            container.removeView(view)
        }

        btnEdit.setOnClickListener {
            editingView = view

            val intent = Intent(this, AddMedicamentActivity::class.java)
            intent.putExtra("nom", nom)
            intent.putExtra("desc", desc)
            intent.putExtra("prix", prix)
            intent.putExtra("stock", stock)

            launcher.launch(intent)
        }

        container.addView(view)
    }

    // 🎯 animation
    private fun slide() {
        overridePendingTransition(
            android.R.anim.slide_in_left,
            android.R.anim.slide_out_right
        )
    }

    // 🔥 menu active cleaner way
    private fun setActiveMenu(activeButton: Button) {

        val menu = findViewById<View>(R.id.bottomMenu)

        val btnVentes = menu.findViewById<Button>(R.id.btn_ventes)
        val btnProduits = menu.findViewById<Button>(R.id.btn_produits)
        val btnTraitement = menu.findViewById<Button>(R.id.btn_traitement)
        val btnProfil = menu.findViewById<Button>(R.id.btn_profil)

        val gray = ContextCompat.getColor(this, android.R.color.darker_gray)
        val green = ContextCompat.getColor(this, R.color.green)

        btnVentes.setTextColor(gray)
        btnProduits.setTextColor(gray)
        btnTraitement.setTextColor(gray)
        btnProfil.setTextColor(gray)

        activeButton.setTextColor(green)
    }
}