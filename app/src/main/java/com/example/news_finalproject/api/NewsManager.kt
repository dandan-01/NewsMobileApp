package com.example.news_finalproject.api

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.news_finalproject.db.AppDatabase
import com.example.news_finalproject.model.Article
import com.example.news_finalproject.model.News
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsManager(database: AppDatabase) {

    private var _newsResponse = mutableStateOf<List<Article>>(emptyList())
    val apiKey:String = "069eb3bd53fc43e7b4650993a0859985"

    // assign news response
    val newsResponse : MutableState<List<Article>>
        @Composable get() = remember{
            _newsResponse
        }

    // call a method automatically
    init {
        getNews(database)
    }

    private fun getNews(database : AppDatabase) {
        val service = Api.retrofitService.getTopHeadLines(apiKey)

        service.enqueue(object : Callback<News>{
            override fun onResponse(
                call: Call<News>,
                response: Response<News>
            ){
                if (response.isSuccessful) {
                    Log.i("Data", "Data loaded")
                    _newsResponse.value = response.body()?.articles?: emptyList()
                    Log.i("DataStream", _newsResponse.toString())

                    //GlobalScope
                    GlobalScope.launch{
                        saveDataToDatabase(database = database, _newsResponse.value)
                    }
                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("error", "${t.message}")
            }
        })
    }

    // open different thread
    private suspend fun saveDataToDatabase(database: AppDatabase, data: List<Article>) {
        database.newsDao().insertAllNews(data)
    }
}