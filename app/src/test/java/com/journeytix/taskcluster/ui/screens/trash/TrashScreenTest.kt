package com.journeytix.taskcluster.ui.screens.trash

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.journeytix.taskcluster.data.db.AppDatabase
import com.journeytix.taskcluster.data.model.Task
import com.journeytix.taskcluster.data.preferences.UserPreferences
import com.journeytix.taskcluster.data.repository.TaskRepository
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.runBlocking
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
class TrashScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    private lateinit var db: AppDatabase
    private lateinit var repository: TaskRepository
    private lateinit var viewModel: TrashViewModel
    private val now = 1_750_000_000_000L

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        repository = TaskRepository(db.taskDao(), db.sectionDao(), db.parentDao())
        viewModel = TrashViewModel(repository, UserPreferences(context), nowMs = { now })
    }

    @After
    fun tearDown() {
        db.close()
        stopKoin()
    }

    private fun trashedTask(title: String, daysAgo: Int) = Task(
        sectionId = 1,
        title = title,
        isTrashed = true,
        trashedAt = now - TimeUnit.DAYS.toMillis(daysAgo.toLong()),
    )

    private fun setContent() {
        composeRule.setContent {
            TrashScreen(onBack = {}, viewModel = viewModel)
        }
        composeRule.waitForIdle()
    }

    // Room emits flow updates on a background executor that waitForIdle
    // doesn't track — poll until the expected text lands in the tree.
    private fun awaitText(text: String) {
        composeRule.waitUntil(timeoutMillis = 5_000) {
            composeRule.onAllNodesWithText(text).fetchSemanticsNodes().isNotEmpty()
        }
    }

    @Test
    fun `empty trash shows the empty state`() {
        setContent()
        composeRule.onNodeWithText("Trash is empty").assertIsDisplayed()
    }

    @Test
    fun `banner shows the auto-delete period and items show days remaining`() {
        runBlocking { repository.addTask(trashedTask("Old grocery list", daysAgo = 3)) }
        setContent()
        composeRule
            .onNodeWithText("Items are permanently deleted after 30 days.")
            .assertIsDisplayed()
        composeRule.onNodeWithText("Old grocery list").assertIsDisplayed()
        // 30-day default TTL − 3 days elapsed = 27d left
        composeRule.onNodeWithText("27d left", substring = true).assertIsDisplayed()
    }

    @Test
    fun `restore puts the task back without confirmation`() {
        runBlocking { repository.addTask(trashedTask("Email Sarah back", daysAgo = 1)) }
        setContent()
        composeRule.onNodeWithText("Restore").performClick()
        awaitText("Trash is empty")
        composeRule.onNodeWithText("Trash is empty").assertIsDisplayed()
    }

    @Test
    fun `delete permanently requires confirmation and cancel keeps the item`() {
        runBlocking { repository.addTask(trashedTask("Renew gym pass", daysAgo = 2)) }
        setContent()
        composeRule.onNodeWithText("Delete").performClick()
        composeRule
            .onNodeWithText("This task will be permanently deleted. This action cannot be undone.")
            .assertIsDisplayed()
        composeRule.onNodeWithText("Cancel").performClick()
        composeRule.onNodeWithText("Renew gym pass").assertIsDisplayed()
    }

    @Test
    fun `empty trash confirmation deletes everything`() {
        runBlocking {
            repository.addTask(trashedTask("one", daysAgo = 1))
            repository.addTask(trashedTask("two", daysAgo = 2))
        }
        setContent()
        composeRule.onAllNodesWithText("Empty trash").onFirst().performClick()
        composeRule
            .onNodeWithText("All 2 items in trash will be permanently deleted. This cannot be undone.")
            .assertIsDisplayed()
        // The dialog's confirm button is also labelled "Empty trash" — last node
        composeRule.onAllNodesWithText("Empty trash")[1].performClick()
        awaitText("Trash is empty")
        composeRule.onNodeWithText("Trash is empty").assertIsDisplayed()
    }
}
