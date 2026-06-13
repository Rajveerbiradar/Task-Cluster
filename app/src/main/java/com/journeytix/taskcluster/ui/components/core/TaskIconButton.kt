package com.journeytix.taskcluster.ui.components.core

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.journeytix.taskcluster.ui.theme.Blue
import com.journeytix.taskcluster.ui.theme.DurFast
import com.journeytix.taskcluster.ui.theme.Ink700
import com.journeytix.taskcluster.ui.theme.RadiusPill
import com.journeytix.taskcluster.ui.theme.SurfaceSunken
import com.journeytix.taskcluster.ui.theme.TaskClusterTheme

private const val DISABLED_ALPHA = 0.4f
private const val PRESS_SCALE = 0.98f

@Composable
fun TaskIconButton(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    active: Boolean = false,
    size: Dp = 44.dp,
    enabled: Boolean = true,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val bg by animateColorAsState(
        targetValue = if (isPressed) SurfaceSunken else Color.Transparent,
        animationSpec = tween(DurFast),
        label = "bg",
    )
    val scale by animateFloatAsState(
        targetValue = if (isPressed) PRESS_SCALE else 1f,
        animationSpec = tween(DurFast),
        label = "scale",
    )

    Box(
        modifier = modifier
            .scale(scale)
            .alpha(if (enabled) 1f else DISABLED_ALPHA)
            .size(size)
            .clip(RoundedCornerShape(RadiusPill))
            .background(bg)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        TaskIcon(
            icon = icon,
            contentDescription = label,
            tint = if (active) Blue else Ink700,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskIconButtonPreview() {
    TaskClusterTheme {
        Row {
            TaskIconButton(icon = TaskIcons.Search, label = "search", onClick = {})
            TaskIconButton(icon = TaskIcons.Plus, label = "add", onClick = {}, active = true)
            TaskIconButton(icon = TaskIcons.MoreVertical, label = "more", onClick = {}, enabled = false)
        }
    }
}
