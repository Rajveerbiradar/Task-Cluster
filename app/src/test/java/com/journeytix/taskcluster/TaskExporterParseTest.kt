package com.journeytix.taskcluster

import com.journeytix.taskcluster.data.export.TaskExporter
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

// Plain Application avoids starting the app's Koin graph (keeps this a pure parse test).
@RunWith(RobolectricTestRunner::class)
@Config(application = android.app.Application::class, sdk = [35])
class TaskExporterParseTest {

    @Test
    fun `parses parents sections and task fields`() {
        val json = """
            {
              "formatVersion": 1,
              "parents": [
                {
                  "title": "Work",
                  "emoji": "💼",
                  "favourite": true,
                  "sections": [
                    {
                      "title": "Launch",
                      "icon": "rocket-01",
                      "tasks": [
                        { "title": "Write notes", "priority": "high", "completed": true },
                        { "title": "Ship" }
                      ]
                    }
                  ]
                }
              ],
              "standaloneSections": [
                { "title": "Errands", "tasks": [ { "title": "Milk" } ] }
              ]
            }
        """.trimIndent()

        val data = TaskExporter.parse(json)

        assertEquals(1, data.parents.size)
        val work = data.parents[0]
        assertEquals("Work", work.title)
        assertTrue(work.favourite)
        assertEquals(1, work.sections.size)
        val launch = work.sections[0]
        assertEquals("rocket-01", launch.icon)
        assertEquals(2, launch.tasks.size)
        assertEquals("high", launch.tasks[0].priority)
        assertTrue(launch.tasks[0].completed)
        assertNull(launch.tasks[1].priority)
        assertEquals(1, data.standaloneSections.size)
        assertEquals("Errands", data.standaloneSections[0].title)
    }

    @Test
    fun `tasks with blank titles are skipped`() {
        val json = """
            {
              "standaloneSections": [
                { "title": "S", "tasks": [ { "title": "" }, { "title": "ok" } ] }
              ]
            }
        """.trimIndent()
        val data = TaskExporter.parse(json)
        assertEquals(1, data.standaloneSections[0].tasks.size)
        assertEquals("ok", data.standaloneSections[0].tasks[0].title)
    }
}
