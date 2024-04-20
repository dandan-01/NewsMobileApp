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

// This Manager handles the newsResponse for EthereumScreen (when a user clicks on Ethereum in one of the main screens)
class EthereumManager(database: AppDatabase) {

    private var _newsResponse = mutableStateOf<List<Article>>(emptyList())
//    val apiKey:String = "069eb3bd53fc43e7b4650993a0859985"
    val apiKey:String = ""

    // assign news response
    val newsResponse : MutableState<List<Article>>
        get() = _newsResponse

    // call a method automatically
    init {
        // Fetch top headlines by default
        getNews(database)
    }

    private fun getNews(database : AppDatabase) {

        // Fetch news based on the specified query
        val service = Api.retrofitService.searchEthereum(apiKey)

        service.enqueue(object : Callback<News>{
            override fun onResponse(
                call: Call<News>,
                response: Response<News>
            ){
                if (response.isSuccessful) {
                    Log.i("EthereumManager", "API Response is successful")

                    val articles = response.body()?.articles
                    if (articles != null) {
                        Log.i("EthereumManager", "Number of articles received: ${articles.size}")
                        for (article in articles) {
                            Log.i("EthereumManager", "Article title: ${article.title}")
                        }
                        _newsResponse.value = articles

                        //GlobalScope
                        GlobalScope.launch {
                            saveDataToDatabase(database, _newsResponse.value)
                        }
                    } else {
                        Log.e("EthereumManager", "Response body is null or empty")
                    }
                } else {
                    Log.e("EthereumManager", "Unsuccessful response: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("EthereumManager", "API call failed: ${t.message}")
            }
        })
    }

    // open different thread
    private suspend fun saveDataToDatabase(database: AppDatabase, data: List<Article>) {
        database.newsDao().insertAllNews(data)
    }
}