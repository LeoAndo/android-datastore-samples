package com.example.prefsdatastorecomposesample

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

internal sealed class TopDestinations(
    val routeName: String,
    val icon: ImageVector,
    val label: String
) {
    object HomeRoute : TopDestinations(routeName = "home", icon = Icons.Filled.Home, label = "Home")
    object AccountRoute :
        TopDestinations(routeName = "account", icon = Icons.Filled.AccountCircle, label = "Account")
}