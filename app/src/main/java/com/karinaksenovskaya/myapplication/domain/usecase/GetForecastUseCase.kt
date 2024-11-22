package com.karinaksenovskaya.myapplication.domain.usecase

import com.karinaksenovskaya.myapplication.domain.repository.WeatherRepository
import javax.inject.Inject

class GetForecastUseCase @Inject constructor(
    private val repository: WeatherRepository
) {

    suspend operator fun invoke(city: String) = repository.getForecast(city)
}