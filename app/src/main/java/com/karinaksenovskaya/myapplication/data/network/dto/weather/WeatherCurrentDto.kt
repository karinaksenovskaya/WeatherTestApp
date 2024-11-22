package com.karinaksenovskaya.myapplication.data.network.dto.weather

import com.google.gson.annotations.SerializedName

data class WeatherCurrentDto(

    @SerializedName("current") val current: WeatherDto
)
