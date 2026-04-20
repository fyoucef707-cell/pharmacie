package youcef.pharmacie

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_vente)

        val title = findViewById<TextView>(R.id.titleDashboard)
        title.text = "Tableau de bord"

        val recette = findViewById<TextView>(R.id.tvRecette)
        recette.text = "RECETTE"

        val bottomMenu = findViewById<LinearLayout>(R.id.bottomMenu)

        val btnVentes = bottomMenu.findViewById<Button>(R.id.btn_ventes)
        val btnProduits = bottomMenu.findViewById<Button>(R.id.btn_produits)
        val btnTraitement = bottomMenu.findViewById<Button>(R.id.btn_traitement)
        val btnProfil = bottomMenu.findViewById<Button>(R.id.btn_profil)

        // 🎯 ACTIVE MENU (Ventes)
        setActiveMenu(btnVentes)

        btnVentes.setOnClickListener {
            Toast.makeText(this, "Déjà sur Dashboard", Toast.LENGTH_SHORT).show()
        }

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
    }

    private fun slide() {
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
    }

    // 💚 CLEAN MENU (TEXT COLOR like ProfilActiviti)
    private fun setActiveMenu(active: Button) {

        val btnVentes = findViewById<Button>(R.id.btn_ventes)
        val btnProduits = findViewById<Button>(R.id.btn_produits)
        val btnTraitement = findViewById<Button>(R.id.btn_traitement)
        val btnProfil = findViewById<Button>(R.id.btn_profil)

        val green = ContextCompat.getColor(this, R.color.green)
        val gray = ContextCompat.getColor(this, android.R.color.darker_gray)

        btnVentes.setTextColor(gray)
        btnProduits.setTextColor(gray)
        btnTraitement.setTextColor(gray)
        btnProfil.setTextColor(gray)

        active.setTextColor(green)
    }
}