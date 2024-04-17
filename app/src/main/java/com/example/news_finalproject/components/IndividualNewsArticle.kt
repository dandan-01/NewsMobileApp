package com.example.news_finalproject.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.news_finalproject.model.Article
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun IndividualNewsArticle(
    newsItem: Article,
    fsDb: FirebaseFirestore,
    onAddToLibraryClick: () -> Unit // Callback for handling add to library button click
) {
    var movieExists by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .background(color = Color.DarkGray)
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(5.dp)
    ) {
        // AsyncImage for the news image
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(newsItem.urlToImage)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Column(Modifier.padding(20.dp)) {
            // Title
            Text(
                color = Color.White,
                text = newsItem.title ?: "",
                modifier = Modifier.padding(top = 8.dp, end = 8.dp),
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.padding(5.dp))

            // Button to add to library
            Button(
                onClick = onAddToLibraryClick, // Call the callback function when the button is clicked
            ) {
                Text(text = "Add to Library") // Provide text for the button
            }
        }
    }
}