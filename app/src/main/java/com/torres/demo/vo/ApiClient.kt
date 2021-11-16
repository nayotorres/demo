package com.torres.demo.vo

import com.torres.demo.utilities.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    private val retrofit: Retrofit

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(Constants.REST_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .client(createRequest())
            .build()
    }

    fun <S> createService(serviceClass: Class<S>): S{
        return retrofit.create(serviceClass)
    }

    private fun createRequest(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(20, TimeUnit.SECONDS)
            .connectTimeout(20, TimeUnit.SECONDS)
            .build()
    }
}