package com.example.news_finalproject.view

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.news_finalproject.api.NewsManager
import com.example.news_finalproject.api.NewsViewModel
import com.example.news_finalproject.components.NewsCard

@Composable
fun NewsScreen(newsManager: NewsManager, navController: NavHostController) {

    val news = newsManager.newsResponse.value
    Log.i("NewsScreen", "NewsResponse size: ${news.size}")
    Log.i("NewsScreen", "First article: ${news.firstOrNull()?.title}")

    for(article in news) {
        Log.i("title", "${article.title}")
    }

    //Column
    Column{
        // Trending News header
        Text(
            text = "Trending News",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(8.dp)
        )

        LazyColumn{
            items(news){
                article -> NewsCard(newsItem= article, navController)
            }
        }
    }
}