package youcef.pharmacie

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class pharmacieProfile : AppCompatActivity() {

    private fun openPage(activity: Class<*>) {
        val intent = Intent(this, activity)
        startActivity(intent)
        overridePendingTransition(
            android.R.anim.fade_in,
            android.R.anim.fade_out
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pharmacie_profile)

        val bottomMenu = findViewById<android.view.View>(R.id.bottomMenu)

        val btnVentes = bottomMenu.findViewById<Button>(R.id.btn_ventes)
        val btnTraitement = bottomMenu.findViewById<Button>(R.id.btn_traitement)
        val btnProduits = bottomMenu.findViewById<Button>(R.id.btn_produits)
        val btnProfil = bottomMenu.findViewById<Button>(R.id.btn_profil)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        // 💚 highlight current page (Profil)
        val greenColor = ContextCompat.getColor(this, R.color.green)
        btnProfil.setTextColor(greenColor)

        // 🔥 logout
        btnLogout.setOnClickListener {
            Toast.makeText(this, "Déconnexion...", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, DashboardActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        // 🔵 navigation (same style openPage)
        btnVentes.setOnClickListener {
            openPage(DashboardActivity::class.java)
        }

        btnTraitement.setOnClickListener {
            openPage(tratmentActivity::class.java)
        }

        btnProduits.setOnClickListener {
            openPage(ProduitsActivity::class.java)
        }

        btnProfil.setOnClickListener {
            Toast.makeText(this, "Déjà sur Profil", Toast.LENGTH_SHORT).show()
        }
    }
}