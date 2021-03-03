package pt.ie.commov_helpapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ButNotas = findViewById<Button>(R.id.butNotas) as Button

        //Abrir Notas
        ButNotas.setOnClickListener {
            val intent = Intent(this, ListaNotas::class.java)
            startActivity(intent)
        }

    }
}