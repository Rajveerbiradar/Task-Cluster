package com.journeytix.taskcluster.ui.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [35])
class NavGraphTest {

    @get:Rule
    val composeRule = createComposeRule()

    private lateinit var navController: TestNavHostController

    @After
    fun tearDown() {
        stopKoin()
    }

    private fun setUpNavGraph() {
        composeRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            NavGraph(navController = navController)
        }
        composeRule.waitForIdle()
    }

    @Test
    fun `start destination is home`() {
        setUpNavGraph()
        assertEquals(Screen.Home.route, navController.currentBackStackEntry?.destination?.route)
    }

    @Test
    fun `navigate to settings and back returns to home`() {
        setUpNavGraph()

        composeRule.runOnUiThread { navController.navigate(Screen.Settings.route) }
        composeRule.waitForIdle()
        assertEquals(Screen.Settings.route, navController.currentBackStackEntry?.destination?.route)

        composeRule.runOnUiThread { navController.popBackStack() }
        composeRule.waitForIdle()
        assertEquals(Screen.Home.route, navController.currentBackStackEntry?.destination?.route)
    }

    @Test
    fun `legal route carries title and url args`() {
        setUpNavGraph()

        composeRule.runOnUiThread {
            navController.navigate(Screen.Legal.routeFor("Privacy policy", "https://journeytix.com/legal/privacy"))
        }
        composeRule.waitForIdle()

        val entry = navController.currentBackStackEntry
        assertEquals(Screen.Legal.route, entry?.destination?.route)
        assertEquals("Privacy policy", entry?.arguments?.getString(Screen.Legal.ARG_TITLE))
        assertEquals(
            "https://journeytix.com/legal/privacy",
            entry?.arguments?.getString(Screen.Legal.ARG_URL),
        )
    }
}
