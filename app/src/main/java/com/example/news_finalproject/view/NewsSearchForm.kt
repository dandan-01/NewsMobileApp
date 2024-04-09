package com.example.news_finalproject.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.news_finalproject.api.NewsViewModel
import com.example.news_finalproject.components.NewsCard
import com.example.news_finalproject.model.Article

// TODO NOT SURE IF THIS IS THE CORRECT WAY. to be discarded?

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NewsSearchForm(navController: NavController){
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val viewModel: NewsViewModel = viewModel()
//    val news: List<Article> = viewModel.news.value // get searched news

    // Observe news updates from the ViewModel
    val news by remember(viewModel) {
        viewModel.news
    }

    var query by remember { mutableStateOf("")}

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ){
        OutlinedTextField(
            value =  query,
            onValueChange = { query = it },
            label = {Text("Search")},
            keyboardOptions = KeyboardOptions(imeAction= ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                // add view model in a bit
                viewModel.searchNewsByName(query)
                keyboardController?.hide()
            }),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        Button(
            onClick ={
                // view model to go next
                viewModel.searchNewsByName(query)
            }
        ){
            Text("Search")
        }
        // thread aka coroutine in android
        LaunchedEffect(viewModel){
            viewModel.searchNewsByName(query)
        }

        LazyColumn{
            items(news){ article ->
                NewsCard(newsItem = article, navController = navController)

            }
        }

    }
}