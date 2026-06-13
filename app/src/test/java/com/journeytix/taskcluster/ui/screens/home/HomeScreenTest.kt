package com.journeytix.taskcluster.ui.screens.home

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.journeytix.taskcluster.data.db.AppDatabase
import com.journeytix.taskcluster.data.repository.TaskRepository
import java.time.LocalDate
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [35])
class HomeScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    private lateinit var db: AppDatabase
    private lateinit var viewModel: HomeViewModel
    private val today = LocalDate.of(2026, 6, 13)

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        viewModel = HomeViewModel(
            TaskRepository(db.taskDao(), db.sectionDao(), db.parentDao()),
            today,
        )
    }

    @After
    fun tearDown() {
        db.close()
        stopKoin()
    }

    private fun setContent() {
        composeRule.setContent {
            HomeScreen(
                onOpenSettings = {},
                onOpenTrash = {},
                onOpenAbout = {},
                viewModel = viewModel,
            )
        }
        composeRule.waitForIdle()
    }

    @Test
    fun `three-dot opens context menu with settings trash about`() {
        setContent()
        composeRule.onNodeWithContentDescription("Open menu").performClick()
        composeRule.onNodeWithText("Settings").assertIsDisplayed()
        composeRule.onNodeWithText("Trash").assertIsDisplayed()
        composeRule.onNodeWithText("About").assertIsDisplayed()
    }

    @Test
    fun `past date shows the read-only banner`() {
        setContent()
        viewModel.onIntent(HomeIntent.SelectDate(today.minusDays(1)))
        composeRule.waitForIdle()
        composeRule.onNodeWithText("viewing — read only").assertIsDisplayed()
    }

    @Test
    fun `future date shows the planning banner`() {
        setContent()
        viewModel.onIntent(HomeIntent.SelectDate(today.plusDays(1)))
        composeRule.waitForIdle()
        composeRule.onNodeWithText("planning — hidden until then").assertIsDisplayed()
        composeRule.onNodeWithText("Nothing planned for this day yet.").assertIsDisplayed()
    }

    @Test
    fun `daily parent is pinned on home`() {
        setContent()
        composeRule.onNodeWithText("daily").assertIsDisplayed()
    }
}
