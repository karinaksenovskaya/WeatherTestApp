package com.karinaksenovskaya.myapplication.domain.usecase

import com.karinaksenovskaya.myapplication.domain.repository.WeatherRepository
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
){

    suspend operator fun invoke(cityId:Int) = repository.getWeather(cityId)
}