package youcef.pharmacie

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ProduitsActivity : AppCompatActivity() {

    private lateinit var container: LinearLayout
    private var editingView: View? = null

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

        setActiveMenu(btnProduits)

        // ✅ ADD PRODUCT (POPUP)
        fab.setOnClickListener {
            editingView = null
            showAddEditDialog()
        }

        btnVentes.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
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

        btnProduits.setOnClickListener {
            Toast.makeText(this, "Déjà sur Produits", Toast.LENGTH_SHORT).show()
        }
    }

    // 🔥 POPUP ADD / EDIT
    private fun showAddEditDialog(
        viewToEdit: View? = null,
        oldNom: String = "",
        oldDesc: String = "",
        oldPrix: String = "",
        oldStock: String = ""
    ) {

        val dialogView = layoutInflater.inflate(R.layout.ajouter_un_medicaments, null)

        val etNom = dialogView.findViewById<EditText>(R.id.etNom)
        val etDesc = dialogView.findViewById<EditText>(R.id.etDescription)
        val etPrix = dialogView.findViewById<EditText>(R.id.etPrix)
        val etStock = dialogView.findViewById<EditText>(R.id.etPosologie)
        val btnAjouter = dialogView.findViewById<Button>(R.id.btnAjouter)

        // fill if edit
        etNom.setText(oldNom)
        etDesc.setText(oldDesc)
        etPrix.setText(oldPrix)
        etStock.setText(oldStock)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        btnAjouter.setOnClickListener {

            val nom = etNom.text.toString()
            val desc = etDesc.text.toString()
            val prix = etPrix.text.toString()
            val stock = etStock.text.toString()

            if (nom.isNotEmpty()) {

                if (viewToEdit != null) {
                    updateProduit(viewToEdit, nom, desc, prix, stock)
                } else {
                    addProduit(nom, desc, prix, stock)
                }

                dialog.dismiss()
            } else {
                Toast.makeText(this, "Nom obligatoire", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }

    // ➕ ADD PRODUCT
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
            showAddEditDialog(view, nom, desc, prix, stock)
        }

        container.addView(view)
    }

    // ✏ UPDATE PRODUCT
    private fun updateProduit(
        view: View,
        nom: String,
        desc: String,
        prix: String,
        stock: String
    ) {

        view.findViewById<TextView>(R.id.tvNom).text = nom
        view.findViewById<TextView>(R.id.tvDesc).text = desc
        view.findViewById<TextView>(R.id.tvPrix).text = "$prix DA"
        view.findViewById<TextView>(R.id.tvStock).text = "Stock: $stock"
    }

    private fun slide() {
        overridePendingTransition(
            android.R.anim.slide_in_left,
            android.R.anim.slide_out_right
        )
    }

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