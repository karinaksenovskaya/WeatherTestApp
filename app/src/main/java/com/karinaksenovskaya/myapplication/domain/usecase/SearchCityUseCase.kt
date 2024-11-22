package com.karinaksenovskaya.myapplication.domain.usecase

import com.karinaksenovskaya.myapplication.domain.repository.SearchRepository
import javax.inject.Inject

class SearchCityUseCase @Inject constructor(
    private val repository: SearchRepository
){
    suspend operator fun invoke(query: String) = repository.search(query)
}