package pt.ie.commov_helpapp.Data.Repository

import androidx.lifecycle.LiveData
import androidx.room.Query
import pt.ie.commov_helpapp.Data.DAO.NotasDao
import pt.ie.commov_helpapp.Data.Entitys.Notas

//Repository to access multiple class Objects

class Repository(private val notasDao: NotasDao) {

    val readAllData: LiveData<List<Notas>> = notasDao.readAllData()

    val readFirstData: LiveData<List<Notas>> = notasDao.readFirstData()

    fun readNotaId(id: Int): LiveData<List<Notas>> {
        return notasDao.readNotaId(id)
    }

    fun deleteporid(id: Int){
        return notasDao.deleteporid(id)
    }

    suspend fun addNotas(registo: Notas){
        notasDao.addNotas(registo)
    }

    suspend fun updateNotas(registo: Notas){
        notasDao.updateNotas(registo)
    }

    suspend fun deleteNotas(registo: Notas){
        notasDao.deleteNotas(registo)
    }

    suspend fun deleteAllNotas(){
        notasDao.deleteAllNotas()
    }

}