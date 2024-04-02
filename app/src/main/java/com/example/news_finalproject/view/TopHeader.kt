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
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.TextField
import androidx.compose.material3.IconButton
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.news_finalproject.R

@Composable
fun TopHeader(navController: NavController) {
    // Define the state for the menu
    var menuVisible by remember { mutableStateOf(false) }

    // define state for search field
    var searchVisible by remember { mutableStateOf(false) }

    // text field value when a user types in the search bar
    var text by remember { mutableStateOf("") }

    // icons
    val ic_menu = painterResource(id = R.drawable.ic_menu)
    val ic_account = painterResource(id = R.drawable.ic_account)
    val ic_guide = painterResource(id = R.drawable.ic_guide)
    val ic_search = painterResource(id = R.drawable.ic_search)
    val ic_bitcoin = painterResource(id = R.drawable.ic_bitcoin)
    val ic_ethereum = painterResource(id = R.drawable.ic_ethereum)
    val ic_stocks = painterResource(id = R.drawable.ic_stocks)

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
                onClick = { menuVisible = !menuVisible },
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Text field for search input
                        BasicTextField(
                            value = text,
                            onValueChange = { text = it },
                            modifier = Modifier
                                .weight(1f) // Take up remaining space
                                .padding(vertical = 8.dp) // Add vertical padding
                        )

                        // Search icon
                        IconButton(
                            onClick = { searchVisible = false },
                            modifier = Modifier.padding(horizontal = 8.dp) // Add padding for better visibility
                        ) {
                            Icon(
                                ic_search,
                                contentDescription = "Search"
                            )
                        }
                    }
                }
            }
        }

        // Overlay for the menu
        if (menuVisible) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.6f)) // Semi-transparent black background
                    .clickable(onClick = { menuVisible = false }) // Close menu when clicked outside the overlay
            ) {
                // Frame for the menu
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f) // Take up 50% of the width
                        .fillMaxHeight()
                        .background(Color.White)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(top = 40.dp) // Add top padding
                            .padding(horizontal = 8.dp)
                            .clickable { /* Handle menu item click */ }
                    ) {
                        // app title with logo
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(bottom = 40.dp)
                        ) {
                            Icon(ic_stocks, contentDescription = "CryptoNow Logo", modifier = Modifier.size(40.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "CRYPTO\nSOURCE",
                                style = TextStyle(
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }

                        // account button
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .padding(15.dp)
                                .clickable { /* Handle menu item click */ }
                        ) {
                            Icon(ic_account, contentDescription = "Account", modifier = Modifier.size(30.dp))
                            Text(
                                text = "Account",
                                style = TextStyle(
                                    fontSize = 16.sp
                                ),
                                modifier = Modifier.padding(top = 8.dp) // Add spacing between icon and text
                            )
                        }

                        HorizontalDivider()

                        // bitcoin button
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .padding(15.dp)
                                .clickable { /* Handle menu item click */ }
                        ) {
                            Icon(ic_bitcoin, contentDescription = "Bitcoin News", modifier = Modifier.size(30.dp))
                            Text(
                                text = "Bitcoin News",
                                style = TextStyle(
                                    fontSize = 16.sp
                                ),
                                modifier = Modifier.padding(top = 8.dp) // Add spacing between icon and text
                            )
                        }

                        HorizontalDivider()

                        // ethereum button
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .padding(15.dp)
                                .clickable { /* Handle menu item click */ }
                        ) {
                            Icon(ic_ethereum, contentDescription = "Ethereum News", modifier = Modifier.size(30.dp))
                            Text(
                                text = "Ethereum News",
                                style = TextStyle(
                                    fontSize = 16.sp
                                ),
                                modifier = Modifier.padding(top = 8.dp) // Add spacing between icon and text
                            )
                        }

                        HorizontalDivider()

                        // safe investing button
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .padding(15.dp)
                                .clickable { /* Handle menu item click */ }
                        ) {
                            Icon(ic_guide, contentDescription = "Guide", modifier = Modifier.size(30.dp))
                            Text(
                                text = "Guide to Safe Investing",
                                style = TextStyle(
                                    fontSize = 16.sp
                                ),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(top = 8.dp) // Add spacing between icon and text
                            )
                        }
                    }
                }
            }
        }

        // Row for popular searches
        Row(modifier = Modifier.fillMaxWidth()) {
            // Trending now
            Button(
                onClick = { /* Navigate to Ethereum Page */ },
                modifier = Modifier
                    .padding(0.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFFD9DFEC))
            ) {
                Text(
                    text = "Trending Now",
                    maxLines = 1,
                    color = Color(0xFF3D568E),
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Bitcoin button
            Button(
                onClick = { /* Navigate to Bitcoin Page */ },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            ) {
                Text(
                    text = "Bitcoin",
                    color = Color(0xFF3D568E),
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Ethereum button
            Button(
                onClick = { /* Navigate to Ethereum Page */ },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            ) {
                Text(
                    text = "Ethereum",
                    color = Color(0xFF3D568E),
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}

// create divider for menu
@Composable
fun HorizontalDivider() {
    Divider(
        color = Color.LightGray,
        thickness = 1.dp,
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth(0.8f) // Take up 80% of the width of the overlay
    )
}