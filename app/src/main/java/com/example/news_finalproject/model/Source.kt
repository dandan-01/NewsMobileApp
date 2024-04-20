package com.example.news_finalproject.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

// not used for my project but was automatically created during the JSON implementation 'Kotlin data class file from JSON'
@JsonClass(generateAdapter = true)
data class Source(
    @Json(name = "id")
    val id: String? = null,
    @Json(name = "name")
    val name: String? = null
)