package youcef.pharmacie

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class tratmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tratment)

        val btnAccept1 = findViewById<Button?>(R.id.btnAccept1)
        val btnRefuse1 = findViewById<Button?>(R.id.btnRefuse1)

        btnAccept1?.setOnClickListener {
            Toast.makeText(this, "CMD #125 accepté", Toast.LENGTH_SHORT).show()
        }

        btnRefuse1?.setOnClickListener {
            Toast.makeText(this, "CMD #125 refusé", Toast.LENGTH_SHORT).show()
        }

        val bottomMenu = findViewById<View>(R.id.bottomMenu)

        val btnVentes = bottomMenu.findViewById<Button>(R.id.btn_ventes)
        val btnProduits = bottomMenu.findViewById<Button>(R.id.btn_produits)
        val btnTraitement = bottomMenu.findViewById<Button>(R.id.btn_traitement)
        val btnProfil = bottomMenu.findViewById<Button>(R.id.btn_profil)

        setActiveMenu(btnTraitement)

        btnVentes.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
            slide()
        }

        btnProduits.setOnClickListener {
            startActivity(Intent(this, ProduitsActivity::class.java))
            slide()
        }

        btnTraitement.setOnClickListener {
            Toast.makeText(this, "Déjà sur Traitement", Toast.LENGTH_SHORT).show()
        }

        btnProfil.setOnClickListener {
            startActivity(Intent(this, pharmacieProfile::class.java))
            slide()
        }
    }

    private fun slide() {
        overridePendingTransition(
            android.R.anim.slide_in_left,
            android.R.anim.slide_out_right
        )
    }

    private fun setActiveMenu(active: Button) {

        val bottomMenu = findViewById<View>(R.id.bottomMenu)

        val btnVentes = bottomMenu.findViewById<Button>(R.id.btn_ventes)
        val btnProduits = bottomMenu.findViewById<Button>(R.id.btn_produits)
        val btnTraitement = bottomMenu.findViewById<Button>(R.id.btn_traitement)
        val btnProfil = bottomMenu.findViewById<Button>(R.id.btn_profil)

        val green = ContextCompat.getColor(this, R.color.green)
        val gray = ContextCompat.getColor(this, android.R.color.darker_gray)

        btnVentes.setTextColor(gray)
        btnProduits.setTextColor(gray)
        btnTraitement.setTextColor(gray)
        btnProfil.setTextColor(gray)

        active.setTextColor(green)
    }
}