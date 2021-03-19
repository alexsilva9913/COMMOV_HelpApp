package pt.ie.commov_helpapp

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import pt.ie.commov_helpapp.Data.Entitys.Notas
import pt.ie.commov_helpapp.Data.ViewModel.NotasViewModel

class editarnotas : AppCompatActivity() {

    private lateinit var mNotasViewModel: NotasViewModel

    var IdItem = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editarnotas)

        (this as AppCompatActivity).supportActionBar?.title = ""

        var notasTitulo = findViewById<EditText>(R.id.editTituloedit)
        var notasHora= findViewById<EditText>(R.id.editHoraDataedit)
        var notasDesc= findViewById<EditText>(R.id.editDescedit)

        IdItem = intent.getIntExtra("idDoItem", 0)
        //Toast.makeText(this, "Clicastes no Item # ${IdItem}", Toast.LENGTH_SHORT).show()

        mNotasViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(NotasViewModel::class.java)

        mNotasViewModel.readNotaId(IdItem).observe(this, Observer { notas ->
            notasTitulo.setText((notas.get(0).titulo))
            notasHora.setText((notas.get(0).DataHora))
            notasDesc.setText((notas.get(0).descricao))
        })

        val button = findViewById<Button>(R.id.butSaveEdit)
        button.setOnClickListener{
            update(IdItem)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.delete_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.delete_menu){
            deleteThis(IdItem)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun update(idNota: Int){

        var notasTitulo = findViewById<EditText>(R.id.editTituloedit)
        var notasHora= findViewById<EditText>(R.id.editHoraDataedit)
        var notasDesc= findViewById<EditText>(R.id.editDescedit)

        val Titulo =  notasTitulo.text.toString()
        val Hora = notasHora.text.toString()
        val Desc = notasDesc.text.toString()

        if(Titulo.isBlank() || Hora.isBlank() || Desc.isBlank()){
            Toast.makeText(this, getResources().getString(R.string.ValoresPreencher), Toast.LENGTH_SHORT).show()
        }
        else{
            val updatedNota = Notas(idNota, Titulo, Hora, Desc)
            mNotasViewModel.updateNotas(updatedNota)
            finish()
        }

    }

    private fun deleteThis(IdItem: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setPositiveButton(getResources().getString(R.string.yes)) { _, _ ->
            mNotasViewModel.deleteporid(IdItem)
            Toast.makeText(
                    this,
                    getResources().getString(R.string.doneone),
                    Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton(getResources().getString(R.string.no)) { _, _ -> }
        builder.setTitle(getResources().getString(R.string.deleteone))
        builder.create().show()
    }

}