package ipvc.estg.retrofit.api

import pt.ie.commov_helpapp.api.Pontos
import retrofit2.Call
import retrofit2.http.*


interface EndPoints {

    //Todos os users
    @GET("users")
    fun getUsers(): Call<List<User>>

    //User pelo username
    @POST("users/{username}")
    fun getUserByNome(@Path("username") username: String): Call<User>

    //Todos os Pontos
    @GET("/pontos")
    fun getPontos(): Call<List<Pontos>>

    //Pontos por id
    @GET("/pontos({id}")
    fun getPontosID(@Path("id") id: Int): Call<Pontos>

    /*
    @FormUrlEncoded
    @POST("/posts")
    fun postTest(@Field("title") first: String?): Call<OutputPost>*/
}