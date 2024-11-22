package com.karinaksenovskaya.myapplication.di

import com.karinaksenovskaya.myapplication.data.network.api.ApiFactory
import com.karinaksenovskaya.myapplication.data.network.api.ApiService
import com.karinaksenovskaya.myapplication.data.repository.SearchRepositoryImpl
import com.karinaksenovskaya.myapplication.data.repository.WeatherRepositoryImpl
import com.karinaksenovskaya.myapplication.domain.repository.SearchRepository
import com.karinaksenovskaya.myapplication.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {


    @[ApplicationScope Binds]
    fun bindWeatherRepository(impl: WeatherRepositoryImpl): WeatherRepository

    @[ApplicationScope Binds]
    fun bindSearchRepository(impl: SearchRepositoryImpl): SearchRepository

    companion object{

        @[ApplicationScope Provides]
        fun provideApiService(): ApiService = ApiFactory.apiService
    }
}