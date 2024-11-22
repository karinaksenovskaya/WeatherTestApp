package com.karinaksenovskaya.myapplication.data.network.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GeoFactory {

    private const val BASE_URL = "https://nominatim.openstreetmap.org/"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val originalRequest = chain.request()
            val newRequest = originalRequest.newBuilder()
                .header("User-Agent", "WeatherApp/1.0")
                .build()
            chain.proceed(newRequest)
        }
        .build()

    val nominatimService: GeoService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(GeoService::class.java)
    }
}