package com.karinaksenovskaya.myapplication.data.network.dto.weather

import com.google.gson.annotations.SerializedName

data class ForecastDto(
    @SerializedName("forecastday") val forecastDay: List<DayDto>
)
