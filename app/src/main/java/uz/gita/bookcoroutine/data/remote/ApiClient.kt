package uz.gita.bookcoroutine.data.remote

import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.gita.bookcoroutine.app.App

object ApiClient {
    private val myClient = OkHttpClient.Builder()
        .addInterceptor(ChuckerInterceptor.Builder(App.instance).build())
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://143.198.48.222:82")
        .client(myClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}