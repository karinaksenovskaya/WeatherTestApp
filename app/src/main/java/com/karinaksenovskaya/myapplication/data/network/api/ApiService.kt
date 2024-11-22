package com.karinaksenovskaya.myapplication.data.network.api

import com.karinaksenovskaya.myapplication.data.network.dto.weather.CityDto
import com.karinaksenovskaya.myapplication.data.network.dto.weather.WeatherCurrentDto
import com.karinaksenovskaya.myapplication.data.network.dto.weather.WeatherForecastDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("current.json")
    suspend fun loadCurrentWeather(
        @Query("q") query: String
    ): WeatherCurrentDto

    @GET("forecast.json")
    suspend fun loadForecast(
        @Query("q") query: String,
        @Query("days") daysCount: Int = 8
    ): WeatherForecastDto

    @GET("search.json")
    suspend fun searchCity(
        @Query("q") query: String
    ): List<CityDto>
}