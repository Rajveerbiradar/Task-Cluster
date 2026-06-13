package com.journeytix.taskcluster.ui.components.core

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import com.journeytix.taskcluster.ui.theme.DurFast
import com.journeytix.taskcluster.ui.theme.GeneralSans
import com.journeytix.taskcluster.ui.theme.Hairline
import com.journeytix.taskcluster.ui.theme.HairlineStrong
import com.journeytix.taskcluster.ui.theme.Ink700
import com.journeytix.taskcluster.ui.theme.Ink900
import com.journeytix.taskcluster.ui.theme.Primary
import com.journeytix.taskcluster.ui.theme.PrimaryOn
import com.journeytix.taskcluster.ui.theme.PrimaryPress
import com.journeytix.taskcluster.ui.theme.RadiusPill
import com.journeytix.taskcluster.ui.theme.Space2
import com.journeytix.taskcluster.ui.theme.Surface
import com.journeytix.taskcluster.ui.theme.SurfaceSunken
import com.journeytix.taskcluster.ui.theme.TaskClusterTheme
import com.journeytix.taskcluster.ui.theme.TrackSnug
import androidx.compose.ui.text.font.FontWeight

enum class TaskButtonVariant { Primary, Secondary, Ghost }
enum class TaskButtonSize { Lg, Md, Sm }

private const val DISABLED_ALPHA = 0.45f
private const val PRESS_SCALE = 0.98f

@Composable
fun TaskButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: TaskButtonVariant = TaskButtonVariant.Secondary,
    size: TaskButtonSize = TaskButtonSize.Lg,
    enabled: Boolean = true,
    iconLeft: ImageVector? = null,
) {
    val height = when (size) {
        TaskButtonSize.Lg -> 48.dp
        TaskButtonSize.Md -> 40.dp
        TaskButtonSize.Sm -> 32.dp
    }
    val padH = when (size) {
        TaskButtonSize.Lg -> 20.dp
        TaskButtonSize.Md -> 16.dp
        TaskButtonSize.Sm -> 12.dp
    }
    val fontSize = when (size) {
        TaskButtonSize.Lg -> 16.sp
        TaskButtonSize.Md -> 15.sp
        TaskButtonSize.Sm -> 14.sp
    }

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val restBg = when (variant) {
        TaskButtonVariant.Primary -> Primary
        TaskButtonVariant.Secondary -> Surface
        TaskButtonVariant.Ghost -> Color.Transparent
    }
    val pressBg = when (variant) {
        TaskButtonVariant.Primary -> PrimaryPress
        TaskButtonVariant.Secondary -> SurfaceSunken
        TaskButtonVariant.Ghost -> Hairline // ~8% ink wash
    }
    val contentColor = when (variant) {
        TaskButtonVariant.Primary -> PrimaryOn
        TaskButtonVariant.Secondary -> Ink900
        TaskButtonVariant.Ghost -> Ink700
    }

    val bg by animateColorAsState(
        targetValue = if (isPressed) pressBg else restBg,
        animationSpec = tween(DurFast),
        label = "bg",
    )
    val scale by animateFloatAsState(
        targetValue = if (isPressed) PRESS_SCALE else 1f,
        animationSpec = tween(DurFast),
        label = "scale",
    )

    val shape = RoundedCornerShape(RadiusPill)
    Row(
        modifier = modifier
            .scale(scale)
            .alpha(if (enabled) 1f else DISABLED_ALPHA)
            .height(height)
            .clip(shape)
            .background(bg)
            .then(
                if (variant == TaskButtonVariant.Secondary) {
                    Modifier.border(1.dp, HairlineStrong, shape)
                } else {
                    Modifier
                }
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                onClick = onClick,
            )
            .padding(horizontal = padH),
        horizontalArrangement = Arrangement.spacedBy(Space2),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (iconLeft != null) {
            TaskIcon(icon = iconLeft, contentDescription = null, size = 20.dp, tint = contentColor)
        }
        Text(
            text = text,
            style = TextStyle(
                fontFamily = GeneralSans,
                fontWeight = FontWeight.W500,
                fontSize = fontSize,
                letterSpacing = TrackSnug,
            ),
            color = contentColor,
            maxLines = 1,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskButtonPreview() {
    TaskClusterTheme {
        Row(horizontalArrangement = Arrangement.spacedBy(Space2)) {
            TaskButton(text = "add", onClick = {}, variant = TaskButtonVariant.Primary)
            TaskButton(text = "cancel", onClick = {}, variant = TaskButtonVariant.Secondary)
            TaskButton(text = "undo", onClick = {}, variant = TaskButtonVariant.Ghost)
        }
    }
}
