package com.example.news_finalproject.view

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.news_finalproject.R

@Composable
fun TopHeader(navController : NavController) {
    val icMenu = painterResource(id = R.drawable.ic_menu)
    val icAccount = painterResource(id = R.drawable.ic_account)
    val icGuide = painterResource(id = R.drawable.ic_guide)

    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp) // add space between Header and rest of the NewsScreen
    ) {
        // Navigation icon (Menu)
        IconButton(
            onClick = { /* Handle navigation icon click */ },
            modifier = Modifier.size(48.dp) // Set size of the IconButton
        ) {
            Icon(icMenu, contentDescription = "Menu")
        }

        // Spacer
        Spacer(modifier = Modifier.weight(1f))

        // Account icon
        IconButton(
            onClick = { /* Handle account icon click */ },
            modifier = Modifier.size(48.dp) // Set size of the IconButton
        ) {
            Icon(icAccount, contentDescription = "Account")
        }

        // Spacer
        Spacer(modifier = Modifier.width(16.dp))

        // Guide icon
        IconButton(
            onClick = { /* Handle guide icon click */ },
            modifier = Modifier.size(48.dp) // Set size of the IconButton
        ) {
            Icon(icGuide, contentDescription = "Guide")
        }
    }
}