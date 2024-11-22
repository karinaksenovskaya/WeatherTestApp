package com.karinaksenovskaya.myapplication.data.repository

import com.karinaksenovskaya.myapplication.data.mapper.toEntities
import com.karinaksenovskaya.myapplication.data.network.api.ApiService
import com.karinaksenovskaya.myapplication.domain.entity.City
import com.karinaksenovskaya.myapplication.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : SearchRepository {

    override suspend fun search(query: String): List<City> {
        return apiService.searchCity(query).toEntities()
    }
}