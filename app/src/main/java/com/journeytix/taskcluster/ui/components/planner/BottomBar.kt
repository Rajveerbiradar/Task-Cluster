package com.journeytix.taskcluster.ui.components.planner

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.journeytix.taskcluster.ui.components.core.TaskIcon
import com.journeytix.taskcluster.ui.components.core.TaskIcons
import com.journeytix.taskcluster.ui.theme.DurFast
import com.journeytix.taskcluster.ui.theme.Hairline
import com.journeytix.taskcluster.ui.theme.Ink500
import com.journeytix.taskcluster.ui.theme.Primary
import com.journeytix.taskcluster.ui.theme.PrimaryOn
import com.journeytix.taskcluster.ui.theme.RadiusPill
import com.journeytix.taskcluster.ui.theme.SurfaceRaised
import com.journeytix.taskcluster.ui.theme.TaskClusterTheme

enum class BottomBarMode(val icon: () -> ImageVector, val label: String) {
    Home({ TaskIcons.Home }, "Home"),
    Tasks({ TaskIcons.List }, "Tasks"),
    Search({ TaskIcons.Search }, "Search"),
    Add({ TaskIcons.Plus }, "Add"),
}

/* BottomBar — the floating pill. Active mode fills ink. */
@Composable
fun BottomBar(
    mode: BottomBarMode,
    onModeChange: (BottomBarMode) -> Unit,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(RadiusPill)
    Row(
        modifier = modifier
            .shadow(12.dp, shape)
            .clip(shape)
            .background(SurfaceRaised)
            .border(1.dp, Hairline, shape)
            .padding(6.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        BottomBarMode.entries.forEach { m ->
            val active = m == mode
            val bg by animateColorAsState(
                targetValue = if (active) Primary else Color.Transparent,
                animationSpec = tween(DurFast),
                label = "bg",
            )
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(RadiusPill))
                    .background(bg)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                    ) { onModeChange(m) },
                contentAlignment = Alignment.Center,
            ) {
                TaskIcon(
                    icon = m.icon(),
                    contentDescription = m.label,
                    size = 22.dp,
                    tint = if (active) PrimaryOn else Ink500,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BottomBarPreview() {
    TaskClusterTheme {
        BottomBar(mode = BottomBarMode.Home, onModeChange = {})
    }
}
