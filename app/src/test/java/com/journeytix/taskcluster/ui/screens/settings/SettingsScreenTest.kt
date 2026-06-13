package com.journeytix.taskcluster.ui.screens.settings

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.journeytix.taskcluster.data.db.AppDatabase
import com.journeytix.taskcluster.data.preferences.UserPreferences
import com.journeytix.taskcluster.data.repository.TaskRepository
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [35])
class SettingsScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    private lateinit var db: AppDatabase
    private lateinit var viewModel: SettingsViewModel

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        viewModel = SettingsViewModel(
            UserPreferences(context),
            TaskRepository(db.taskDao(), db.sectionDao(), db.parentDao()),
        )
    }

    @After
    fun tearDown() {
        db.close()
        stopKoin()
    }

    private fun setContent(
        onOpenLegal: (String, String) -> Unit = { _, _ -> },
    ) {
        composeRule.setContent {
            SettingsScreen(onBack = {}, onOpenLegal = onOpenLegal, viewModel = viewModel)
        }
        composeRule.waitForIdle()
    }

    @Test
    fun `all groups from the spec are present`() {
        setContent()
        listOf(
            "Appearance", "Tasks", "Notifications", "Trash", "Data", "Legal", "Danger zone",
        ).forEach { composeRule.onNodeWithText(it).performScrollTo().assertIsDisplayed() }
    }

    @Test
    fun `legal rows navigate with title and url`() {
        var navTitle = ""
        var navUrl = ""
        setContent(onOpenLegal = { t, u -> navTitle = t; navUrl = u })
        composeRule.onNodeWithText("Privacy Policy").performScrollTo().performClick()
        assertEquals("Privacy Policy", navTitle)
        assertEquals("https://journeytix.com/legal/privacy", navUrl)
    }

    @Test
    fun `reset settings asks for confirmation first`() {
        setContent()
        composeRule.onNodeWithText("Reset to default settings").performScrollTo().performClick()
        composeRule
            .onNodeWithText("This will reset all settings to their defaults. Your tasks will not be affected.")
            .assertIsDisplayed()
    }

    @Test
    fun `canceling the reset dialog closes it without executing`() {
        setContent()
        composeRule.onNodeWithText("Reset to default settings").performScrollTo().performClick()
        composeRule.onNodeWithText("Cancel").performClick()
        composeRule
            .onNodeWithText("This will reset all settings to their defaults. Your tasks will not be affected.")
            .assertDoesNotExist()
    }
}
