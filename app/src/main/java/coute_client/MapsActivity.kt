package coute_client

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import youcef.pharmacie.R

class MapsActivity : AppCompatActivity() {

    private lateinit var map: MapView
    private var locationOverlay: MyLocationNewOverlay? = null

    private val pharmacy = GeoPoint(35.6985, -0.6350)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Configuration.getInstance().load(
            applicationContext,
            getSharedPreferences("osmdroid", MODE_PRIVATE)
        )

        setContentView(R.layout.activity_maps)

        map = findViewById(R.id.map)

        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setMultiTouchControls(true)

        map.controller.apply {
            setZoom(15.0)
            setCenter(pharmacy)
        }

        addMarker(pharmacy, "Pharmacie")

        val searchInput = findViewById<EditText>(R.id.searchInput)

        searchInput.setOnEditorActionListener { _, _, _ ->
            searchLocation(searchInput.text.toString())
            true
        }

        findViewById<Button>(R.id.btnAccueil).setOnClickListener {
            openPage(AccueilActivity::class.java)
        }

        findViewById<Button>(R.id.btnCarte).setTextColor(
            ContextCompat.getColor(this, R.color.green)
        )

        findViewById<Button>(R.id.btnCommande).setOnClickListener {
            openPage(CommandeActivity::class.java)
        }

        findViewById<Button>(R.id.btnProfil).setOnClickListener {
            openPage(ProfilActivity::class.java)
        }

        val btnMyLocation = findViewById<FloatingActionButton>(R.id.btnMyLocation)

        btnMyLocation.setOnClickListener {
            locationOverlay?.enableFollowLocation()
                ?: Toast.makeText(this, "GPS not ready", Toast.LENGTH_SHORT).show()
        }

        requestLocation()
    }

    private fun searchLocation(query: String) {
        when {
            query.contains("pharmacie", true) -> {
                moveTo(pharmacy, "Pharmacie")
            }
            else -> Toast.makeText(this, "Not found", Toast.LENGTH_SHORT).show()
        }
    }

    private fun moveTo(point: GeoPoint, title: String) {
        map.controller.animateTo(point)
        map.controller.setZoom(17.0)
        addMarker(point, title)
    }

    private fun addMarker(point: GeoPoint, title: String) {
        val marker = Marker(map)
        marker.position = point
        marker.title = title
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        map.overlays.add(marker)
        map.invalidate()
    }

    private fun requestLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
        } else {
            startLocation()
        }
    }

    private fun startLocation() {
        locationOverlay = MyLocationNewOverlay(
            GpsMyLocationProvider(this),
            map
        )

        locationOverlay?.enableMyLocation()
        map.overlays.add(locationOverlay)
    }

    private fun openPage(activity: Class<*>) {
        startActivity(Intent(this, activity))
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    override fun onResume() {
        super.onResume()
        map.onResume()
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }
}