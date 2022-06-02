package com.example.newsapp.di.modules

import com.example.newsapp.core.network.ApiDefinition
import com.example.newsapp.core.network.utils.NetworkUtil
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.*
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    private val timeOut = 60L

    @Singleton
    @Provides
    fun provideApiDefinition(retrofit: Retrofit): ApiDefinition {
        return retrofit.create(ApiDefinition::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        client: OkHttpClient,
        jsonConverter: Converter.Factory,
        networkUtil: NetworkUtil
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(networkUtil.baseUrl)
            .addConverterFactory(jsonConverter)
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun provideJsonConverter(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        @Named("headersInterceptor") headersInterceptor: Interceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .writeTimeout(timeOut, TimeUnit.SECONDS)
            .readTimeout(timeOut, TimeUnit.SECONDS)
            .connectTimeout(timeOut, TimeUnit.SECONDS)
            .addInterceptor(headersInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }
        return interceptor
    }

    @Singleton
    @Provides
    @Named("headersInterceptor")
    fun provideHeadersInterceptor(networkUtil: NetworkUtil): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()

            val request: Request = original.newBuilder().headers(networkUtil.headers).method(
                original.method,
                original.body
            ).build()

            chain.proceed(request)
        }
    }

}
