package com.example.news_finalproject

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.news_finalproject.api.NewsManager
import com.example.news_finalproject.api.NewsViewModel
import com.example.news_finalproject.db.AppDatabase
import com.example.news_finalproject.model.Article
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.example.news_finalproject.ui.theme.News_FinalProjectTheme
import com.example.news_finalproject.view.NewsScreen
import com.example.news_finalproject.view.TopHeader

sealed class Destination(val route: String){

    // Destination.route.Article
    object Article: Destination("article")

    object Bitcoin: Destination("bitcoin")

    object Ethereum: Destination("ethereum")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            News_FinalProjectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    //fetch logged in user
                    val userID = intent.getStringExtra("userID")

                    // context
                    var context: Context = LocalContext.current

                    // db
                    val db = AppDatabase.getInstance(context)

                    // fetch news data from api
                    val newsManager: NewsManager = NewsManager(db)

                    // firestore
                    val fs_db = Firebase.firestore

                    // initialize view model
                    val viewModel : NewsViewModel = viewModel()

                    // moviescaffold
                    val navController = rememberNavController()
                    NewsScaffold(navController = navController, newsManager)
                }
            }
        }
    }
}

@Composable
fun NewsScaffold(navController: NavHostController, newsManager: NewsManager) {
    Scaffold(
        topBar = {
            TopHeader(navController = navController)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {

            //NavHost
            NavHost(navController = navController, startDestination = Destination.Article.route) {
                composable(Destination.Article.route) {
                    NewsScreen(newsManager, navController)
                }

                composable(Destination.Bitcoin.route)
                {
                    BitcoinScreen()
                }
            }
        }
    }
}