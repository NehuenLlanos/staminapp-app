package com.staminapp.data.network.api

import android.content.Context
import com.google.gson.GsonBuilder
import com.staminapp.BuildConfig

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.*

object RetrofitClient {

    @Volatile
    private var instance: Retrofit? = null

    // Si dos corutinas llaman a dos instance entonces tengo que crear solamente uno
    // y sincronizar el bloque de codigo y volver a preguntar por instance para que
    // dos corutinas no creen dos instancia de lo mismo.
    // Se pregunta si es null dos veces. Una antes de entrar y una antes de crearla, es decir,
    // luego de pasar por todo el codigo que posee dentro.
    // El it es lo que devuelve el buildRetrofit.
    private fun getInstance(context: Context) : Retrofit =
        instance ?: synchronized(this) {
            instance ?: buildRetrofit(context).also { instance = it }
        }

    private fun buildRetrofit(context: Context) : Retrofit {
        val httpLogginInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(context))
            .addInterceptor(httpLogginInterceptor)
            .build()

        val gson = GsonBuilder()
            .registerTypeAdapter(Date::class.java, ApiDateTypeAdapter())
            .create()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    fun getApiUserService(context: Context) : ApiUserService {
        return getInstance(context).create(ApiUserService::class.java)
    }

    fun getRoutinesApiService(context: Context) : RoutinesApiService {
        return getInstance(context).create(RoutinesApiService::class.java)
    }
}