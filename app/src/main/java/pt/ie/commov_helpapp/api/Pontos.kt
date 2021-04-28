package pt.ie.commov_helpapp.api

data class Pontos (
    val id: Int,
    val titulo: String,
    val descricao: String,
    val lat: String,
    val lon: String,
    val tipo: String,
    val user_id: Int
)

