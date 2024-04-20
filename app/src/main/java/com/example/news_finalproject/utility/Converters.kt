package com.example.news_finalproject.utility

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

// Converters are used to convert JSON file into a list of integers and vice versa
class Converters {
    private val moshi: Moshi = Moshi.Builder().build()

    // converts JSON string into a list of integers
    fun fromString(value: String?): List<Int>? {
        if (value == null) return null

        val listType = Types.newParameterizedType(List::class.java, Integer::class.javaObjectType)
        val adapter: JsonAdapter<List<Int>> = moshi.adapter(listType)

        return adapter.fromJson(value)
    }

    // converts list of integers into JSON string
    @TypeConverter
    fun listToString(list: List<Int>?): String? {
        if (list == null) return null

        val listType = Types.newParameterizedType(List::class.java, Integer::class.javaObjectType)
        val adapter: JsonAdapter<List<Int>> = moshi.adapter(listType)

        return adapter.toJson(list)
    }
}