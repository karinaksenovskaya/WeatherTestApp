package com.karinaksenovskaya.myapplication.data.network.api

import com.karinaksenovskaya.myapplication.data.network.dto.geo.NominatimResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoService {

    @GET("reverse")
    suspend fun getReverseGeocoding(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("format") format: String = "json"
    ): NominatimResponse
}