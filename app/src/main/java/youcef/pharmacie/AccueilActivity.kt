package youcef.pharmacie

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class AccueilActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accueil)

        val btnAccueil = findViewById<Button>(R.id.btnAccueil)
        val btnCarte = findViewById<Button>(R.id.btnCarte)
        val btnCommande = findViewById<Button>(R.id.btnCommande)
        val btnProfil = findViewById<Button>(R.id.btnProfil)

        val searchInput = findViewById<EditText>(R.id.searchInput)

        setActive(btnAccueil)

        btnCarte.setOnClickListener {
            openPage(MapsActivity::class.java)
        }

        btnCommande.setOnClickListener {
            openPage(CommandeActivity::class.java)
        }

        btnProfil.setOnClickListener {
            openPage(ProfilActivity::class.java)
        }

        searchInput.setOnEditorActionListener { _, actionId, _ ->

            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                actionId == EditorInfo.IME_ACTION_DONE
            ) {

                val query = searchInput.text.toString().trim()

                if (query.isNotEmpty()) {
                    handleSearch(query)
                }

                true
            } else false
        }
    }

    private fun handleSearch(query: String) {

        when (query.lowercase()) {

            "pharmacie", "map", "carte" ->
                openMapsWithDefaultPharmacy()

            "pharmacie centrale" ->
                openSpecificPharmacy(
                    "Pharmacie Centrale",
                    35.6985,
                    -0.6350
                )

            "commande", "orders" ->
                openPage(CommandeActivity::class.java)

            "profil", "profile" ->
                openPage(ProfilActivity::class.java)

            else -> {
                val intent = Intent(this, ResultatActivity::class.java)
                intent.putExtra("query", query)
                startActivity(intent)
            }
        }
    }

    private fun openMapsWithDefaultPharmacy() {

        val intent = Intent(this, MapsActivity::class.java)

        intent.putExtra("name", "Pharmacie")
        intent.putExtra("lat", 35.6985)
        intent.putExtra("lon", -0.6350)

        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    private fun openSpecificPharmacy(name: String, lat: Double, lon: Double) {

        val intent = Intent(this, MapsActivity::class.java)

        intent.putExtra("name", name)
        intent.putExtra("lat", lat)
        intent.putExtra("lon", lon)

        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    private fun openPage(activity: Class<*>) {
        startActivity(Intent(this, activity))
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    private fun setActive(activeBtn: Button) {

        val green = ContextCompat.getColor(this, R.color.green)
        val gray = ContextCompat.getColor(this, android.R.color.darker_gray)

        val buttons = arrayOf(
            findViewById<Button>(R.id.btnAccueil),
            findViewById<Button>(R.id.btnCarte),
            findViewById<Button>(R.id.btnCommande),
            findViewById<Button>(R.id.btnProfil)
        )

        buttons.forEach { it.setTextColor(gray) }
        activeBtn.setTextColor(green)
    }
}