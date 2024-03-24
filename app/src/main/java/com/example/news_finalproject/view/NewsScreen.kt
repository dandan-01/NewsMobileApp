package com.example.news_finalproject.view

import android.util.Log
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Divider
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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
    val ic_stocks = painterResource(id = R.drawable.ic_stocks)

    //Column
    Column(modifier = Modifier
        .padding(start = 10.dp, end = 10.dp)
    ) {
        // app title with logo
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "CRYPTOSOURCE",
                style = TextStyle(
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(8.dp)
            )
        }

        // Divider
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth() // Occupy maximum width
        ) {
            Divider(
                color = Color.LightGray,
                thickness = 1.dp,
                modifier = Modifier
                    .fillMaxWidth(0.9f) // Set width to 80% of the parent's width
                    .padding(vertical = 8.dp) // Add vertical padding
            )
        }

        Text(
            text = "Popular Searches",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
        )

        // Row for popular searches
        Row(modifier = Modifier.fillMaxWidth()) {
            // bitcoin
            Button(
                onClick = { /* Navigate to Bitcoin Page */ },
                modifier = Modifier
                    .padding(horizontal = 2.dp, vertical = 4.dp) // Decrease padding,
                    .padding(start = 0.dp), // Remove start padding
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            ) {
                Text(
                    text = "Bitcoin",
                    color = Color(0xFF800080),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            // ethereum
            Button(
                onClick = { /* Navigate to Ethereum Page */ },
                modifier = Modifier
                    .padding(horizontal = 2.dp, vertical = 4.dp), // Decrease padding,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            ) {
                Text(
                    text = "Ethereum",
                    color = Color(0xFF800080),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            // trending now
            Button(
                onClick = { /* Navigate to Ethereum Page */ },
                modifier = Modifier
                    .padding(horizontal = 2.dp, vertical = 4.dp), // Decrease padding,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            ) {
                Text(
                    text = "Trending Now",
                    maxLines = 1,
                    color = Color(0xFF800080),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }

        // Trending News header
        Text(
            text = "Trending Crypto News",
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