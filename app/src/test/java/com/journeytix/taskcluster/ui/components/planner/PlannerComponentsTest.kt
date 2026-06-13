package com.journeytix.taskcluster.ui.components.planner

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [35])
class PlannerComponentsTest {

    @get:Rule
    val composeRule = createComposeRule()

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `calm time pill renders nothing`() {
        composeRule.setContent {
            TimePill(status = TimePillStatus.Calm, label = "4h 10m")
        }
        composeRule.onNodeWithText("4h 10m").assertDoesNotExist()
    }

    @Test
    fun `non-calm time pill states render their labels`() {
        composeRule.setContent {
            TimePill(status = TimePillStatus.OnTrack, label = "4h 10m")
            TimePill(status = TimePillStatus.Close, label = "1h 30m")
            TimePill(status = TimePillStatus.Due, label = "12m")
            TimePill(status = TimePillStatus.Overdue, label = "−2h 14m")
        }
        composeRule.onNodeWithText("4h 10m").assertIsDisplayed()
        composeRule.onNodeWithText("1h 30m").assertIsDisplayed()
        composeRule.onNodeWithText("12m").assertIsDisplayed()
        composeRule.onNodeWithText("−2h 14m").assertIsDisplayed()
    }

    @Test
    fun `completed task row drops its time pill and stays in place`() {
        var checked by mutableStateOf(false)
        composeRule.setContent {
            TaskRow(
                title = "finish the quarterly report",
                checked = checked,
                onToggle = { checked = it },
                status = TimePillStatus.Due,
                time = "12m",
            )
        }
        composeRule.onNodeWithTag("time-pill", useUnmergedTree = true).assertIsDisplayed()

        checked = true
        composeRule.waitForIdle()

        // Pill dropped, row still present (greyed in place — not removed).
        composeRule.onNodeWithTag("time-pill", useUnmergedTree = true).assertDoesNotExist()
        composeRule.onNodeWithText("finish the quarterly report").assertIsDisplayed()
    }
}
