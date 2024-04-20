package com.example.news_finalproject

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.example.news_finalproject.api.BitcoinManager
import com.example.news_finalproject.api.EthereumManager
import com.example.news_finalproject.api.NewsManager
import com.example.news_finalproject.api.NewsViewModel
import com.example.news_finalproject.api.jsoup.JsoupArticleUrl
import com.example.news_finalproject.api.jsoup.WebViewWithCss
import com.example.news_finalproject.auth.AccountScreen
import com.example.news_finalproject.auth.RegisterScreen
import com.example.news_finalproject.auth.SignInScreen
import com.example.news_finalproject.components.NavItem
import com.example.news_finalproject.components.NewsDetailCard
import com.example.news_finalproject.db.AppDatabase
import com.example.news_finalproject.model.Article
import com.example.news_finalproject.model.News
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.example.news_finalproject.ui.theme.News_FinalProjectTheme
import com.example.news_finalproject.view.BitcoinScreen
import com.example.news_finalproject.view.EthereumScreen
import com.example.news_finalproject.view.NewsScreen
import com.example.news_finalproject.view.TopHeader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// Represents different destinations and routes. The sealed modifier, means all subclasses must be declared in the same file as the sealed class itself. This makes it easier to manage and understand all possible destinations in one place.
sealed class Destination(val route: String){

    // HOME
    object Article: Destination("article")

    object Bitcoin: Destination("bitcoin")

    object Ethereum: Destination("ethereum")

    object Account: Destination("account")

    object Register: Destination("register")

    // holds website content about Crypto Investing
    object JsoupGuide: Destination("jsoup")

    // holds website content about Article Url
    object JsoupUrl: Destination("jsoupUrl/{url}")

    // holds individual news articles
    object NewsDetail: Destination("newsDetail")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            News_FinalProjectTheme {
                // A surface container
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // fetch logged in user from intents
                    val userID = intent.getStringExtra("userID")

                    // grab the current context
                    var context: Context = LocalContext.current

                    // initialize database
                    val db = AppDatabase.getInstance(context)

                    // fetch news data from api based on managers
                    val newsManager: NewsManager = NewsManager(db)
                    val bitcoinManager: BitcoinManager = BitcoinManager(db)
                    val ethereumManager: EthereumManager = EthereumManager(db)

                    // access firestore database
                    val fs_db = Firebase.firestore

                    // initialize view model
                    val viewModel : NewsViewModel = viewModel()

                    // moviescaffold
                    val navController = rememberNavController()
                    NewsScaffold(navController = navController, newsManager, bitcoinManager, ethereumManager, viewModel, db, fs_db)
                }
            }
        }
    }
}

// Represents the basic structure for the App, TopHeader + whichever screen the user selects
@Composable
fun NewsScaffold(navController: NavHostController, newsManager: NewsManager, bitcoinManager: BitcoinManager, ethereumManager: EthereumManager, viewModel : NewsViewModel, db : AppDatabase, fs_db: FirebaseFirestore) {
    var newsItem by remember {
        mutableStateOf<Article?>(null)
    }

    Scaffold(
        // handles top header
        topBar = {
            TopHeader(navController = navController)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {

            // NavHost holds all composable functions for each screen
            NavHost(navController = navController, startDestination = Destination.Article.route) {

                // this is the HOME screen
                composable(Destination.Article.route) {
                    NewsScreen(newsManager, navController)
                }

                composable(Destination.Bitcoin.route)
                {
                    BitcoinScreen(bitcoinManager, navController)
                }

                composable(Destination.Ethereum.route)
                {
                    EthereumScreen(ethereumManager, navController)
                }

                composable(Destination.Account.route) {
                    AccountScreen(navController) //this is in auth.kt
                }

                composable(Destination.Register.route) {
                    RegisterScreen(navController) //this is in Register.kt
                }

                // jsoup guide
                composable(Destination.JsoupGuide.route) {
                    WebViewWithCss()
                }

                // jsoup article url
                composable(Destination.JsoupUrl.route + "/{url}") { navBackStackEntry ->
                    val url = Uri.decode(navBackStackEntry.arguments?.getString("url") ?: "")
                    JsoupArticleUrl(url = url, navController)
                }

                // Initially, I launched coroutines directly using GlobalScope.launch.
                // The problem was that I needed to use LaunchedEffect inside the composable function to perform asynchronous operations instead.
                // I also needed to use mutableStateOf to manage UI state and trigger recomposition when state changes occur
                composable(Destination.NewsDetail.route + "/{url}") { navBackStackEntry ->
                    val url: String? = navBackStackEntry.arguments?.getString("url")

                    // State to hold the retrieved news item
                    var newsItemState by remember { mutableStateOf<Article?>(null) }

                    LaunchedEffect(url) {
                        url?.let {
                            val fetchedNewsItem = withContext(Dispatchers.IO) {
                                db.newsDao().getNewsById(url)
                            }
                            // Update the state with the fetched news item
                            newsItemState = fetchedNewsItem
                        }
                    }

                    // Display the NewsDetailCard if newsItemState is not null
                    newsItemState?.let { newsItem ->
                        NewsDetailCard(newsItem = newsItem, navController)
                    }
                }
            }
        }
    }
}