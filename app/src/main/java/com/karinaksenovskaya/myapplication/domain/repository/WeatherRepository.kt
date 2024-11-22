package com.karinaksenovskaya.myapplication.domain.repository

import com.karinaksenovskaya.myapplication.domain.entity.Forecast
import com.karinaksenovskaya.myapplication.domain.entity.Weather

interface WeatherRepository {
    suspend fun getWeather(cityId: Int): Weather

    suspend fun getForecast(city: String): Forecast
}