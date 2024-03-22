package com.example.news_finalproject.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object Api {
    private val BASE_URL = "https://newsapi.org/v2/"

    // parse JSON files for usage in Kotlin & Compose

    // create moshi
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    // create retrofit
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

    // instantiate class
    val retrofitService: NewsService by lazy{
        retrofit.create(NewsService::class.java)
    }
}