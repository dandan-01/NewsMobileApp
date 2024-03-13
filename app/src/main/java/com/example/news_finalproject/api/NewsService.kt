package com.example.news_finalproject.api

import com.example.news_finalproject.model.News
import com.example.news_finalproject.model.Source
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    // always call parent News data and NOT Article
    @GET("top-headlines?country=us")
    fun getTopHeadLines(
        @Query("apiKey") apiKey: String
    ): Call<News>

    //
    @GET("everything")
    fun searchNewsByName (
        @Query("apiKey") apiKey: String,
        @Query("q") query: String
    ) : Call<News>
}