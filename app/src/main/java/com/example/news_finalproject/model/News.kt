package com.example.news_finalproject.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

// parent table that holds all array of info
@JsonClass(generateAdapter = true)
data class News(
    @Json(name = "articles")
    val articles: List<Article>,
    @Json(name = "status")
    val status: String,
    @Json(name = "totalResults")
    val totalResults: Int
)