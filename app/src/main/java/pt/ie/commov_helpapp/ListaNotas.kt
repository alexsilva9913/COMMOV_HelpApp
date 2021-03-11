package pt.ie.commov_helpapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pt.ie.commov_helpapp.Adapter.NotasAdapter
import pt.ie.commov_helpapp.Data.ViewModel.NotasViewModel

private lateinit var mNotasViewModel: NotasViewModel

class ListaNotas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_notas)

        // recycler view
        val recyclerView = findViewById<RecyclerView>(R.id.recycler)
        val adapter = NotasAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // view model
        mNotasViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(NotasViewModel::class.java)
        mNotasViewModel.readAllData.observe(this, Observer { notas ->
            notas?.let { adapter.setNotas(it) }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.top_menu, menu)
        return true
    }

    //Options Delete Menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.add_user){
            val intent = Intent(this, InserirNotas::class.java)
            startActivity(intent)
        }
        if(item.itemId == R.id.delete_menu){
            deleteAll()
        }
        return super.onOptionsItemSelected(item)
    }

    fun deleteAll(){
        mNotasViewModel.deleteAllNotas();
    }

}