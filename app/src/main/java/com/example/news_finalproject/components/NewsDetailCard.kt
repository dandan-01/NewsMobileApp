package com.example.news_finalproject.components

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.news_finalproject.Destination
import com.example.news_finalproject.model.Article
import com.google.firebase.firestore.FirebaseFirestore

// Composable function to display the details of a news article
@Composable
fun NewsDetailCard(
    newsItem: Article,
    navController: NavHostController, // NavController to handle navigation
    // fs_db
) {
    // context
    val context = LocalContext.current

    // Layout for displaying news details
    Column(
        modifier = Modifier
            .padding(22.dp)
            .fillMaxWidth()
    ) {
        // Back button to navigate back to the previous page
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navController.popBackStack()
                }
        ) {
            Icon(
                painter = painterResource(id = com.example.news_finalproject.R.drawable.ic_back_arrow),
                contentDescription = "Back",
                tint = Color.Black
            )
            Text(
                text = "Back",
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        // title
        Text(
            text = newsItem.title ?: "",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // image
        newsItem.urlToImage?.let { imageUrl ->
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(newsItem.urlToImage)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth() // Set size of the image
                        .height(200.dp) // Set the desired height of the image
                        .clip(shape = RoundedCornerShape(4.dp))
                )
            }
        }

        // author
        Text(
            text = "Author: ${newsItem.author}",
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        // date and time of publish
        Text(
            text = "Published at: ${newsItem.publishedAt}",
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        // description
        Text(
            text = newsItem.description ?: "",
            modifier = Modifier.padding(bottom = 40.dp)
        )

        Text(
            text = "Read more"
        )

        // url
        Text(
            text = newsItem.url,
            color = Color.Blue, // Change color as desired
            modifier = Modifier.clickable {
                navController.navigate("${Destination.JsoupUrl.route}/${Uri.encode(newsItem.url)}")
            }
        )

        Spacer(modifier = Modifier.weight(1f)) // This pushes the button to the bottom

        // Add FloatingActionButton for bookmarking
        FloatingActionButton(
            onClick = {
                saveArticleToFirestore(newsItem, context)
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally) // Centering the FAB horizontally
        ) {
            Icon(
                painter = painterResource(id = com.example.news_finalproject.R.drawable.ic_bookmark),
                contentDescription = ""
            )
        }
    }
}

fun saveArticleToFirestore(newsItem: Article, context: android.content.Context) {
    val db = FirebaseFirestore.getInstance()

    // Creating a map for the data to be saved
    val articleMap = mapOf(
        "title" to (newsItem.title ?: ""),
        "author" to (newsItem.author ?: ""),
        "description" to (newsItem.description ?: ""),
        "url" to newsItem.url,
        "date_saved" to java.util.Date()
    )

    db.collection("saved_articles").add(articleMap)
        .addOnSuccessListener { documentReference ->
            // Success handling, maybe show a toast
            Toast.makeText(context, "Article saved successfully!", Toast.LENGTH_SHORT).show()
        }
        .addOnFailureListener { e ->
            // Failure handling, maybe show a toast
            Toast.makeText(context, "Failed to save article.", Toast.LENGTH_SHORT).show()
        }
}