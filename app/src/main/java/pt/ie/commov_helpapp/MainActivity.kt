package pt.ie.commov_helpapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import ipvc.estg.retrofit.api.EndPoints
import ipvc.estg.retrofit.api.ServiceBuilder
import ipvc.estg.retrofit.api.User
import pt.ie.commov_helpapp.Data.Entitys.Notas
import pt.ie.commov_helpapp.Data.ViewModel.NotasViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        val ButLogin = findViewById<Button>(R.id.butLogin) as Button

        //Abrir Login
        ButLogin.setOnClickListener {


            val intent = Intent(this, VisualizadorMapa::class.java)
            startActivity(intent)

            /*
            var username = findViewById<EditText>(R.id.editUsername)
            var password = findViewById<EditText>(R.id.editPassword)

            val usernameStr =  username.text.toString()
            val passwordStr = password.text.toString()

            Login(usernameStr, passwordStr)*/
        }
    }

    fun Login(user: String, pass: String) {

        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.getUserByNome(user)

        Log.d("TESTE",call.toString())

        call.enqueue(object : Callback<User> {

            override fun onResponse(call: Call<User>, response: Response<User>) {

                Log.d("TESTE1",call.toString())
                Log.d("TESTE1",response.toString())

                if (response.isSuccessful){
                    val c: User = response.body()!!

                    if(c.password.equals(pass)) {
                        Toast.makeText(this@MainActivity, c.password, Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(this@MainActivity, "Password Errada!", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                //Toast.makeText(this@MainActivity, "ERRO ONFAILURE!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun LoginUser(){

        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.getUsers()

        call.enqueue(object : Callback<List<User>>{
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful){
                    Toast.makeText(this@MainActivity, "YES" + response.body(), Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

}