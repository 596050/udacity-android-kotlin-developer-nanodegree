package com.udacity.asteroidradar

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.udacity.asteroidradar.api.NasaService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

fun getService(): NasaService {
    val json = Json {
        ignoreUnknownKeys = true
    }
//        val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
//        val client = OkHttpClient.Builder()
//            .addInterceptor(logging)
//            .build()
    val contentType = "application/json".toMediaType()
    return Retrofit
        .Builder()
//            .client(client)
        .addConverterFactory(json.asConverterFactory(contentType))
        .baseUrl(Constants.BASE_URL)
        .build()
        .create(NasaService::class.java)
}