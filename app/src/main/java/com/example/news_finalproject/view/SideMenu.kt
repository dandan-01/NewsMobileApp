package com.example.news_finalproject.view

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.news_finalproject.Destination
import com.example.news_finalproject.components.NavItem
import kotlinx.coroutines.launch

@Composable
fun SideMenu(
    navController: NavController,
    menuVisible: Boolean,
    toggleMenu: () -> Unit
) {
    // menu drawerState
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Open)

    // coroutine for menu overlay
    val coroutineScope = rememberCoroutineScope()

    // selected item index
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }

    // menu items
    val items = listOf(
        NavItem(
            title = "Home",
            icon = Icons.Filled.Home,
            destination = Destination.Article.route
        ),
        NavItem(
            title = "Account",
            icon = Icons.Filled.AccountCircle,
            destination = Destination.Article.route
        ),
        NavItem(
            title = "Guide to Safe Investing",
            icon = Icons.Filled.Info,
            destination = Destination.Article.route
        ),
        NavItem(
            title = "Settings",
            icon = Icons.Filled.Settings,
            destination = Destination.Article.route
        )
    )

    // menu overlay
    if (menuVisible) {
        ModalNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet {
                    Spacer(modifier = Modifier.height(16.dp))

                    items.forEachIndexed { index, item ->
                        NavigationDrawerItem(
                            label = {
                                Text(text = item.title)
                            },
                            selected = index == selectedItemIndex,
                            onClick = {
                                navController.navigate(item.destination)
                                selectedItemIndex = index
                                coroutineScope.launch { drawerState.close() }
                                toggleMenu()
                            },
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.title
                                )
                            },
                            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                        )
                    }
                }
            },
            drawerState = drawerState
        ) {}
    }
}