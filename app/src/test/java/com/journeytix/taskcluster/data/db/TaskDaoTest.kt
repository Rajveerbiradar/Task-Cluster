package com.journeytix.taskcluster.data.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.journeytix.taskcluster.data.model.Priority
import com.journeytix.taskcluster.data.model.Task
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [35])
class TaskDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var taskDao: TaskDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        taskDao = db.taskDao()
    }

    @After
    fun tearDown() {
        db.close()
        // The real TaskClusterApp starts Koin on each Robolectric app boot;
        // stop it so the next test's app instance can start cleanly.
        stopKoin()
    }

    @Test
    fun `insert task and read it back returns identical fields`() = runTest {
        // Arrange
        val task = Task(
            sectionId = 7,
            title = "buy stamps",
            description = "the square ones",
            isCompleted = false,
            priority = Priority.HIGH,
            dueDate = 1_780_000_000_000,
            dueTime = 1_780_000_360_000,
            createdAt = 1_779_999_000_000,
            completedAt = null,
            isTrashed = false,
            trashedAt = null,
        )

        // Act
        val id = taskDao.insert(task)
        val loaded = taskDao.getById(id)

        // Assert
        assertEquals(task.copy(id = id), loaded)
    }
}
