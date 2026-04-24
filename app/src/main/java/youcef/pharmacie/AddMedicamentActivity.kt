package youcef.pharmacie

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddMedicamentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ajouter_un_medicaments)

        val etNom = findViewById<EditText>(R.id.etNom)
        val etPrix = findViewById<EditText>(R.id.etPrix)
        val etDesc = findViewById<EditText>(R.id.etDescription)
        val etStock = findViewById<EditText>(R.id.etPosologie)

        val btnAjouter = findViewById<Button>(R.id.btnAjouter)

        val oldNom = intent.getStringExtra("nom")
        val oldDesc = intent.getStringExtra("desc")
        val oldPrix = intent.getStringExtra("prix")
        val oldStock = intent.getStringExtra("stock")

        if (oldNom != null) {
            etNom.setText(oldNom)
            etDesc.setText(oldDesc)
            etPrix.setText(oldPrix)
            etStock.setText(oldStock)
        }

        btnAjouter.setOnClickListener {

            val nom = etNom.text.toString().trim()
            val prix = etPrix.text.toString().trim()
            val desc = etDesc.text.toString().trim()
            val stock = etStock.text.toString().trim()

            if (nom.isEmpty() || prix.isEmpty()) {
                Toast.makeText(this, "Remplir les champs obligatoires", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val resultIntent = Intent().apply {
                putExtra("nom", nom)
                putExtra("desc", desc)
                putExtra("prix", prix)
                putExtra("stock", stock)
            }

            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}