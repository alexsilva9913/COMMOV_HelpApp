package pt.ie.commov_helpapp.Data.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import pt.ie.commov_helpapp.Data.DAO.NotasDao
import pt.ie.commov_helpapp.Data.Entitys.Notas

@Database(entities = [Notas::class], version = 2, exportSchema = false)

abstract class db_database : RoomDatabase() {

    abstract fun notasDao(): NotasDao

    private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var notasDao = database.notasDao()

                    // Delete all content here.
                    // notasDao.deleteAllNotas()

                    // Add samples

                    var nota = Notas(1, "Acidente", "19/03/2021 15:00","TesteTesteTesteTesteTesteTesteTesteTeste")
                    notasDao.addNotas(nota)
                    nota = Notas(2, "Obras", "19/03/2021 19:00","TesteTesteTesteTesteTesteTesteTesteTesteTesteTesteTeste")
                    notasDao.addNotas(nota)

                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: db_database? = null

        fun getDatabase(context: Context, scope: CoroutineScope): db_database {
            val tempInstance =
                INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    db_database::class.java,
                    "Db_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(WordDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }



}