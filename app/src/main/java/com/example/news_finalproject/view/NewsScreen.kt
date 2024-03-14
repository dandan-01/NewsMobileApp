package com.example.news_finalproject.view

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.news_finalproject.api.NewsManager
import com.example.news_finalproject.api.NewsViewModel
import com.example.news_finalproject.components.NewsCard

@Composable
fun NewsScreen(newsManager: NewsManager, navController: NavHostController) {

    val news = newsManager.newsResponse.value
    Log.i("NewsScreen", "NewsResponse size: ${news.size}")
    Log.i("NewsScreen", "First article: ${news.firstOrNull()?.title}")
    Text(text="News App screen")

    for(article in news) {
        Log.i("title", "${article.title}")
    }

    //Column
    Column{
        LazyColumn{
            items(news){
                article -> NewsCard(newsItem= article, navController)
            }
        }
    }
}