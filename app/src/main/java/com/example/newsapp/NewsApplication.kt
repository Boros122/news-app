package com.example.newsapp

import android.app.Application
import com.example.newsapp.di.components.ApplicationComponent
import com.example.newsapp.di.components.DaggerApplicationComponent

open class NewsApplication: Application() {

    val appComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
    }

}