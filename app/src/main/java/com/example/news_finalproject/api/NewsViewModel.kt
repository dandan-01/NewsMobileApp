package com.example.news_finalproject.api

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import com.example.news_finalproject.db.AppDatabase
import com.example.news_finalproject.model.Article
import com.example.news_finalproject.model.News
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel : ViewModel() {
    // MutableState for holding current state of news
    private val news = mutableStateOf<List<Article>>(emptyList())

    // MutableStateFlow for exposing news state as a flow
    private val _newsStateFlow = MutableStateFlow(news.value)

    // Expose news state as StateFlow
    val newsStateFlow: StateFlow<List<Article>> = _newsStateFlow

    // api key
    val api_key: String = "069eb3bd53fc43e7b4650993a0859985"

    // Search movies
    fun searchNews(newsName: String, database: AppDatabase) {
        if (newsName.isNotBlank()) {
            val service = Api.retrofitService.searchNewsByName(api_key, newsName)

            service.enqueue(object : Callback<News> {
                override fun onResponse(
                    call: Call<News>,
                    response: Response<News>) {
                    if (response.isSuccessful) {
                        Log.i("DS", "testing testing")
                        news.value = response.body()?.articles?:emptyList()
                        Log.i("Article found", news.toString())

                        // GlobalScope
                        GlobalScope.launch {
                            database.newsDao().insertAllNews(news = news.value)
                        }
                    }
                }

                override fun onFailure(call: Call<News>, t: Throwable) {
                    // Log.d = debug
                    // Log.i = information
                    Log.d("error", "${t.message}")
                }
            })

        } else {
            news.value = emptyList()
        }
    }
}