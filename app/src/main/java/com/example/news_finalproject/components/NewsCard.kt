package com.example.news_finalproject.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.news_finalproject.api.NewsViewModel
import com.example.news_finalproject.model.Article

@Composable
fun NewsCard(
    newsItem: Article,
    navController: NavController
) {
    var isIconChanged by remember {mutableStateOf(false)}

    Row(
        modifier = Modifier
            .padding(8.dp)
            .clickable { /* Handle click event */ }
            .fillMaxWidth()
            .border(1.dp, Color.Gray, shape = RectangleShape)
    ) {
        // Image
        Box(
            modifier = Modifier
                .width(120.dp)
                .height(120.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(newsItem.urlToImage)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }

        // Spacer between image and text
        Spacer(modifier = Modifier.width(8.dp))

        // Title and Description
        Column(
            modifier = Modifier
                .weight(1f) // Occupy remaining horizontal space
                .padding(vertical = 8.dp)
        ) {
            // Title
            Text(
                text = newsItem.title ?: "",
                maxLines = 2,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Description
            Text(
                text = newsItem.description ?: "",
                maxLines = 3,
                style = TextStyle(
                    fontSize = 16.sp
                ),
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}