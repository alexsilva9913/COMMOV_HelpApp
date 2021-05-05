package pt.ie.commov_helpapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import ipvc.estg.retrofit.api.EndPoints
import ipvc.estg.retrofit.api.ServiceBuilder
import pt.ie.commov_helpapp.api.Pontos
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VisualizadorMapa : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var Pontos: List<Pontos>

    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest

    private lateinit var locAdd: LatLng

    private lateinit var findTipo: EditText
    
    //Shared Preferences
    lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualizador_mapa)

        (this as AppCompatActivity).supportActionBar?.title = ""

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // initialize fusedLocationClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        carregar_pontos(null,null)

        //added to implement location periodic updates
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                lastLocation = p0.lastLocation
                var loc = LatLng(lastLocation.latitude, lastLocation.longitude)
                locAdd = loc
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 13.0f))
                //Mostra as coordenadas periodicamente
                Log.d("Coords",loc.latitude.toString() + " - " + loc.longitude.toString())
            }
        }
        createLocationRequest()

        //Buscar tipo de ponto pelo texto do user
        findTipo = findViewById(R.id.insertTipoPonto)
        val button1 = findViewById<Button>(R.id.Listar)
        button1.setOnClickListener {
            mMap.clear()
            carregar_pontos(findTipo.text.toString(),null)
        }

    }

    fun carregar_pontos(tipo: String?, distance: Int?) {

        //Listar TUDO
        val request = ServiceBuilder.buildService(EndPoints::class.java)

        var call: Call<List<Pontos>>
        if( tipo != null ) call = request.getPontosTipo(tipo)
        else call = request.getPontos()

        var position: LatLng

        call.enqueue(object : Callback<List<Pontos>> {

            override fun onResponse(call: Call<List<Pontos>>, response: Response<List<Pontos>>) {

                if (response.isSuccessful){

                    Pontos = response.body()!!

                    if (Pontos != null) {

                        for (Ponto in Pontos) {

                            position = LatLng(Ponto.lat.toDouble(), Ponto.lon.toDouble())

                            //val distanceOf = calculateDistance(lastLocation.latitude, lastLocation.longitude, position.latitude, position.longitude)
                            val distanceOf = 100

                            preferences = getSharedPreferences("SharedLogin", Context.MODE_PRIVATE);
                            val idPref = preferences.getInt("ID", 0)

                            if (distance != null) {
                                if (distanceOf < distance) {
                                    if (Ponto.user_id.equals(idPref)) {
                                        val marker = mMap.addMarker(MarkerOptions().position(position).title(Ponto.titulo).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
                                        marker.tag = "${Ponto.id}-true"
                                    } else {
                                        val marker = mMap.addMarker(MarkerOptions().position(position).title(Ponto.titulo).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)))
                                        marker.tag = "${Ponto.id}-true"
                                    }
                                }
                            } else {
                                if (Ponto.user_id.equals(idPref)) {
                                    val marker = mMap.addMarker(MarkerOptions().position(position).title(Ponto.titulo).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
                                    marker.tag = "${Ponto.id}-true"

                                } else {
                                    val marker = mMap.addMarker(MarkerOptions().position(position).title(Ponto.titulo).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)))
                                    marker.tag = "${Ponto.id}-true"
                                }
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<Pontos>>, t: Throwable) {
                Toast.makeText(this@VisualizadorMapa, "${t.message}", Toast.LENGTH_SHORT).show()
            }

        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.top_menu_map, menu)
        return true
    }

    //Options Delete Menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.add_point){

            val intent = Intent(this, inserir_ponto::class.java)
            intent.putExtra("LocLat", locAdd.latitude)
            intent.putExtra("LocLon", locAdd.longitude)
            startActivity(intent)

        }
        if(item.itemId == R.id.logout){
            preferences = getSharedPreferences("SharedLogin", Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = preferences.edit()
            editor.clear()
            editor.apply()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        if(item.itemId == R.id.range){
            mMap.clear()
            carregar_pontos(null, 1000)
        }
        if(item.itemId == R.id.showall){
            mMap.clear()
            carregar_pontos(null, null)
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.setOnInfoWindowClickListener( object: GoogleMap.OnInfoWindowClickListener {
            override fun onInfoWindowClick(p0: Marker) {

                val intent = Intent(this@VisualizadorMapa, marker_desc::class.java)

                //Buscar valor do ID do marker
                val id: Int
                val split = TextUtils.split( "${p0.tag}", "-")

                //Valor do ID
                id = split[0].toInt()
                intent.putExtra("idDoMarker",id)

                Log.d("VALOR", id.toString())

                startActivity(intent)
                finish()
            }
        })
    }

    //Alterado
    companion object {
        // add to implement last known location
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        //added to implement location periodic updates
        private const val REQUEST_CHECK_SETTINGS = 2
    }

    //added to implement location periodic updates
    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE)
            return
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null /* Looper */)
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.interval = 5000 //Rate de 5 segundos
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }


    //Parar de receber Coordenadas
    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
        Log.d("Valor:", "Pause")
    }

    //Resumir a receção de coordenadas
    public override fun onResume() {
        super.onResume()
        startLocationUpdates()
        Log.d("Valor:", "Resume")
    }


    private fun getAddress(lat: Double, lng: Double): String {
        val geocoder = Geocoder(this)
        val list = geocoder.getFromLocation(lat, lng, 1)
        return list[0].getAddressLine(0)
    }

    fun calculateDistance(lat1: Double, lng1: Double, lat2: Double, lng2: Double): Float {
        val results = FloatArray(1)
        Location.distanceBetween(lat1, lng1, lat2, lng2, results)
        // distance in meter
        return results[0]
    }
}