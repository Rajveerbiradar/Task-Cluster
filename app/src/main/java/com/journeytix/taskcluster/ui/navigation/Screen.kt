package com.journeytix.taskcluster.ui.navigation

import android.net.Uri

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Settings : Screen("settings")
    data object Trash : Screen("trash")
    data object About : Screen("about")
    data object Legal : Screen("legal/{title}/{url}") {
        const val ARG_TITLE = "title"
        const val ARG_URL = "url"
        fun routeFor(title: String, url: String) =
            "legal/${Uri.encode(title)}/${Uri.encode(url)}"
    }
}
