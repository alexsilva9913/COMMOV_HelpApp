package ipvc.estg.retrofit.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
    private val client = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        //.baseUrl("http://localhost:81/slim_cm/api/")
        //.baseUrl("http://192.168.1.7/slim_cm/api/")
            .baseUrl("http://172.16.181.232/slim_cm/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun<T> buildService(service: Class<T>): T{
        return retrofit.create(service)
    }
}