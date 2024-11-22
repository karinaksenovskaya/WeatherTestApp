package com.karinaksenovskaya.myapplication

import android.app.Application
import com.karinaksenovskaya.myapplication.di.ApplicationComponent
import com.karinaksenovskaya.myapplication.di.DaggerApplicationComponent

class WeatherApp : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.factory().create(this)
    }
}