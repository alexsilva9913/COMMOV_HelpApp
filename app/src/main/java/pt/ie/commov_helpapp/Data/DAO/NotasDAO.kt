package pt.ie.commov_helpapp.Data.DAO

import androidx.lifecycle.LiveData
import androidx.room.*
import pt.ie.commov_helpapp.Data.Entitys.Notas

//Data Access Object [Methods to Access the Database]

@Dao
interface NotasDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNotas(notas: Notas)

    @Query("SELECT * FROM notas")
    fun readAllData(): LiveData<List<Notas>>

    @Query("SELECT * FROM notas ORDER BY id DESC")
    fun readFirstData(): LiveData<List<Notas>>

    @Update
    suspend fun updateNotas(notas: Notas)

    @Delete
    suspend fun deleteNotas(notas: Notas)

    @Query("DELETE FROM notas")
    suspend fun deleteAllNotas()

}