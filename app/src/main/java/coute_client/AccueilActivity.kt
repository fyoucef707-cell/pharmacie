package coute_client

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import youcef.pharmacie.R

class AccueilActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accueil)

        val btnAccueil = findViewById<Button>(R.id.btnAccueil)
        val btnCarte = findViewById<Button>(R.id.btnCarte)
        val btnCommande = findViewById<Button>(R.id.btnCommande)
        val btnProfil = findViewById<Button>(R.id.btnProfil)

        val searchInput = findViewById<EditText>(R.id.searchInput)

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

        setActive(btnAccueil)

        btnCarte.setOnClickListener { openPage(MapsActivity::class.java) }
        btnCommande.setOnClickListener { openPage(CommandeActivity::class.java) } // ✅ مصححة
        btnProfil.setOnClickListener { openPage(ProfilActivity::class.java) } // ✅ مصححة
    }

    private fun handleSearch(query: String) {

        when (query.lowercase()) {

            "pharmacie", "map", "carte" ->
                openPage(MapsActivity::class.java)

            "commande", "orders" ->
                openPage(CommandeActivity::class.java)

            "profil", "profile" ->
                openPage(ProfilActivity::class.java)

            else ->
                openPage(MapsActivity::class.java)
        }
    }

    // ================= NAVIGATION =================
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