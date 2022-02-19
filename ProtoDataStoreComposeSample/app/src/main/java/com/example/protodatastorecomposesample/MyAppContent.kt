package com.example.protodatastorecomposesample

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.protodatastorecomposesample.ui.account.AccountScreen
import com.example.protodatastorecomposesample.ui.extentions.mainContentPadding
import com.example.protodatastorecomposesample.ui.home.HomeScreen
import com.example.protodatastorecomposesample.ui.theme.ProtoDataStoreComposeSampleTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MyAppContent() {
    ProtoDataStoreComposeSampleTheme {
        var selectedItem by remember { mutableStateOf(0) }
        val items = listOf(TopDestinations.HomeRoute, TopDestinations.AccountRoute)

        Scaffold(
            bottomBar = {
                NavigationBar {
                    items.forEachIndexed { index, item ->
                        NavigationBarItem(
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label) },
                            selected = selectedItem == index,
                            onClick = { selectedItem = index }
                        )
                    }
                }
            },
            content = {
                Box(
                    modifier = Modifier.mainContentPadding(it)
                ) {
                    MyAppNavigationGraph(startDestination = items[selectedItem].routeName)
                }
            })
    }
}

@Composable
private fun MyAppNavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        composable(route = TopDestinations.HomeRoute.routeName, content = {
            HomeScreen()
        })

        composable(route = TopDestinations.AccountRoute.routeName, content = {
            AccountScreen()
        })
    }
}