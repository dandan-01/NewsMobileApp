package com.example.news_finalproject.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Divider
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.news_finalproject.Destination
import com.example.news_finalproject.R
import com.example.news_finalproject.api.NewsManager
import com.example.news_finalproject.api.NewsViewModel
import com.example.news_finalproject.components.NewsCard
import com.example.news_finalproject.model.Article

@Composable
fun SearchScreen(navController: NavController) {

    val viewModel: NewsViewModel = viewModel()
    val news: List<Article> = viewModel.news.value
    // logs
    Log.i("SearchScreen", "NewsResponse size: ${news.size}")
    Log.i("SearchScreen", "First article: ${news.firstOrNull()?.title}")

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
        // Row for popular searches
        Row(modifier = Modifier.fillMaxWidth()) {
            // Trending now
            Button(
                onClick = { navController.navigate(Destination.Article.route) },
                modifier = Modifier
                    .padding(0.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            ) {
                androidx.compose.material.Text(
                    text = "Trending News",
                    maxLines = 1,
                    color = Color(0xFF3D568E),
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Bitcoin button
            Button(
                onClick = { navController.navigate(Destination.Bitcoin.route) },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            ) {
                androidx.compose.material.Text(
                    text = "Bitcoin",
                    color = Color(0xFF3D568E),
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Ethereum button
            Button(
                onClick = { navController.navigate(Destination.Ethereum.route) },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            ) {
                androidx.compose.material.Text(
                    text = "Ethereum",
                    color = Color(0xFF3D568E),
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }

        // Trending News header
        Text(
            text = "Search results",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
        )

        // Row for the last article image and title
        Box(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            // AsyncImage for the last article image
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(news.lastOrNull()?.urlToImage) // Use imageUrl from the last article in news
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth() // Set size of the image
                    .height(200.dp) // Set the desired height of the image
                    .clip(shape = RoundedCornerShape(4.dp)) // Apply rounded corners to the image
            )

            // Title of the last article
            Text(
                text = news.lastOrNull()?.title ?: "", // Get the title of the last article
                color = Color.White,
                style = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                ),
                maxLines = 2, // Limit title to 2 lines
                overflow = TextOverflow.Ellipsis, // Add ellipsis if the title exceeds 2 lines
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp) // Add padding around the text
                    .background(color = Color.Black.copy(alpha = 0.25f)) // Semi-transparent black background
            )
        }

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(news) { article ->
                NewsCard(newsItem = article, navController)
            }
        }
    }
}