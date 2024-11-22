package com.karinaksenovskaya.myapplication.domain.repository

import com.karinaksenovskaya.myapplication.domain.entity.City

interface SearchRepository {
    suspend fun search(query: String): List<City>
}