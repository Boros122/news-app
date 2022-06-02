package com.example.newsapp.shared.ui

import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.NewsApplication
import com.example.newsapp.di.components.ApplicationComponent

abstract class BaseActivity: AppCompatActivity() {

    val appComponent: ApplicationComponent by lazy {
        (application as NewsApplication).appComponent
    }

    companion object {
        const val ACTIVITY_DATA_KEY = "activity_data_key"
    }

}