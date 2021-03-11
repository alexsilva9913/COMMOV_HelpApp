package pt.ie.commov_helpapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import pt.ie.commov_helpapp.Data.Entitys.Notas
import pt.ie.commov_helpapp.Data.ViewModel.NotasViewModel

class MainActivity : AppCompatActivity() {

    //private lateinit var mNotasViewModel: NotasViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ButNotas = findViewById<Button>(R.id.butNotas) as Button

        //Abrir Notas
        ButNotas.setOnClickListener {
            val intent = Intent(this, ListaNotas::class.java)
            startActivity(intent)
        }

        /*mNotasViewModel = ViewModelProvider(this@MainActivity).get(NotasViewModel::class.java)

        val notas = Notas(1,"Acidente1","11/02/2021 15:00", "asdsdasdasdasbvfsagfdbfbgfsgbgfbgfg")

        mNotasViewModel.addNotas(notas)*/

    }
}