package com.journeytix.taskcluster.ui.screens.home

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.journeytix.taskcluster.data.db.AppDatabase
import com.journeytix.taskcluster.data.model.Section
import com.journeytix.taskcluster.data.model.Task
import com.journeytix.taskcluster.data.repository.TaskRepository
import com.journeytix.taskcluster.ui.components.planner.TimePillStatus
import java.time.LocalDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [35])
class HomeViewModelTest {

    private lateinit var db: AppDatabase
    private lateinit var repository: TaskRepository
    private val today = LocalDate.of(2026, 6, 13)

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        repository = TaskRepository(db.taskDao(), db.sectionDao(), db.parentDao())
    }

    @After
    fun tearDown() {
        db.close()
        Dispatchers.resetMain()
        stopKoin()
    }

    private suspend fun awaitState(
        viewModel: HomeViewModel,
        predicate: (HomeUiState) -> Boolean,
    ): HomeUiState = viewModel.state.first(predicate)

    @Test
    fun `completing a task never reorders the list`() = runTest {
        val sectionId = repository.addSection(Section(title = "errands"))
        repository.addTask(Task(sectionId = sectionId, title = "first", createdAt = 1_000))
        repository.addTask(Task(sectionId = sectionId, title = "second", createdAt = 2_000))
        repository.addTask(Task(sectionId = sectionId, title = "third", createdAt = 3_000))

        val viewModel = HomeViewModel(repository, today)
        var state = awaitState(viewModel) { it.standalone.isNotEmpty() }
        val tasks = state.standalone.first().tasks
        // Newest on top
        assertEquals(listOf("third", "second", "first"), tasks.map { it.title })

        // Complete the middle task
        viewModel.onIntent(HomeIntent.ToggleTask(tasks[1], true))
        state = awaitState(viewModel) {
            it.standalone.firstOrNull()?.tasks?.any { t -> t.isCompleted } == true
        }
        val after = state.standalone.first().tasks
        // Greyed in place — same order, completion flag set, nothing moved
        assertEquals(listOf("third", "second", "first"), after.map { it.title })
        assertTrue(after[1].isCompleted)
    }

    @Test
    fun `past dates are read-only and block task toggling`() = runTest {
        val sectionId = repository.addSection(Section(title = "errands"))
        repository.addTask(Task(sectionId = sectionId, title = "task", createdAt = 1_000))

        val viewModel = HomeViewModel(repository, today)
        var state = awaitState(viewModel) { it.standalone.isNotEmpty() }

        viewModel.onIntent(HomeIntent.SelectDate(today.minusDays(1)))
        state = awaitState(viewModel) { it.isReadOnly }
        assertTrue(state.isReadOnly)
        assertFalse(state.isPlanning)

        // Toggle is ignored while read-only
        viewModel.onIntent(HomeIntent.ToggleTask(state.standalone.first().tasks.first(), true))
        assertFalse(
            awaitState(viewModel) { it.standalone.isNotEmpty() }
                .standalone.first().tasks.first().isCompleted
        )
    }

    @Test
    fun `future dates are planning mode`() = runTest {
        val viewModel = HomeViewModel(repository, today)
        viewModel.onIntent(HomeIntent.SelectDate(today.plusDays(2)))
        val state = awaitState(viewModel) { it.isPlanning }
        assertTrue(state.isPlanning)
        assertFalse(state.isReadOnly)
    }

    @Test
    fun `daily sections are separated from parented and standalone ones`() = runTest {
        repository.addSection(Section(title = "morning", isDaily = true))
        repository.addSection(Section(title = "side project"))

        val viewModel = HomeViewModel(repository, today)
        val state = awaitState(viewModel) { it.daily.isNotEmpty() && it.standalone.isNotEmpty() }
        assertEquals("morning", state.daily.first().section.title)
        assertEquals("side project", state.standalone.first().section.title)
    }

    @Test
    fun `time pill derivation covers all five states`() {
        val created = 0L
        val due = 100_000L
        fun taskDue(now: Long) =
            timePillFor(Task(sectionId = 1, title = "t", createdAt = created, dueDate = due), now)

        assertEquals(TimePillStatus.Calm, taskDue(10_000).first) // 90% left
        assertEquals(TimePillStatus.OnTrack, taskDue(60_000).first) // 40% left
        assertEquals(TimePillStatus.Close, taskDue(80_000).first) // 20% left
        assertEquals(TimePillStatus.Due, taskDue(95_000).first) // 5% left
        val (status, label) = taskDue(160_000) // past due
        assertEquals(TimePillStatus.Overdue, status)
        assertEquals("−1m", label)

        // No deadline → calm, no label
        val none = timePillFor(Task(sectionId = 1, title = "t"), 0)
        assertEquals(TimePillStatus.Calm, none.first)
        assertNull(none.second)
    }

    @Test
    fun `duration formatting follows the numbers-as-facts voice`() {
        assertEquals("2d 4h", formatDuration((52 * 60 + 14) * 60_000L))
        assertEquals("2h 14m", formatDuration((2 * 60 + 14) * 60_000L))
        assertEquals("45m", formatDuration(45 * 60_000L))
    }
}
