package com.karinaksenovskaya.myapplication.data.network.dto.weather

import com.google.gson.annotations.SerializedName

data class DayDto(
    @SerializedName("date_epoch") val date: Long,
    @SerializedName("day") val dayWeatherDto: DayWeatherDto
)
