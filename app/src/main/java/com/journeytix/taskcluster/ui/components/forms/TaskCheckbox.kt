package com.journeytix.taskcluster.ui.components.forms

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.journeytix.taskcluster.ui.components.core.TaskIcon
import com.journeytix.taskcluster.ui.components.core.lucide
import com.journeytix.taskcluster.ui.theme.DurFast
import com.journeytix.taskcluster.ui.theme.Ink300
import com.journeytix.taskcluster.ui.theme.Primary
import com.journeytix.taskcluster.ui.theme.PrimaryOn
import com.journeytix.taskcluster.ui.theme.RadiusXs
import com.journeytix.taskcluster.ui.theme.SpringDamping
import com.journeytix.taskcluster.ui.theme.SpringStiffness
import com.journeytix.taskcluster.ui.theme.TaskClusterTheme

// Check drawn heavier inside the box (JSX stroke 2.5).
private val CheckboxCheck = lucide("checkbox-check", "M20 6 9 17l-5-5", stroke = 2.5f)

private const val DISABLED_ALPHA = 0.5f

@Composable
fun TaskCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    size: Dp = 22.dp,
    enabled: Boolean = true,
) {
    val bg by animateColorAsState(
        targetValue = if (checked) Primary else Color.Transparent,
        animationSpec = tween(DurFast),
        label = "bg",
    )
    val checkScale by animateFloatAsState(
        targetValue = if (checked) 1f else 0f,
        animationSpec = spring(dampingRatio = SpringDamping, stiffness = SpringStiffness),
        label = "check",
    )
    val shape = RoundedCornerShape(RadiusXs)
    Box(
        modifier = modifier
            .alpha(if (enabled) 1f else DISABLED_ALPHA)
            .size(size)
            .clip(shape)
            .background(bg)
            .border(
                width = if (checked) 1.dp else 1.5.dp,
                color = if (checked) Primary else Ink300,
                shape = shape,
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                enabled = enabled,
                role = Role.Checkbox,
            ) { onCheckedChange(!checked) },
        contentAlignment = Alignment.Center,
    ) {
        if (checkScale > 0f) {
            TaskIcon(
                icon = CheckboxCheck,
                contentDescription = null,
                size = size - 7.dp,
                tint = PrimaryOn,
                modifier = Modifier.scale(checkScale),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskCheckboxPreview() {
    TaskClusterTheme {
        Box {
            TaskCheckbox(checked = true, onCheckedChange = {})
        }
    }
}
