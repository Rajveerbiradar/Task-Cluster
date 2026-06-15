package com.journeytix.taskcluster.baselineprofile

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/* Generates a baseline profile by exercising the two hot screens: the Home
   planner and the Tasks list. Run with:  ./gradlew :app:generateBaselineProfile
   The resulting app/src/main/baseline-prof.txt ships in the APK and is applied
   at install time for faster startup and smoother first scrolls. */
@RunWith(AndroidJUnit4::class)
class BaselineProfileGenerator {

    @get:Rule
    val rule = BaselineProfileRule()

    @Test
    fun generate() = rule.collect(packageName = "com.journeytix.taskcluster") {
        pressHome()
        startActivityAndWait()
        device.waitForIdle()

        // Home screen — render + scroll the planner.
        scrollList()

        // Tasks list screen — navigate via the bottom bar, then scroll.
        runCatching {
            (device.findObject(By.desc("Tasks")) ?: device.findObject(By.text("Tasks")))?.click()
            device.waitForIdle()
            scrollList()
        }
    }

    private fun MacrobenchmarkScope.scrollList() {
        val list = device.findObject(By.scrollable(true)) ?: return
        list.setGestureMargin(device.displayWidth / 5)
        repeat(2) {
            list.fling(Direction.DOWN)
            device.waitForIdle()
        }
        list.fling(Direction.UP)
        device.waitForIdle()
    }
}
