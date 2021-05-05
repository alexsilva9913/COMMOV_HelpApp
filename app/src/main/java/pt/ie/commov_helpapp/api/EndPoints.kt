package ipvc.estg.retrofit.api

import pt.ie.commov_helpapp.api.Pontos
import retrofit2.Call
import retrofit2.http.*


interface EndPoints {

    //Todos os users
    @GET("users")
    fun getUsers(): Call<List<User>>

    //User pelo username
    @FormUrlEncoded
    @POST("users/post")
    fun getUserByNome(@Field("username") username: String?): Call<User>

    //Todos os Pontos
    @GET("pontos")
    fun getPontos(): Call<List<Pontos>>

    //Pontos por Tipo
    @GET("pontos/{tipo}")
    fun getPontosTipo(@Path("tipo") tipo: String?): Call<List<Pontos>>

    //Pontos por ID
    @GET("pontosID/{id}")
    fun getPontosID(@Path("id") id: Int?): Call<Pontos>

    //Delete Ponto
    @DELETE("pontosDelete/{id}")
    fun deletePonto(@Path("id") id: Int?): Call<Pontos>

    //Editar Ponto
    @FormUrlEncoded
    @PUT("pontosPut/{id}")
    fun editPonto(@Path("id") id: Int?,
                  @Field("titulo") titulo: String?,
                  @Field("descricao") descricao: String?,
                  @Field("tipo") tipo: String?): Call<Pontos>

    //Inserir Ponto
    @FormUrlEncoded
    @POST("addponto")
    fun inserirPonto(@Field("titulo") titulo: String?,
                     @Field("descricao") descricao: String?,
                     @Field("lat") lat: String?,
                     @Field("lon") lon: String?,
                     @Field("tipo") tipo: String?,
                     @Field("user_id") user_id: Int): Call<Pontos>
}