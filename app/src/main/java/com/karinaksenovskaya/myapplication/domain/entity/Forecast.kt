package com.karinaksenovskaya.myapplication.domain.entity

data class Forecast(
    val currentWeather: Weather,
    val upcoming: List<Weather>
)
