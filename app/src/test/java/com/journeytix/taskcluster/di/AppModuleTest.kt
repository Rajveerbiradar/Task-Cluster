package com.journeytix.taskcluster.di

import com.journeytix.taskcluster.data.db.AppDatabase
import com.journeytix.taskcluster.data.preferences.UserPreferences
import com.journeytix.taskcluster.data.repository.TaskRepository
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.GlobalContext
import org.koin.core.context.stopKoin
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [35])
class AppModuleTest {

    @After
    fun tearDown() {
        stopKoin()
    }

    // Robolectric boots the real TaskClusterApp, which calls startKoin in
    // onCreate — so resolving from GlobalContext verifies actual app startup.
    @Test
    fun `app startup wires the full dependency graph`() {
        val koin = GlobalContext.get()

        assertNotNull(koin.get<AppDatabase>())
        assertNotNull(koin.get<TaskRepository>())
        assertNotNull(koin.get<UserPreferences>())
    }
}
