package com.journeytix.taskcluster.ui.components.forms

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [35])
class TaskTimeInputTest {

    @get:Rule
    val composeRule = createComposeRule()

    @After
    fun tearDown() {
        stopKoin()
    }

    private fun setContent(initialMode: TimeInputMode, initialNoLimit: Boolean) {
        // State lives outside the composition so recomposition can't reset it.
        var mode by mutableStateOf(initialMode)
        var noLimit by mutableStateOf(initialNoLimit)
        composeRule.setContent {
            TaskTimeInput(
                mode = mode,
                noLimit = noLimit,
                date = "",
                time = "",
                days = "",
                hours = "",
                onModeChange = { mode = it },
                onNoLimitChange = { noLimit = it },
                onDateChange = {},
                onTimeChange = {},
                onDaysChange = {},
                onHoursChange = {},
            )
        }
    }

    @Test
    fun `no limit hides deadline fields`() {
        setContent(TimeInputMode.DateTime, initialNoLimit = true)
        composeRule.onNodeWithTag("date-field").assertDoesNotExist()
        composeRule.onNodeWithTag("time-field").assertDoesNotExist()
        composeRule.onNodeWithText("Date & time").assertDoesNotExist()
    }

    @Test
    fun `date mode shows date and time fields`() {
        setContent(TimeInputMode.DateTime, initialNoLimit = false)
        composeRule.onNodeWithTag("date-field").assertIsDisplayed()
        composeRule.onNodeWithTag("time-field").assertIsDisplayed()
        composeRule.onNodeWithTag("days-field").assertDoesNotExist()
    }

    @Test
    fun `switching to days mode swaps the fields`() {
        setContent(TimeInputMode.DateTime, initialNoLimit = false)
        composeRule.onNodeWithText("Days & hours").performClick()
        composeRule.onNodeWithTag("days-field").assertIsDisplayed()
        composeRule.onNodeWithTag("hours-field").assertIsDisplayed()
        composeRule.onNodeWithTag("date-field").assertDoesNotExist()
    }
}
