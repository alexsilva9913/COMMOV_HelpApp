
package pt.ie.commov_helpapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import ipvc.estg.retrofit.api.EndPoints
import ipvc.estg.retrofit.api.ServiceBuilder
import ipvc.estg.retrofit.api.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ButNotas = findViewById<Button>(R.id.butNotas) as Button

        //Abrir Notas
        ButNotas.setOnClickListener {
            val intent = Intent(this, ListaNotas::class.java)
            startActivity(intent)
        }

        preferences = getSharedPreferences("SharedLogin", Context.MODE_PRIVATE);
        val userPref = preferences.getString("USERNAME", null)
        val passPref = preferences.getString("PASSWORD", null)

        val ButLogin = findViewById<Button>(R.id.butLogin) as Button

        if (userPref.isNullOrBlank() || passPref.isNullOrBlank()) {

            //Abrir Login
            ButLogin.setOnClickListener {

                var username = findViewById<EditText>(R.id.editUsername)
                var password = findViewById<EditText>(R.id.editPassword)

                val usernameStr = username.text.toString()
                val passwordStr = password.text.toString()

                if(usernameStr.isNullOrBlank() || passwordStr.isNullOrBlank()){
                    Toast.makeText(this@MainActivity, getResources().getString(R.string.preenchercampos), Toast.LENGTH_SHORT).show()
                }
                else{
                    Login(usernameStr, passwordStr)
                }
            }
        }

        else {

            val intent = Intent(this, VisualizadorMapa::class.java)
            startActivity(intent)
            finish()

        }
    }

    fun Login(user: String, pass: String) {

        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.getUserByNome(user)

        val intent = Intent(this, VisualizadorMapa::class.java)

        call.enqueue(object : Callback<User> {

            override fun onResponse(call: Call<User>, response: Response<User>) {

                if (response.isSuccessful){
                    val c: User = response.body()!!

                    if(c.password.equals(pass)) {

                        val editor: SharedPreferences.Editor = preferences.edit()

                        editor.putString("USERNAME", user)
                        editor.putString("PASSWORD", pass)
                        editor.putInt("ID", c.id)
                        editor.apply()

                        startActivity(intent)
                        finish()
                    }
                    else{
                        Toast.makeText(this@MainActivity, getResources().getString(R.string.passworderrada), Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

}





