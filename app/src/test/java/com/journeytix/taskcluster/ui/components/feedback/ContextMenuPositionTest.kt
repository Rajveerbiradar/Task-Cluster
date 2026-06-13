package com.journeytix.taskcluster.ui.components.feedback

import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import org.junit.Assert.assertTrue
import org.junit.Test

class ContextMenuPositionTest {

    private val window = IntSize(1080, 2280)
    private val menu = IntSize(620, 480)

    private fun assertWithinWindow(anchor: IntOffset) {
        val pos = contextMenuPosition(anchor, menu, window)
        assertTrue("left edge clipped at $anchor -> $pos", pos.x >= 0)
        assertTrue("top edge clipped at $anchor -> $pos", pos.y >= 0)
        assertTrue("right edge clipped at $anchor -> $pos", pos.x + menu.width <= window.width)
        assertTrue("bottom edge clipped at $anchor -> $pos", pos.y + menu.height <= window.height)
    }

    @Test
    fun `menu is never clipped in any corner or edge`() {
        val anchors = listOf(
            IntOffset(0, 0), // top-left corner
            IntOffset(window.width, 0), // top-right corner
            IntOffset(0, window.height), // bottom-left corner
            IntOffset(window.width, window.height), // bottom-right corner
            IntOffset(window.width / 2, window.height / 2), // centre
            IntOffset(window.width - 1, window.height / 2), // right edge
            IntOffset(1, window.height - 1), // bottom edge
        )
        anchors.forEach(::assertWithinWindow)
    }

    @Test
    fun `menu opens right of anchor on the left half`() {
        val anchor = IntOffset(100, 500)
        val pos = contextMenuPosition(anchor, menu, window)
        assertTrue("expected menu right of anchor", pos.x > anchor.x)
    }

    @Test
    fun `menu flips left of anchor on the right half`() {
        val anchor = IntOffset(1000, 500)
        val pos = contextMenuPosition(anchor, menu, window)
        assertTrue("expected menu left of anchor", pos.x + menu.width < anchor.x)
    }

    @Test
    fun `menu flips above anchor near the bottom`() {
        val anchor = IntOffset(540, window.height - 50)
        val pos = contextMenuPosition(anchor, menu, window)
        assertTrue("expected menu above anchor", pos.y + menu.height < anchor.y)
    }
}
