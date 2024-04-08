package com.example.news_finalproject.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.navigation.NavController
import com.example.news_finalproject.ui.theme.News_FinalProjectTheme
import com.example.news_finalproject.components.NavItem
import kotlinx.coroutines.launch

@Composable
fun MenuOverlay(navController: NavController) {
    @OptIn(ExperimentalMaterial3Api::class)
    Column {
        News_FinalProjectTheme {
            val items = listOf(
                NavItem(
                    title = "Home",
                    selectedIcon = Icons.Filled.Home,
                    unselectedIcon = Icons.Outlined.Home
                ),
                NavItem(
                    title = "Account",
                    selectedIcon = Icons.Filled.AccountCircle,
                    unselectedIcon = Icons.Outlined.AccountCircle
                ),
                NavItem(
                    title = "Search",
                    selectedIcon = Icons.Filled.Search,
                    unselectedIcon = Icons.Outlined.Search
                ),
                NavItem(
                    title = "Settings",
                    selectedIcon = Icons.Filled.Settings,
                    unselectedIcon = Icons.Outlined.Settings
                )
            )

            // surface
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                //
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

                // coroutine
                val coroutineScope = rememberCoroutineScope()

                //
                var selectedItemIndex by rememberSaveable {
                    mutableStateOf(0)
                }

                // Drawer = background overlay
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
                                        selectedItemIndex = index
                                        coroutineScope.launch {
                                            drawerState.close()
                                        }
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = if (index == selectedItemIndex) {
                                                item.selectedIcon
                                            } else item.unselectedIcon,
                                            contentDescription = item.title
                                        )
                                    },
                                    modifier = Modifier
                                        .padding(NavigationDrawerItemDefaults.ItemPadding)
                                        .clickable { // Add clickable modifier
                                            selectedItemIndex = index
                                            coroutineScope.launch {
                                                drawerState.close()
                                            }
                                        }
                                )
                            }
                        }
                    },
                    drawerState = drawerState
                ) { }
            }
        }
    }
}