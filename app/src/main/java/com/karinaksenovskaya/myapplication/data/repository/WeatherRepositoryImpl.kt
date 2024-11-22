package com.karinaksenovskaya.myapplication.data.repository

import com.karinaksenovskaya.myapplication.data.mapper.toEntity
import com.karinaksenovskaya.myapplication.data.network.api.ApiService
import com.karinaksenovskaya.myapplication.domain.entity.Forecast
import com.karinaksenovskaya.myapplication.domain.entity.Weather
import com.karinaksenovskaya.myapplication.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : WeatherRepository {

    override suspend fun getWeather(cityId: Int): Weather {
        return apiService.loadCurrentWeather("$PREFIX_CITY_ID$cityId").toEntity()
    }

    override suspend fun getForecast(city: String): Forecast {
        return apiService.loadForecast(city).toEntity()
    }

    private companion object {
        private const val PREFIX_CITY_ID = "id:"
    }
}