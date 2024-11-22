package com.karinaksenovskaya.myapplication.data.network.dto.weather

import com.google.gson.annotations.SerializedName

data class WeatherForecastDto(
    @SerializedName("current") val current: WeatherDto,
    @SerializedName("forecast") val forecastDto: ForecastDto
)
