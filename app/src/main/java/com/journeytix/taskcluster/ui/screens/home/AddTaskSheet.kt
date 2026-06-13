package com.journeytix.taskcluster.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.journeytix.taskcluster.data.model.Task
import com.journeytix.taskcluster.ui.components.core.TaskButton
import com.journeytix.taskcluster.ui.components.core.TaskButtonVariant
import com.journeytix.taskcluster.ui.components.core.TaskIconButton
import com.journeytix.taskcluster.ui.components.core.TaskIcons
import com.journeytix.taskcluster.ui.components.forms.TaskTextField
import com.journeytix.taskcluster.ui.components.forms.TaskTimeInput
import com.journeytix.taskcluster.ui.components.forms.TimeInputMode
import com.journeytix.taskcluster.ui.theme.GeneralSans
import com.journeytix.taskcluster.ui.theme.HairlineStrong
import com.journeytix.taskcluster.ui.theme.Ink900
import com.journeytix.taskcluster.ui.theme.RadiusLg
import com.journeytix.taskcluster.ui.theme.Scrim
import com.journeytix.taskcluster.ui.theme.Space4
import com.journeytix.taskcluster.ui.theme.Space5
import com.journeytix.taskcluster.ui.theme.SurfaceRaised
import androidx.compose.material3.Text
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

data class TaskDraft(
    val title: String = "",
    val description: String = "",
    val noLimit: Boolean = true,
    val timeMode: TimeInputMode = TimeInputMode.DateTime,
    val date: String = "",
    val time: String = "",
    val days: String = "",
    val hours: String = "",
) {
    fun toDueTimestamp(): Long? {
        if (noLimit) return null
        return when (timeMode) {
            TimeInputMode.DaysHours -> {
                val d = days.toLongOrNull() ?: 0
                val h = hours.toLongOrNull() ?: 0
                if (d == 0L && h == 0L) null
                else System.currentTimeMillis() + d * 86_400_000 + h * 3_600_000
            }
            TimeInputMode.DateTime -> {
                null
            }
        }
    }
}

@Composable
fun AddTaskSheet(
    open: Boolean,
    sectionId: Long,
    editTask: Task? = null,
    onDismiss: () -> Unit,
    onSave: (title: String, description: String, dueDate: Long?, dueTime: Long?) -> Unit,
) {
    if (!open) return

    var draft by remember { mutableStateOf(TaskDraft()) }
    val isEdit = editTask != null

    LaunchedEffect(open, editTask) {
        if (open) {
            draft = if (editTask != null) {
                TaskDraft(
                    title = editTask.title,
                    description = editTask.description,
                    noLimit = editTask.dueDate == null,
                )
            } else {
                TaskDraft()
            }
        }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Scrim)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onDismiss,
                ),
            contentAlignment = Alignment.Center,
        ) {
            val shape = RoundedCornerShape(20.dp)
            Column(
                modifier = Modifier
                    .widthIn(max = 400.dp)
                    .padding(20.dp)
                    .clip(shape)
                    .background(SurfaceRaised, shape)
                    .border(1.5.dp, HairlineStrong, shape)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = {},
                    )
                    .padding(horizontal = 22.dp, vertical = 20.dp),
            ) {
                // Header
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = if (isEdit) "Edit task" else "New task",
                        style = TextStyle(
                            fontFamily = GeneralSans,
                            fontWeight = FontWeight.W500,
                            fontSize = 18.sp,
                        ),
                        color = Ink900,
                        modifier = Modifier.align(Alignment.CenterStart),
                    )
                    TaskIconButton(
                        icon = TaskIcons.X,
                        label = "Close",
                        size = 32.dp,
                        onClick = onDismiss,
                        modifier = Modifier.align(Alignment.CenterEnd),
                    )
                }

                Spacer(modifier = Modifier.height(Space4))

                Column(verticalArrangement = Arrangement.spacedBy(Space4)) {
                    TaskTextField(
                        value = draft.title,
                        onValueChange = { draft = draft.copy(title = it) },
                        label = "Title",
                        placeholder = "What needs doing?",
                    )
                    TaskTextField(
                        value = draft.description,
                        onValueChange = { draft = draft.copy(description = it) },
                        label = "Description",
                        placeholder = "Optional — truncates at two lines",
                        multiline = true,
                        rows = 2,
                    )
                    TaskTimeInput(
                        mode = draft.timeMode,
                        noLimit = draft.noLimit,
                        date = draft.date,
                        time = draft.time,
                        days = draft.days,
                        hours = draft.hours,
                        onModeChange = { draft = draft.copy(timeMode = it) },
                        onNoLimitChange = { draft = draft.copy(noLimit = it) },
                        onDateChange = { draft = draft.copy(date = it) },
                        onTimeChange = { draft = draft.copy(time = it) },
                        onDaysChange = { draft = draft.copy(days = it) },
                        onHoursChange = { draft = draft.copy(hours = it) },
                    )
                }

                Spacer(modifier = Modifier.height(Space5))

                TaskButton(
                    text = if (isEdit) "Save changes" else "Add task",
                    onClick = {
                        val title = draft.title.ifBlank { "Untitled task" }
                        val dueTime = draft.toDueTimestamp()
                        onSave(title, draft.description, dueTime, dueTime)
                    },
                    variant = TaskButtonVariant.Primary,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}
