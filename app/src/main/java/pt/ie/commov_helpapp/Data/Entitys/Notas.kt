package pt.ie.commov_helpapp.Data.Entitys

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
//import kotlinx.android.parcel.Parcelize

//@Parcelize
@Entity(tableName = "notas")
data class Notas(
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        val titulo: String,
        val descricao: String
)//: Parcelable