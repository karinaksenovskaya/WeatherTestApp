package com.karinaksenovskaya.myapplication.data.mapper

import com.karinaksenovskaya.myapplication.data.network.dto.weather.WeatherCurrentDto
import com.karinaksenovskaya.myapplication.data.network.dto.weather.WeatherDto
import com.karinaksenovskaya.myapplication.data.network.dto.weather.WeatherForecastDto
import com.karinaksenovskaya.myapplication.domain.entity.Forecast
import com.karinaksenovskaya.myapplication.domain.entity.Weather
import java.util.Calendar
import java.util.Date

fun WeatherCurrentDto.toEntity(): Weather = current.toEntity()

fun WeatherDto.toEntity() : Weather = Weather(
    tempC = tempC,
    conditionText = conditionDto.text,
    conditionUrl = conditionDto.iconUrl.correctImageUrl(),
    date = date.toCalendar()
)

fun WeatherForecastDto.toEntity() = Forecast(
    currentWeather = current.toEntity(),
    upcoming = forecastDto.forecastDay.drop(1).map{ dayDto ->
        val dayWeather = dayDto.dayWeatherDto
        Weather(
            tempC = dayWeather.tempC,
            conditionText = dayWeather.conditionDto.text,
            conditionUrl = dayWeather.conditionDto.iconUrl.correctImageUrl(),
            date = dayDto.date.toCalendar()
        )
    }
)

private fun Long.toCalendar() = Calendar.getInstance().apply {
    time = Date(this@toCalendar * 1000)
}

private fun String.correctImageUrl() = "https:$this".replace(
    oldValue = "64X64",
    newValue = "128x128"
)