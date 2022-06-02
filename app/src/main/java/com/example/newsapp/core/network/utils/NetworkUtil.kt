package com.example.newsapp.core.network.utils

import android.content.Context
import com.example.newsapp.BuildConfig
import com.example.newsapp.R
import okhttp3.Headers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkUtil @Inject constructor(
    context: Context
) {

    val baseUrl = context.resources.getString(R.string.api_base_url)

    private val apiKey: String = context.resources.getString(R.string.api_key)

    val headers: Headers
        get() {
            val headerBuilder = Headers.Builder()
                .add(apiKey, BuildConfig.API_KEY)

            return headerBuilder.build()
        }

}