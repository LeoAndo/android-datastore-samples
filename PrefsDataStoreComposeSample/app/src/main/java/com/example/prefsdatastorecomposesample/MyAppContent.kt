package com.example.prefsdatastorecomposesample

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.prefsdatastorecomposesample.ui.account.AccountScreen
import com.example.prefsdatastorecomposesample.ui.extentions.mainContentPadding
import com.example.prefsdatastorecomposesample.ui.home.HomeScreen
import com.example.prefsdatastorecomposesample.ui.theme.PrefsDataStoreComposeSampleTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MyAppContent() {
    PrefsDataStoreComposeSampleTheme {
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