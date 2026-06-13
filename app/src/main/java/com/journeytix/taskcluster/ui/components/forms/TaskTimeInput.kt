package com.journeytix.taskcluster.ui.components.forms

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.journeytix.taskcluster.ui.components.core.TaskIcon
import com.journeytix.taskcluster.ui.components.core.TaskIcons
import com.journeytix.taskcluster.ui.theme.GeneralSans
import com.journeytix.taskcluster.ui.theme.HairlineStrong
import com.journeytix.taskcluster.ui.theme.Ink400
import com.journeytix.taskcluster.ui.theme.Ink500
import com.journeytix.taskcluster.ui.theme.Ink700
import com.journeytix.taskcluster.ui.theme.Ink900
import com.journeytix.taskcluster.ui.theme.RadiusSm
import com.journeytix.taskcluster.ui.theme.RadiusXs
import com.journeytix.taskcluster.ui.theme.Space2
import com.journeytix.taskcluster.ui.theme.Space3
import com.journeytix.taskcluster.ui.theme.Surface
import com.journeytix.taskcluster.ui.theme.SurfaceRaised
import com.journeytix.taskcluster.ui.theme.SurfaceSunken
import com.journeytix.taskcluster.ui.theme.TaskClusterTheme

enum class TimeInputMode(val label: String) {
    DateTime("Date & time"),
    DaysHours("Days & hours"),
}

private val FieldTextStyle = TextStyle(
    fontFamily = GeneralSans,
    fontWeight = FontWeight.W400,
    fontSize = 16.sp,
    color = Ink900,
)

@Composable
fun TaskTimeInput(
    mode: TimeInputMode,
    noLimit: Boolean,
    date: String,
    time: String,
    days: String,
    hours: String,
    onModeChange: (TimeInputMode) -> Unit,
    onNoLimitChange: (Boolean) -> Unit,
    onDateChange: (String) -> Unit,
    onTimeChange: (String) -> Unit,
    onDaysChange: (String) -> Unit,
    onHoursChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = "Deadline",
) {
    Column(modifier = modifier) {
        if (label != null) {
            Text(
                text = label,
                style = FieldLabelStyle,
                modifier = Modifier.padding(bottom = Space2),
            )
        }

        // No time limit checkbox row
        Row(
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                ) { onNoLimitChange(!noLimit) }
                .padding(horizontal = 4.dp, vertical = Space2)
                .testTag("no-limit-row"),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TaskCheckbox(
                checked = noLimit,
                onCheckedChange = onNoLimitChange,
                size = 20.dp,
            )
            Text(
                text = "No time limit",
                style = TextStyle(
                    fontFamily = GeneralSans,
                    fontWeight = FontWeight.W400,
                    fontSize = 15.sp,
                ),
                color = Ink700,
            )
        }

        if (!noLimit) {
            // Segmented control — two options, equal widths
            Row(
                modifier = Modifier
                    .padding(top = Space3, bottom = Space3)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(RadiusSm))
                    .background(SurfaceSunken)
                    .padding(3.dp),
                horizontalArrangement = Arrangement.spacedBy(3.dp),
            ) {
                TimeInputMode.entries.forEach { m ->
                    SegmentTab(
                        text = m.label,
                        selected = m == mode,
                        onClick = { onModeChange(m) },
                        modifier = Modifier.weight(1f),
                    )
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(Space2)) {
                when (mode) {
                    TimeInputMode.DateTime -> {
                        FieldShell(modifier = Modifier.weight(1f).testTag("date-field"), icon = TaskIcons.Calendar) {
                            ShellInput(value = date, onValueChange = onDateChange, placeholder = "Jun 12, 2026")
                        }
                        FieldShell(modifier = Modifier.weight(1f).testTag("time-field"), icon = TaskIcons.Clock) {
                            ShellInput(value = time, onValueChange = onTimeChange, placeholder = "12:59 PM")
                        }
                    }
                    TimeInputMode.DaysHours -> {
                        FieldShell(modifier = Modifier.weight(1f).testTag("days-field"), unit = "days") {
                            ShellInput(value = days, onValueChange = onDaysChange, placeholder = "2", numeric = true)
                        }
                        FieldShell(modifier = Modifier.weight(1f).testTag("hours-field"), unit = "hours") {
                            ShellInput(value = hours, onValueChange = onHoursChange, placeholder = "0", numeric = true)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SegmentTab(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(RadiusXs)
    Box(
        modifier = modifier
            .height(34.dp)
            .then(if (selected) Modifier.shadow(1.dp, shape) else Modifier)
            .clip(shape)
            .background(if (selected) SurfaceRaised else androidx.compose.ui.graphics.Color.Transparent)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontFamily = GeneralSans,
                fontWeight = FontWeight.W500,
                fontSize = 14.sp,
            ),
            color = if (selected) Ink900 else Ink500,
            maxLines = 1,
        )
    }
}

@Composable
private fun FieldShell(
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    unit: String? = null,
    content: @Composable () -> Unit,
) {
    Row(
        modifier = modifier
            .height(48.dp)
            .background(Surface, RoundedCornerShape(RadiusSm))
            .border(1.dp, HairlineStrong, RoundedCornerShape(RadiusSm))
            .padding(horizontal = 14.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (icon != null) {
            TaskIcon(icon = icon, contentDescription = null, size = 20.dp, tint = Ink500)
        }
        Box(modifier = Modifier.weight(1f)) {
            content()
        }
        if (unit != null) {
            Text(
                text = unit,
                style = TextStyle(
                    fontFamily = GeneralSans,
                    fontWeight = FontWeight.W400,
                    fontSize = 14.sp,
                ),
                color = Ink400,
            )
        }
    }
}

@Composable
private fun ShellInput(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    numeric: Boolean = false,
) {
    val style = if (numeric) FieldTextStyle.copy(textAlign = TextAlign.End) else FieldTextStyle
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = style,
        singleLine = true,
        cursorBrush = SolidColor(Ink900),
        keyboardOptions = if (numeric) {
            KeyboardOptions(keyboardType = KeyboardType.Number)
        } else {
            KeyboardOptions.Default
        },
        modifier = Modifier.fillMaxWidth(),
        decorationBox = { innerTextField ->
            Box(contentAlignment = if (numeric) Alignment.CenterEnd else Alignment.CenterStart) {
                if (value.isEmpty()) {
                    Text(text = placeholder, style = style.copy(color = Ink400))
                }
                innerTextField()
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun TaskTimeInputPreview() {
    TaskClusterTheme {
        TaskTimeInput(
            mode = TimeInputMode.DateTime,
            noLimit = false,
            date = "",
            time = "12:59 PM",
            days = "",
            hours = "0",
            onModeChange = {},
            onNoLimitChange = {},
            onDateChange = {},
            onTimeChange = {},
            onDaysChange = {},
            onHoursChange = {},
        )
    }
}
