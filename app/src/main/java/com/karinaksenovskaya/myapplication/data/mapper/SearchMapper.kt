package com.karinaksenovskaya.myapplication.data.mapper

import com.karinaksenovskaya.myapplication.data.network.dto.weather.CityDto
import com.karinaksenovskaya.myapplication.domain.entity.City

fun CityDto.toEntity(): City = City(id, name, country)

fun List<CityDto>.toEntities(): List<City> = map {
    it.toEntity()
}