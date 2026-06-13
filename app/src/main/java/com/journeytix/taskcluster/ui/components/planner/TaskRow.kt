package com.journeytix.taskcluster.ui.components.planner

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.background
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.journeytix.taskcluster.ui.components.forms.TaskCheckbox
import com.journeytix.taskcluster.ui.theme.GeneralSans
import com.journeytix.taskcluster.ui.theme.Hairline
import com.journeytix.taskcluster.ui.theme.Ink300
import com.journeytix.taskcluster.ui.theme.Ink600
import com.journeytix.taskcluster.ui.theme.Ink900
import com.journeytix.taskcluster.ui.theme.Space3
import com.journeytix.taskcluster.ui.theme.TaskClusterTheme

/* TaskRow — no surface of its own, divider-separated inside a SectionCard.
   Completed task greys out IN PLACE — never moves. A done task carries no
   urgency, so its pill drops. Long-press reveals the context menu. */
@Composable
fun TaskRow(
    title: String,
    checked: Boolean,
    onToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    description: String = "",
    status: TimePillStatus = TimePillStatus.Calm,
    time: String? = null,
    divider: Boolean = false,
    onMenu: ((IntOffset) -> Unit)? = null,
) {
    val titleColor = if (checked) Ink300 else Ink900
    val descColor = if (checked) Ink300 else Ink600

    var rootOrigin by remember { mutableStateOf(Offset.Zero) }
    Column(modifier = modifier.fillMaxWidth()) {
        if (divider) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Hairline),
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { rootOrigin = it.positionInRoot() }
                .pointerInput(onMenu) {
                    detectTapGestures(
                        onLongPress = { offset ->
                            onMenu?.invoke(
                                IntOffset(
                                    (rootOrigin.x + offset.x).toInt(),
                                    (rootOrigin.y + offset.y).toInt(),
                                )
                            )
                        },
                    )
                }
                .padding(vertical = 14.dp),
            horizontalArrangement = Arrangement.spacedBy(Space3),
        ) {
            Box(modifier = Modifier.padding(top = 1.dp)) {
                TaskCheckbox(checked = checked, onCheckedChange = onToggle)
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = TextStyle(
                        fontFamily = GeneralSans,
                        fontWeight = FontWeight.W400,
                        fontSize = 16.sp,
                        lineHeight = 22.sp, // 1.35
                    ),
                    color = titleColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                if (description.isNotEmpty()) {
                    Text(
                        text = description,
                        style = TextStyle(
                            fontFamily = GeneralSans,
                            fontWeight = FontWeight.W400,
                            fontSize = 14.sp,
                            lineHeight = 20.sp, // 1.4
                        ),
                        color = descColor,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(top = 3.dp),
                    )
                }
            }
            if (!checked) {
                TimePill(
                    status = status,
                    label = time,
                    modifier = Modifier
                        .align(Alignment.Top)
                        .padding(top = 2.dp)
                        .testTag("time-pill"),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskRowPreview() {
    TaskClusterTheme {
        Column {
            TaskRow(
                title = "finish the quarterly report",
                description = "include the new figures from finance",
                checked = false,
                onToggle = {},
                status = TimePillStatus.Close,
                time = "1h 30m",
            )
            TaskRow(
                title = "buy stamps",
                checked = true,
                onToggle = {},
                divider = true,
            )
        }
    }
}
