package com.example.news_finalproject.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.news_finalproject.Destination
import com.example.news_finalproject.model.Article
import com.google.firebase.firestore.FirebaseFirestore

// Composable function to display the details of a news article
@Composable
fun NewsDetailCard(
    newsItem: Article,
    //fs_db: FirebaseFirestore
) {
    // Layout for displaying news details
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        // Display the title of the news article
        Text(
            text = newsItem.title ?: "",
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Display other details like author, date, content, etc.
        Text(
            text = "Author: ${newsItem.author}",
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Text(
            text = "Published at: ${newsItem.publishedAt}",
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Text(
            text = newsItem.description ?: "",
        )
    }
}

// In your BitcoinScreen or wherever you navigate to the news detail screen
// Pass the selected news item to the NewsDetailCard composable
fun navigateToNewsDetail(navController: NavHostController, newsItem: Article) {
    navController.navigate("${Destination.NewsDetail.route}/${newsItem.url}")
}
