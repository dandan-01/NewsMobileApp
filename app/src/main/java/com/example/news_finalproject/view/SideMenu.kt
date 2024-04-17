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

// This function handles the menu click event. It opens up a Left-hand side panel with predefined items
@Composable
fun SideMenu(
    navController: NavController,
    menuVisible: Boolean, // indicates if side menu is visible
    toggleMenu: () -> Unit // used to toggle visibility of side menu (this is handled within TopHeader as well)
) {
    // menu drawerState, manages the closing and opening of the drawer
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Open)

    // coroutine for menu overlay (asynchronous operations)
    val coroutineScope = rememberCoroutineScope()

    // keeps track of the selected item within the index
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }

    // define list of menu items
    val items = listOf(
        NavItem(
            title = "Home",
            icon = Icons.Filled.Home,
            destination = Destination.Article.route
        ),
        NavItem(
            title = "Account",
            icon = Icons.Filled.AccountCircle,
            destination = Destination.Account.route
        ),
        NavItem(
            title = "Guide to Safe Investing",
            icon = Icons.Filled.Info,
            destination = Destination.Article.route
        )
    )

    // if menu is visible (or toggled on) then open up a ModalNavigationDrawer
    if (menuVisible) {
        // handles the opening and closing of the drawer
        ModalNavigationDrawer(
            drawerContent = {

                // represents actual menu content
                ModalDrawerSheet {
                    Spacer(modifier = Modifier.height(16.dp))

                    // iterates through the list of menu items
                    items.forEachIndexed { index, item ->
                        NavigationDrawerItem(
                            label = {
                                Text(text = item.title)
                            },
                            // whether the current item is selected or not
                            selected = index == selectedItemIndex,
                            onClick = {
                                navController.navigate(item.destination) //navigates to the destination route
                                selectedItemIndex = index // updates selected item index
                                coroutineScope.launch { drawerState.close() } // close drawer
                                toggleMenu() // toggles menu visibility
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