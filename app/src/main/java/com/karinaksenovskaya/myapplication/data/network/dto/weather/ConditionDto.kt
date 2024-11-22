package com.karinaksenovskaya.myapplication.data.network.dto.weather

import com.google.gson.annotations.SerializedName

data class ConditionDto(

    @SerializedName("text") val text: String,
    @SerializedName("icon") val iconUrl: String
)
