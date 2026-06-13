package com.example.tulista.navigation

import android.R
import android.R.attr.text
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tulista.data.ListViewModel
import com.example.tulista.screens.CardScreen
import com.example.tulista.screens.HomeScreen
import com.example.tulista.screens.ListScreen
import com.example.tulista.ui.theme.bar
import com.example.tulista.ui.theme.letras
import com.example.tulista.ui.theme.ovalo
import java.nio.file.WatchEvent

sealed class BottomNavItem(val route: String, val icon: ImageVector, val title: String) {
    object Home : BottomNavItem("home", Icons.Default.Home, "Inicio")
    object Create : BottomNavItem("list", Icons.Default.Add, "Añadir")
    object Cart : BottomNavItem("cart", Icons.Default.ShoppingCart, "Cesta")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(){

    val navController = rememberNavController()
    val listViewModel: ListViewModel = viewModel()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "TuLista 📝",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = bar,
                    titleContentColor = letras
                )
            )
        },
        bottomBar = {
            NavigationBar (
                containerColor = bar
            ){
                val items = listOf(BottomNavItem.Home, BottomNavItem.Create, BottomNavItem.Cart)

                items.forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                        label = { Text(item.title) },
                        selected = currentRoute == item.route,
                        onClick = {
                            if (currentRoute != item.route) {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.White,
                            selectedTextColor = Color.White,
                            indicatorColor = ovalo
                        )
                    )
                }
            }
        }
    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(paddingValues)
        ){
            composable("home") {

                HomeScreen(
                    navController = navController, viewModel = listViewModel
                )
            }
            composable("list") {

                ListScreen(navController = navController, viewModel = listViewModel )
            }
            composable("cart") {

                CardScreen(navController = navController, viewModel = listViewModel)
            }
        }
    }
}