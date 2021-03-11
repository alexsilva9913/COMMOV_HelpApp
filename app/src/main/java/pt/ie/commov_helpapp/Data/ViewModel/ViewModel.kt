package pt.ie.commov_helpapp.Data.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pt.ie.commov_helpapp.Data.DAO.NotasDao
import pt.ie.commov_helpapp.Data.Database.db_database
import pt.ie.commov_helpapp.Data.Entitys.Notas
import pt.ie.commov_helpapp.Data.Repository.Repository

//ViewModel [Provides data to the UI and survive configuration changes] [Communication between Repository and UI]

class ViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Notas>>

    val readFirstData: LiveData<List<Notas>>

    private val repository: Repository

    init {
        val RegistoDao = db_database.getDatabase(application).NotasDao()
        repository = Repository(RegistoDao)
        readAllData = repository.readAllData
        readFirstData = repository.readFirstData
    }

    fun addNotas(notas: Notas){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addNotas(notas)
        }
    }

    fun updateNotas(notas: Notas){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateNotas(notas)
        }
    }

    fun deleteNotas(notas: Notas){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNotas(notas)
        }
    }

    fun deleteAllNotas(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllNotas()
        }
    }
}