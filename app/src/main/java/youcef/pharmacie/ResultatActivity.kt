package youcef.pharmacie

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ResultatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultat)

        val recycler = findViewById<RecyclerView>(R.id.recyclerView)

        recycler.layoutManager = LinearLayoutManager(this)

        val query = intent.getStringExtra("query")?.lowercase() ?: ""

        val filteredList = if (query.isEmpty()) {
            MedicamentData.list
        } else {
            MedicamentData.list.filter {
                it.nom.lowercase().contains(query)
            }
        }

        val adapter = MedicamentAdapter(filteredList) { medicament ->

            val intent = Intent(this, CommandeActivity::class.java)
            intent.putExtra("produit_name", medicament.nom)
            intent.putExtra("prix", medicament.prix)
            startActivity(intent)
        }

        recycler.adapter = adapter
    }
}