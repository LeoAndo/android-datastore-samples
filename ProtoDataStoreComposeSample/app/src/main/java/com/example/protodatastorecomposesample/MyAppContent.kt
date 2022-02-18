package com.example.protodatastorecomposesample

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.protodatastorecomposesample.ui.theme.ProtoDataStoreComposeSampleTheme

@Composable
internal fun MyAppContent() {
    ProtoDataStoreComposeSampleTheme {
        var selectedItem by remember { mutableStateOf(0) }
        val items = listOf(TopDestinations.HomeRoute, TopDestinations.AccountRoute)

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
            content = {
                Column(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        MyAppNavigationGraph(
                            modifier = Modifier.padding(PaddingValues(12.dp)),
                            startDestination = items[selectedItem].routeName
                        )
                    }

                    Column {
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
                    }
                }
            }
        )
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
            Text(text = "home")
        })

        composable(route = TopDestinations.AccountRoute.routeName, content = {
            Text(text = "account name")
        })
    }
}