package com.journeytix.taskcluster.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.journeytix.taskcluster.ui.screens.about.AboutScreen
import com.journeytix.taskcluster.ui.screens.home.HomeScreen
import com.journeytix.taskcluster.ui.screens.legal.LegalWebViewScreen
import com.journeytix.taskcluster.ui.screens.settings.SettingsScreen
import com.journeytix.taskcluster.ui.screens.trash.TrashScreen
import com.journeytix.taskcluster.ui.theme.DurBase

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        enterTransition = { slideInHorizontally(tween(DurBase)) { it } },
        exitTransition = { slideOutHorizontally(tween(DurBase)) { -it } },
        popEnterTransition = { slideInHorizontally(tween(DurBase)) { -it } },
        popExitTransition = { slideOutHorizontally(tween(DurBase)) { it } },
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onOpenSettings = { navController.navigate(Screen.Settings.route) },
                onOpenTrash = { navController.navigate(Screen.Trash.route) },
                onOpenAbout = { navController.navigate(Screen.About.route) },
            )
        }
        composable(Screen.Settings.route) {
            SettingsScreen(
                onBack = { navController.popBackStack() },
                onOpenLegal = { title, url ->
                    navController.navigate(Screen.Legal.routeFor(title, url))
                },
            )
        }
        composable(Screen.Trash.route) {
            TrashScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.About.route) {
            AboutScreen(
                onBack = { navController.popBackStack() },
                onOpenLegal = { title, url ->
                    navController.navigate(Screen.Legal.routeFor(title, url))
                },
            )
        }
        composable(
            route = Screen.Legal.route,
            arguments = listOf(
                navArgument(Screen.Legal.ARG_TITLE) { type = NavType.StringType },
                navArgument(Screen.Legal.ARG_URL) { type = NavType.StringType },
            ),
        ) { entry ->
            LegalWebViewScreen(
                title = entry.arguments?.getString(Screen.Legal.ARG_TITLE).orEmpty(),
                url = entry.arguments?.getString(Screen.Legal.ARG_URL).orEmpty(),
                onBack = { navController.popBackStack() },
            )
        }
    }
}
