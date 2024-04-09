package com.example.news_finalproject.view

import androidx.compose.runtime.*
import android.view.MenuItem
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Surface
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.ModalDrawer
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.TextField
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.news_finalproject.Destination
import com.example.news_finalproject.R
import com.example.news_finalproject.api.NewsViewModel
import com.example.news_finalproject.components.NavItem
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.news_finalproject.components.NewsCard
import com.example.news_finalproject.db.AppDatabase
import com.example.news_finalproject.model.Article
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TopHeader(navController: NavController) {
    // Define the state for the menu
    var menuVisible by remember { mutableStateOf(false) }

    // define state for search field
    var searchVisible by remember { mutableStateOf(false) }

    // text field value when a user types in the search bar
    var text by remember { mutableStateOf("") }

    // get db
//    val db = AppDatabase.getInstance(LocalContext.current)
    val context = LocalContext.current

    // keyboard controller
    val keyboardController = LocalSoftwareKeyboardController.current

    // viewModel
    val viewModel: NewsViewModel = viewModel()

    //get searched articles
    val news: List<Article> = viewModel.news.value

    // icons
    val ic_menu = painterResource(id = R.drawable.ic_menu)
    val ic_account = painterResource(id = R.drawable.ic_account)
    val ic_guide = painterResource(id = R.drawable.ic_guide)
    val ic_search = painterResource(id = R.drawable.ic_search)
    val ic_bitcoin = painterResource(id = R.drawable.ic_bitcoin)
    val ic_ethereum = painterResource(id = R.drawable.ic_ethereum)
    val ic_stocks = painterResource(id = R.drawable.ic_stocks)

    // menu drawerState
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    // coroutine for menu overlay
    val coroutineScope = rememberCoroutineScope()

    //Column
    Column(
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth() // Occupy maximum width
                .background(Color(0xFF3D568E)), // Set light blue background color
            verticalAlignment = Alignment.CenterVertically // Align children vertically
        ) {
            // Navigation icon (Menu)
            IconButton(
                onClick = {
                    menuVisible = !menuVisible
                    if (menuVisible) {
                        coroutineScope.launch {
                            drawerState.open()
                        }
                    } else {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                    }
                },
                modifier = Modifier.size(48.dp), // Set size of the IconButton
            ) {
                Icon(ic_menu, contentDescription = "Menu", tint = Color.White)
            }

            // Text field for search input
            Text(
                text = "CRYPTOSOURCE",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                ),
                modifier = Modifier.padding(8.dp)
            )

            // Spacer to push the search icon to the right
            Spacer(modifier = Modifier.weight(1f))

            // Search icon
            IconButton(
                onClick = {
                    searchVisible = !searchVisible
                },
                modifier = Modifier.size(48.dp) // Set size of the IconButton
            ) {
                Icon(ic_search, contentDescription = "Search", tint = Color.White)
            }
        }

        // Overlay for the search
        if (searchVisible) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.75f)) // Semi-transparent black background
                    .clickable(onClick = { searchVisible = false }) // Close search when clicked outside the overlay
            ) {
                // Frame for the search
                Box(
                    modifier = Modifier
                        .fillMaxWidth() // Take up the full width
                        .background(Color.White)
                        .padding(horizontal = 16.dp) // Add horizontal padding for better visibility
                ) {
                    // Row to hold the text field and search icon
                    Column (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        OutlinedTextField(
                            value =  text,
                            onValueChange = { text = it },
                            label = {Text("Search")},
                            keyboardOptions = KeyboardOptions(imeAction= ImeAction.Search),
                            keyboardActions = KeyboardActions(onSearch = {
                                // add view model in a bit
                                viewModel.searchNewsByName(text)
                                keyboardController?.hide()
                            }),
                            modifier = Modifier
                                .fillMaxWidth()
                        )

                        // add space
                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick ={
                                // view model to go next
                                //navController.navigate(Destination.Search.route) put this in NewsViewModel instead
                                searchVisible = false
                                viewModel.searchNewsByName(text)
                            }
                        ){
                            Text(
                                "Submit",
                                color = Color.White)
                        }
                        // thread aka coroutine in android
                        LaunchedEffect(news) {
                            if (news.isNotEmpty()) {
                                navController.navigate(Destination.Search.route)
                            }
                        }

                        LazyColumn{
                            items(news){ article ->
                                NewsCard(newsItem = article, navController)
                            }
                        }
                    }
                }
            }
        }

        // menu overlay
        if (menuVisible) {
            ModalNavigationDrawer(
                drawerContent = {
                    ModalDrawerSheet {
                        Text( text = "Home")
                    }
                },
                drawerState = drawerState
            ) {}
        }
    }
}