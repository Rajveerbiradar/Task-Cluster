package com.journeytix.taskcluster.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.journeytix.taskcluster.data.model.Task
import com.journeytix.taskcluster.ui.components.core.TaskButton
import com.journeytix.taskcluster.ui.components.core.TaskButtonVariant
import com.journeytix.taskcluster.ui.components.core.TaskIcon
import com.journeytix.taskcluster.ui.components.core.TaskIconButton
import com.journeytix.taskcluster.ui.components.core.TaskIcons
import com.journeytix.taskcluster.ui.components.forms.TaskCheckbox
import com.journeytix.taskcluster.ui.components.forms.TaskTextField
import com.journeytix.taskcluster.ui.theme.*
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

private val DATE_FORMAT = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH)
private val TIME_FORMAT = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskSheet(
    open: Boolean,
    sectionId: Long,
    editTask: Task? = null,
    onDismiss: () -> Unit,
    onSave: (title: String, description: String, dueDate: Long?, dueTime: Long?) -> Unit,
) {
    if (!open) return

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var noLimit by remember { mutableStateOf(true) }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var selectedTime by remember { mutableStateOf<LocalTime?>(null) }
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    val isEdit = editTask != null

    LaunchedEffect(open, editTask) {
        if (open) {
            if (editTask != null) {
                title = editTask.title
                description = editTask.description
                noLimit = editTask.dueDate == null
                selectedDate = editTask.dueDate?.let {
                    Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate()
                }
                selectedTime = editTask.dueTime?.let {
                    Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalTime()
                }
            } else {
                title = ""
                description = ""
                noLimit = true
                selectedDate = null
                selectedTime = null
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
                        value = title,
                        onValueChange = { title = it },
                        label = "Title",
                        placeholder = "What needs doing?",
                    )
                    TaskTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = "Description",
                        placeholder = "Optional — truncates at two lines",
                        multiline = true,
                        rows = 2,
                    )

                    // Deadline section
                    Column {
                        Text(
                            text = "Deadline",
                            style = TextStyle(
                                fontFamily = GeneralSans,
                                fontWeight = FontWeight.W500,
                                fontSize = 14.sp,
                            ),
                            color = Ink600,
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            TaskCheckbox(
                                checked = noLimit,
                                onCheckedChange = { noLimit = it },
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "No time limit",
                                style = TextStyle(
                                    fontFamily = GeneralSans,
                                    fontWeight = FontWeight.W400,
                                    fontSize = 15.sp,
                                ),
                                color = Ink900,
                            )
                        }

                        if (!noLimit) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                            ) {
                                // Date picker field
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(48.dp)
                                        .clip(RoundedCornerShape(RadiusSm))
                                        .background(SurfaceSunken)
                                        .border(1.dp, Hairline, RoundedCornerShape(RadiusSm))
                                        .clickable { showDatePicker = true }
                                        .padding(horizontal = 12.dp),
                                    contentAlignment = Alignment.CenterStart,
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        TaskIcon(TaskIcons.Calendar, null, size = 18.dp, tint = Ink500)
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = selectedDate?.format(DATE_FORMAT) ?: "Select date",
                                            style = TextStyle(
                                                fontFamily = GeneralSans,
                                                fontWeight = FontWeight.W400,
                                                fontSize = 15.sp,
                                            ),
                                            color = if (selectedDate != null) Ink900 else Ink400,
                                        )
                                    }
                                }

                                // Time picker field
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(48.dp)
                                        .clip(RoundedCornerShape(RadiusSm))
                                        .background(SurfaceSunken)
                                        .border(1.dp, Hairline, RoundedCornerShape(RadiusSm))
                                        .clickable { showTimePicker = true }
                                        .padding(horizontal = 12.dp),
                                    contentAlignment = Alignment.CenterStart,
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        TaskIcon(TaskIcons.Clock, null, size = 18.dp, tint = Ink500)
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = selectedTime?.format(TIME_FORMAT) ?: "Select time",
                                            style = TextStyle(
                                                fontFamily = GeneralSans,
                                                fontWeight = FontWeight.W400,
                                                fontSize = 15.sp,
                                            ),
                                            color = if (selectedTime != null) Ink900 else Ink400,
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(Space5))

                TaskButton(
                    text = if (isEdit) "Save changes" else "Add task",
                    onClick = {
                        val finalTitle = title.ifBlank { "Untitled task" }
                        val dueTimestamp = if (noLimit || selectedDate == null) {
                            null
                        } else {
                            val date = selectedDate!!
                            val time = selectedTime ?: LocalTime.NOON
                            date.atTime(time).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
                        }
                        onSave(finalTitle, description, dueTimestamp, dueTimestamp)
                    },
                    variant = TaskButtonVariant.Primary,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }

    // Material3 DatePicker Dialog
    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = selectedDate?.atStartOfDay(ZoneId.systemDefault())?.toInstant()?.toEpochMilli()
        )
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        selectedDate = Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate()
                    }
                    showDatePicker = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel")
                }
            },
        ) {
            DatePicker(state = datePickerState)
        }
    }

    // Material3 TimePicker Dialog
    if (showTimePicker) {
        val timePickerState = rememberTimePickerState(
            initialHour = selectedTime?.hour ?: 12,
            initialMinute = selectedTime?.minute ?: 0,
        )
        AlertDialog(
            onDismissRequest = { showTimePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    selectedTime = LocalTime.of(timePickerState.hour, timePickerState.minute)
                    showTimePicker = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showTimePicker = false }) {
                    Text("Cancel")
                }
            },
            text = {
                TimePicker(state = timePickerState)
            },
        )
    }
}
