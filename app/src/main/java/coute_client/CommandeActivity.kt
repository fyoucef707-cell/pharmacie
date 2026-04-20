package coute_client

import android.R
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlin.jvm.java

class CommandeActivity : AppCompatActivity() {
    private fun openPage(activity: Class<*>) {
        val intent = Intent(this, activity)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(youcef.pharmacie.R.layout.activity_commande)

        val btnAccueil = findViewById<Button>(youcef.pharmacie.R.id.btnAccueil)
        val btnCarte = findViewById<Button>(youcef.pharmacie.R.id.btnCarte)
        var btnCommande = findViewById<Button>(youcef.pharmacie.R.id.btnCommande)
        val btnProfil = findViewById<Button>(youcef.pharmacie.R.id.btnProfil)

        btnCommande = findViewById<Button>(youcef.pharmacie.R.id.btnCommande)
        val greenColor = ContextCompat.getColor(this, youcef.pharmacie.R.color.green)
        btnCommande.setTextColor(greenColor)

        btnCarte.setOnClickListener {
            openPage(MapsActivity::class.java)
        }

        btnAccueil.setOnClickListener {
            openPage(AccueilActivity::class.java)
        }

        btnProfil.setOnClickListener {
            openPage(ProfilActiviti::class.java)
        }
    }
}