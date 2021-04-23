package pt.ie.commov_helpapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
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
    lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualizador_mapa)

        (this as AppCompatActivity).supportActionBar?.title = ""

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.getPontos()
        var position: LatLng
        //var teste = LatLng(-0.17042, 69.45837)

        //mMap.addMarker(MarkerOptions().position(teste).title("Olá"))

        call.enqueue(object : Callback<List<Pontos>> {

             override fun onResponse(call: Call<List<Pontos>>, response: Response<List<Pontos>>) {
                if (response.isSuccessful){

                    Pontos = response.body()!!

                    for(Ponto in Pontos ){

                        position = LatLng(Ponto.lat.toString().toDouble(), Ponto.lon.toString().toDouble())

                        mMap.addMarker(MarkerOptions().position(position).title(Ponto.descricao + " - " + Ponto.tipo))


                    }
                }
            }

            override fun onFailure(call: Call<List<Pontos>>, t: Throwable) {
                Toast.makeText(this@VisualizadorMapa, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

        /*
        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.getPontosID(1)
        var position: LatLng

        val intent = Intent(this, VisualizadorMapa::class.java)

        call.enqueue(object : Callback<Pontos> {

            override fun onResponse(call: Call<Pontos>, response: Response<Pontos>) {

                if (response.isSuccessful){
                    val c: Pontos = response.body()!!

                    position = LatLng(c.lat.toString().toDouble(), c.lon.toString().toDouble())

                    mMap.addMarker(MarkerOptions().position(position).title(c.descricao + " - " + c.tipo))

                }
            }

            override fun onFailure(call: Call<Pontos>, t: Throwable) {
                Toast.makeText(this@VisualizadorMapa, "${t.message}", Toast.LENGTH_SHORT).show()
                //Toast.makeText(this@MainActivity, "ERRO ONFAILURE!", Toast.LENGTH_SHORT).show()
            }
        })*/

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.top_menu_map, menu)
        return true
    }

    //Options Delete Menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.add_point){
            val intent = Intent(this, InserirNotas::class.java)
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
        return super.onOptionsItemSelected(item)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        //Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}