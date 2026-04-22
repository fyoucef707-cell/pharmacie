package youcef.pharmacie

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
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

class MapsActivity : AppCompatActivity() {

    private lateinit var map: MapView
    private lateinit var txtDistance: TextView

    private var locationOverlay: MyLocationNewOverlay? = null
    private var userMarker: Marker? = null

    // ✅ dynamic pharmacy (IMPORTANT)
    private var pharmacy = GeoPoint(35.6985, -0.6350)
    private var pharmacyName = "Pharmacie Centrale"

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Configuration.getInstance().load(
            applicationContext,
            getSharedPreferences("osmdroid", MODE_PRIVATE)
        )
        Configuration.getInstance().userAgentValue = packageName

        setContentView(R.layout.activity_maps)

        map = findViewById(R.id.map)
        txtDistance = findViewById(R.id.txtDistance)

        // ================= GET INTENT DATA =================
        val name = intent.getStringExtra("name")
        val lat = intent.getDoubleExtra("lat", 35.6985)
        val lon = intent.getDoubleExtra("lon", -0.6350)

        if (name != null) pharmacyName = name
        pharmacy = GeoPoint(lat, lon)

        setupMap()
        setupMenu()
        requestLocation()
    }

    // ================= MAP =================
    private fun setupMap() {

        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setMultiTouchControls(true)

        map.controller.setZoom(16.0)
        map.controller.setCenter(pharmacy)

        addMarker(pharmacy, pharmacyName)
    }

    // ================= MENU =================
    private fun setupMenu() {

        val accueil = findViewById<Button>(R.id.btnAccueil)
        val carte = findViewById<Button>(R.id.btnCarte)
        val commande = findViewById<Button>(R.id.btnCommande)
        val profil = findViewById<Button>(R.id.btnProfil)

        val green = ContextCompat.getColor(this, R.color.green)
        val gray = ContextCompat.getColor(this, android.R.color.darker_gray)

        fun reset() {
            accueil.setTextColor(gray)
            carte.setTextColor(gray)
            commande.setTextColor(gray)
            profil.setTextColor(gray)
        }

        fun setActive(btn: Button) {
            reset()
            btn.setTextColor(green)
        }

        setActive(carte)

        accueil.setOnClickListener {
            setActive(accueil)
            openPage(AccueilActivity::class.java)
        }

        commande.setOnClickListener {
            setActive(commande)
            openPage(CommandeActivity::class.java)
        }

        profil.setOnClickListener {
            setActive(profil)
            openPage(ProfilActivity::class.java)
        }

        findViewById<FloatingActionButton>(R.id.btnMyLocation).setOnClickListener {
            locationOverlay?.enableFollowLocation()
            Toast.makeText(this, "GPS activated", Toast.LENGTH_SHORT).show()
        }
    }

    // ================= LOCATION =================
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

        val provider = GpsMyLocationProvider(this)
        provider.locationUpdateMinTime = 1000L
        provider.locationUpdateMinDistance = 1f

        locationOverlay = MyLocationNewOverlay(provider, map)

        locationOverlay?.enableMyLocation()
        locationOverlay?.enableFollowLocation()

        map.overlays.add(locationOverlay)

        startTracking()
    }

    // ================= LIVE TRACKING =================
    private fun startTracking() {

        handler.post(object : Runnable {
            override fun run() {

                val loc = locationOverlay?.lastFix

                if (loc != null) {

                    val user = GeoPoint(loc.latitude, loc.longitude)

                    updateUserMarker(user)

                    val dist = calculateDistance(user, pharmacy)

                    txtDistance.text = "📍 %.2f km".format(dist)

                    map.controller.animateTo(user)

                } else {
                    txtDistance.text = "📡 GPS en cours..."
                }

                handler.postDelayed(this, 2000)
            }
        })
    }

    // ================= USER MARKER =================
    private fun updateUserMarker(point: GeoPoint) {

        if (userMarker == null) {
            userMarker = Marker(map)
            userMarker?.title = "Vous êtes ici"
            userMarker?.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            map.overlays.add(userMarker)
        }

        userMarker?.position = point
        map.invalidate()
    }

    // ================= DISTANCE =================
    private fun calculateDistance(p1: GeoPoint, p2: GeoPoint): Double {

        val R = 6371.0

        val dLat = Math.toRadians(p2.latitude - p1.latitude)
        val dLon = Math.toRadians(p2.longitude - p1.longitude)

        val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(p1.latitude)) *
                Math.cos(Math.toRadians(p2.latitude)) *
                Math.sin(dLon / 2) *
                Math.sin(dLon / 2)

        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))

        return R * c
    }

    private fun addMarker(point: GeoPoint, title: String) {

        val marker = Marker(map)
        marker.position = point
        marker.title = title
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)

        map.overlays.add(marker)
        map.invalidate()
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
        handler.removeCallbacksAndMessages(null)
    }
}