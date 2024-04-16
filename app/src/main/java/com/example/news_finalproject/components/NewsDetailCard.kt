package com.example.news_finalproject.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.news_finalproject.model.Article
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun NewsDetailCard(
    newsItem: Article,
    //title:String
    fs_db:FirebaseFirestore
) {
    var lastInsertedDocument by remember {
        mutableStateOf<DocumentReference?>(null)
    }
    Column(
        modifier = Modifier
            //.border(2.dp, Color.Black, shape = CircleShape)
            .background(color = Color.DarkGray)
            .fillMaxWidth()
            .fillMaxHeight()
            //.height(80.dp)
            .padding(5.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(newsItem.urlToImage)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Column(Modifier.padding(20.dp)) {
            newsItem.title?.let {
                Text(
                    color = Color.White,
                    text = it,
                    modifier = Modifier
                        .padding(top = 8.dp, end = 8.dp),
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
                )
            }
            Spacer(modifier = Modifier.padding(5.dp))
            Row {
                Button(
                    onClick = {
                        val library: CollectionReference =
                            FirebaseFirestore.getInstance().collection("movies_library")
                        var movieExists: Boolean? = null

                        val movie = hashMapOf(
                            "movie_id" to "${newsItem.url}",
                            "movie_title" to "${newsItem.title}"
                        )
                        GlobalScope.launch {
                            movieExists = doesMovieExist(newsItem.url.toString(), library)


                        }
                    }, //end onclick

                ) {
                }
            }
        }
    }
}
suspend fun doesMovieExist(movieID:String, collection:CollectionReference): Boolean{
    val querySnapshot = collection.whereEqualTo("movie_id", movieID).get().await()
    return !querySnapshot.isEmpty
}