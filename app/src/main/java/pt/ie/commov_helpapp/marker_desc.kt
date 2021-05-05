package pt.ie.commov_helpapp

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.model.LatLng
import ipvc.estg.retrofit.api.EndPoints
import ipvc.estg.retrofit.api.ServiceBuilder
import pt.ie.commov_helpapp.api.Pontos
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class marker_desc : AppCompatActivity() {

    //Shared Preferences
    lateinit var preferences: SharedPreferences

    private lateinit var Titulo: TextView
    private lateinit var Tipo: TextView
    private lateinit var Descricao: TextView
    //private lateinit var imagem: ImageView

    private var iddoUserMarker = 0
    private var IdMarker = 0
    private lateinit var TituloValor: String
    private lateinit var DescValor: String
    private lateinit var TipoValor: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marker_desc)

        Titulo = findViewById<TextView>(R.id.outputTituloMarker)
        Tipo = findViewById<TextView>(R.id.outputTipoMarker)
        Descricao = findViewById<TextView>(R.id.OutputMarkerDescricao)
        //imagem = findViewById<ImageView>(R.id.marker_imagem)

        IdMarker = intent.getIntExtra("idDoMarker", 0)

        if(IdMarker == 0){
            IdMarker = intent.getIntExtra("idDoMarkerDoEdit", 0)
        }

        carregar_pontos(IdMarker)

        val button = findViewById<Button>(R.id.buttonEditar)
        button.setOnClickListener{
            preferences = getSharedPreferences("SharedLogin", Context.MODE_PRIVATE);
            val idPref = preferences.getInt("ID", 0)

            if(idPref.equals(iddoUserMarker)){
                val intent = Intent(this@marker_desc, edit_Ponto::class.java)
                intent.putExtra("idDoMarkerEdit", IdMarker)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this@marker_desc, R.string.cantedit, Toast.LENGTH_SHORT).show()
            }
        }

        val button1 = findViewById<Button>(R.id.buttonVoltar)
        button1.setOnClickListener{

            val intent = Intent(this@marker_desc, VisualizadorMapa::class.java)
            startActivity(intent)
            finish()
        }

    }

    fun carregar_pontos(id: Int?) {

        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.getPontosID(id)

        var position: LatLng

        call.enqueue(object : Callback<Pontos> {

            override fun onResponse(call: Call<Pontos>, response: Response<Pontos>) {

                if (response.isSuccessful){
                    val p: Pontos = response.body()!!

                    Titulo.setText(p.titulo)
                    Tipo.setText(p.tipo)
                    Descricao.setText(p.descricao)

                    TituloValor = p.titulo
                    DescValor = p.tipo
                    TipoValor = p.descricao

                    iddoUserMarker = p.user_id
                }
            }

            override fun onFailure(call: Call<Pontos>, t: Throwable) {
                Toast.makeText(this@marker_desc, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    fun deletePonto(id: Int?) {

        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.deletePonto(id)

        call.enqueue(object : Callback<Pontos> {

            override fun onResponse(call: Call<Pontos>, response: Response<Pontos>) {

                Toast.makeText(this@marker_desc, R.string.deleteMArker, Toast.LENGTH_SHORT).show()

                val intent = Intent(this@marker_desc, VisualizadorMapa::class.java)
                startActivity(intent)
                finish()

            }

            override fun onFailure(call: Call<Pontos>, t: Throwable) {
                Toast.makeText(this@marker_desc, R.string.deleteMArker, Toast.LENGTH_SHORT).show()
                val intent = Intent(this@marker_desc, VisualizadorMapa::class.java)
                startActivity(intent)
                finish()
                //Toast.makeText(this@marker_desc, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.delete_menu, menu)
        return true
    }

    //Options Delete Menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.delete){

            preferences = getSharedPreferences("SharedLogin", Context.MODE_PRIVATE);
            val idPref = preferences.getInt("ID", 0)

            if(idPref.equals(iddoUserMarker)){

                val builder = AlertDialog.Builder(this)
                builder.setPositiveButton(getResources().getString(R.string.yes)) { _, _ ->
                    deletePonto(IdMarker)
                }
                builder.setNegativeButton(getResources().getString(R.string.no)) { _, _ -> }
                builder.setTitle(getResources().getString(R.string.deletepontopergunta))
                builder.create().show()

            }
            else{
                Toast.makeText(this@marker_desc, R.string.cantdelete, Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val intent = Intent(this@marker_desc, VisualizadorMapa::class.java)
        startActivity(intent)
        finish()
    }
}
