package com.example.news_finalproject.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

//News is parent table, Article is child table
@Entity(tableName = "tbl_news")
@JsonClass(generateAdapter = true)
data class Article(
    @Json(name = "author")
    val author: String? = null,
    @Json(name = "content")
    val content: String? = null,
    @Json(name = "description")
    val description: String? = null,
    @Json(name = "publishedAt")
    val publishedAt: String? = null,
//    @Json(name = "source")
//    val source: Source? = null,
    @Json(name = "title")
    val title: String? = null,

    @PrimaryKey
    @Json(name = "url")
    val url: String,

    @Json(name = "urlToImage")
    val urlToImage: String? = null
)