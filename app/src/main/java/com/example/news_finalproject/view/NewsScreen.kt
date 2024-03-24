package com.example.news_finalproject.view

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.news_finalproject.R
import com.example.news_finalproject.api.NewsManager
import com.example.news_finalproject.api.NewsViewModel
import com.example.news_finalproject.components.NewsCard

@Composable
fun NewsScreen(newsManager: NewsManager, navController: NavHostController) {

    // logs
    val news = newsManager.newsResponse.value
    Log.i("NewsScreen", "NewsResponse size: ${news.size}")
    Log.i("NewsScreen", "First article: ${news.firstOrNull()?.title}")

    for(article in news) {
        Log.i("title", "${article.title}")
    }

    // icons
    val ic_bitcoin = painterResource(id = R.drawable.ic_bitcoin)
    val ic_ethereum = painterResource(id = R.drawable.ic_ethereum)

    //Column
    Column(modifier = Modifier.padding(8.dp)) {
        // Row for Buttons
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(
                onClick = { /* Navigate to Bitcoin Page */ },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 4.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
            ) {
                Icon(ic_bitcoin, contentDescription = "Account", modifier = Modifier.size(24.dp))
                Text("Bitcoin")
            }
            Button(
                onClick = { /* Navigate to Ethereum Page */ },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 4.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
            ) {
                Icon(ic_ethereum, contentDescription = "Account", modifier = Modifier.size(24.dp))
                Text("Ethereum")
            }
        }

        // Trending News header
        Text(
            text = "Trending News",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
        )

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(news) { article ->
                NewsCard(newsItem = article, navController)
            }
        }
    }
}