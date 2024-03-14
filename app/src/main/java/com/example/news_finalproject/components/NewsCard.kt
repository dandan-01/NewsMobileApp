package com.example.news_finalproject.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
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

    Column(
        modifier = Modifier
            .border(1.dp, Color.Red, shape=RectangleShape)
            .padding(5.dp)
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(
                    LocalContext.current
                ).data("${newsItem.urlToImage}").build(),
                contentDescription = "${newsItem.title ?: ""}"
            )

            // Another way to write modifer = Modifier is to pass it directly
            Column(Modifier.padding(20.dp)) {
                newsItem.title?.let {
                    Text(
                        color = Color.Black,
                        text = "${newsItem.title ?: ""}",
                        style = TextStyle(fontSize = 16.sp),
                        maxLines = 1
                    )
                }

                newsItem.description?.let {
                    Text(
                        color = Color.Black,
                        text = "${newsItem.description ?: ""}",
                        style = TextStyle(fontSize=16.sp),
                        maxLines = 1
                    )
                }
            }
        }
    }
}