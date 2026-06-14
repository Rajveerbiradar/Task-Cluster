package com.journeytix.taskcluster

import com.journeytix.taskcluster.data.model.Task
import com.journeytix.taskcluster.ui.components.planner.TimePillStatus
import com.journeytix.taskcluster.ui.screens.home.aggregatePill
import com.journeytix.taskcluster.ui.screens.home.formatDuration
import com.journeytix.taskcluster.ui.screens.home.timePillFor
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class TimePillTest {

    @Test
    fun `formatDuration renders days hours and minutes`() {
        assertEquals("30m", formatDuration(30 * 60_000L))
        assertEquals("1h 30m", formatDuration(90 * 60_000L))
        assertEquals("1d 1h", formatDuration(25 * 60 * 60_000L))
    }

    @Test
    fun `task with no deadline is calm with no label`() {
        val task = Task(sectionId = 1, title = "x", createdAt = 0)
        val (status, label) = timePillFor(task, nowMs = 1000)
        assertEquals(TimePillStatus.Calm, status)
        assertNull(label)
    }

    @Test
    fun `overdue task counts up with a minus sign`() {
        val task = Task(sectionId = 1, title = "x", createdAt = 0, dueTime = 1000)
        val (status, label) = timePillFor(task, nowMs = 1000 + 90 * 60_000L)
        assertEquals(TimePillStatus.Overdue, status)
        assertTrue(label!!.startsWith("−")) // − minus
    }

    @Test
    fun `deadline far out still shows remaining time`() {
        // 100% of the window remains -> previously hidden, now shown as on-track.
        val task = Task(sectionId = 1, title = "x", createdAt = 0, dueTime = 100_000)
        val (status, label) = timePillFor(task, nowMs = 0)
        assertEquals(TimePillStatus.OnTrack, status)
        assertEquals(formatDuration(100_000), label)
    }

    @Test
    fun `aggregate pill surfaces the soonest deadline among tasks`() {
        val soon = Task(sectionId = 1, title = "soon", createdAt = 0, dueTime = 10)
        val later = Task(sectionId = 1, title = "later", createdAt = 0, dueTime = 1_000_000)
        val done = Task(sectionId = 1, title = "done", createdAt = 0, dueTime = 5, isCompleted = true)
        val (_, label) = aggregatePill(listOf(later, soon, done), nowMs = 0)
        // Completed task (due 5) is ignored; soonest active is "soon" (due 10).
        assertEquals(formatDuration(10), label)
    }
}
