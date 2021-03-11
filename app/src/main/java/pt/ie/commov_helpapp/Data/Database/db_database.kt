package pt.ie.commov_helpapp.Data.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pt.ie.commov_helpapp.Data.DAO.NotasDao
import pt.ie.commov_helpapp.Data.Entitys.Notas

@Database(entities = [Notas::class], version = 1, exportSchema = false)

abstract class db_database : RoomDatabase() {

    abstract fun NotasDao(): NotasDao

    companion object {
        @Volatile
        private var INSTANCE: db_database? = null

        fun getDatabase(context: Context): db_database {
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
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}