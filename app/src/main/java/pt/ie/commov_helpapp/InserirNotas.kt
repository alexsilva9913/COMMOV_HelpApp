package pt.ie.commov_helpapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import pt.ie.commov_helpapp.Data.Entitys.Notas
import pt.ie.commov_helpapp.Data.ViewModel.NotasViewModel
import java.time.*;

class InserirNotas : AppCompatActivity() {

    private lateinit var mNotasViewModel: NotasViewModel

    private lateinit var notasTitulo: EditText
    private lateinit var notasHora: EditText
    private lateinit var notasDesc: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inserir_notas)

        notasTitulo = findViewById(R.id.editTituloedit)
        notasHora = findViewById(R.id.editHoraDataedit)
        notasDesc = findViewById(R.id.editDescedit)

        //Gets Current Date
        val current = LocalDateTime.now()

        //Transform The String Date
        val DateNow = current.toString().take(10)

        //Cuts Time from current time
        val TimeNow = current.toString().take(16)
        val TimeNowFinal = TimeNow.toString().takeLast(5)

        //Creates String whit Date and Time
        val DateTimeDone = "$TimeNowFinal $DateNow"

        //Insert date and time
        notasHora.setText(DateTimeDone)

        mNotasViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(NotasViewModel::class.java)
        //mNotasViewModel = ViewModelProvider(this).get(NotasViewModel::class.java)

        val button = findViewById<Button>(R.id.butSave)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(notasTitulo.text) || TextUtils.isEmpty(notasHora.text) || TextUtils.isEmpty(notasDesc.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                insertNotas()
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY_TIT = "com.example.android.TIT"
        const val EXTRA_REPLY_HORA = "com.example.android.HORA"
        const val EXTRA_REPLY_DESC = "com.example.android.DESC"
    }

    private fun insertNotas(){

        //Gets the values inserted in the EditText Inputs
        val tit = notasTitulo.text
        val hora = notasHora.text
        val desc = notasDesc.text

        val notas = Notas(0, tit.toString(),hora.toString(),desc.toString())

        mNotasViewModel.addNotas(notas)

        Toast.makeText(this, "Created Successfully!", Toast.LENGTH_SHORT).show()

    }
}



