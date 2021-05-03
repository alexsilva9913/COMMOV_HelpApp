package pt.ie.commov_helpapp

import android.R.attr
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ipvc.estg.retrofit.api.EndPoints
import ipvc.estg.retrofit.api.ServiceBuilder
import pt.ie.commov_helpapp.api.Pontos
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class inserir_ponto : AppCompatActivity() {

    private lateinit var pontoTitInsert: EditText
    private lateinit var pontoDesc: EditText
    private lateinit var pontoTipo: EditText
    //private lateinit var notasDesc: EditText

    //Imagem
    lateinit var imageView: ImageView
    private val pickImage = 100
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inserir_ponto)

        //Shared Preferences
        lateinit var preferences: SharedPreferences

        //Buscar o ID
        preferences = getSharedPreferences("SharedLogin", Context.MODE_PRIVATE);
        val idPref = preferences.getInt("ID", 0)

        (this as AppCompatActivity).supportActionBar?.title = ""

        pontoTitInsert = findViewById(R.id.pontoTit)
        pontoDesc = findViewById(R.id.pontoDesc)
        pontoTipo = findViewById(R.id.pontoTipo)

        var LocLat : Double
        var LocLon : Double

        LocLat = intent.getDoubleExtra("LocLat",0.0)
        LocLon = intent.getDoubleExtra("LocLon",0.0)

        val button1 = findViewById<Button>(R.id.buttonSelect)
        button1.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }

        val button = findViewById<Button>(R.id.butSave)
        button.setOnClickListener {
            inserePonto(LocLat,LocLon,idPref)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode !== Activity.RESULT_CANCELED) {
            if (resultCode == RESULT_OK && requestCode == pickImage) {
                imageView.setImageURI(data?.data)
            }
        }
    }

    private fun inserePonto(LocLat:Double, LocLon:Double,idPref: Int ){

        //Gets the values inserted in the EditText Inputs
        val tit = pontoTitInsert.text.toString()
        val desc = pontoDesc.text.toString()
        val tipo = pontoTipo.text.toString()

        if(tit.isBlank() || desc.isBlank()) {
            Toast.makeText(this, getResources().getString(R.string.ValoresPreencher), Toast.LENGTH_SHORT).show()
        }
        else{
            val request = ServiceBuilder.buildService(EndPoints::class.java)
            val call = request.inserirPonto(tit,desc,LocLat.toString(),LocLon.toString(),tipo,idPref)

            call.enqueue(object : Callback<Pontos> {

                override fun onResponse(call: Call<Pontos>, response: Response<Pontos>) {
                    if (response.isSuccessful){
                        Toast.makeText(this@inserir_ponto, getResources().getString(R.string.Criado), Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    else{
                        Toast.makeText(this@inserir_ponto, getResources().getString(R.string.erro), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Pontos>, t: Throwable) {
                    Toast.makeText(this@inserir_ponto, "${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}