package com.journeytix.taskcluster.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.journeytix.taskcluster.ui.components.core.TaskIcon
import com.journeytix.taskcluster.ui.components.core.TaskIconButton
import com.journeytix.taskcluster.ui.components.core.TaskIcons
import com.journeytix.taskcluster.ui.components.feedback.PopupShell
import com.journeytix.taskcluster.ui.theme.*
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle as JavaTextStyle
import java.util.Locale

private val WEEKDAY_LABELS = listOf("M", "T", "W", "T", "F", "S", "S")

@Composable
fun CalendarPopup(
    selectedDate: LocalDate,
    today: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit,
) {
    var viewMonth by remember { mutableStateOf(YearMonth.from(selectedDate)) }
    var showYearPicker by remember { mutableStateOf(false) }

    PopupShell(onDismiss = onDismiss) {
        // Header with month/year navigation
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TaskIconButton(
                icon = TaskIcons.ChevronLeft,
                label = "Previous",
                size = 32.dp,
                onClick = {
                    viewMonth = if (showYearPicker) viewMonth.minusYears(12) else viewMonth.minusMonths(1)
                },
            )
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                    ) { showYearPicker = !showYearPicker }
                    .padding(horizontal = 12.dp, vertical = 4.dp),
            ) {
                Text(
                    text = if (showYearPicker) {
                        val baseYear = (viewMonth.year / 12) * 12
                        "${baseYear - 4} – ${baseYear + 7}"
                    } else {
                        "${viewMonth.month.getDisplayName(JavaTextStyle.FULL, Locale.ENGLISH)} ${viewMonth.year}"
                    },
                    style = TextStyle(
                        fontFamily = GeneralSans,
                        fontWeight = FontWeight.W500,
                        fontSize = 16.sp,
                    ),
                    color = Ink900,
                )
            }
            TaskIconButton(
                icon = TaskIcons.ChevronRight,
                label = "Next",
                size = 32.dp,
                onClick = {
                    viewMonth = if (showYearPicker) viewMonth.plusYears(12) else viewMonth.plusMonths(1)
                },
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        if (showYearPicker) {
            // Year grid
            val baseYear = (viewMonth.year / 12) * 12
            val years = (baseYear - 4..baseYear + 7).toList()
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                for (row in years.chunked(4)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                    ) {
                        for (year in row) {
                            val isCurrentYear = year == today.year
                            val isSelected = year == viewMonth.year
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(40.dp)
                                    .clip(RoundedCornerShape(RadiusSm))
                                    .background(if (isSelected) Ink900 else androidx.compose.ui.graphics.Color.Transparent)
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null,
                                    ) {
                                        viewMonth = viewMonth.withYear(year)
                                        showYearPicker = false
                                    },
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    text = year.toString(),
                                    style = TextStyle(
                                        fontFamily = GeneralSans,
                                        fontWeight = FontWeight.W500,
                                        fontSize = 14.sp,
                                    ),
                                    color = when {
                                        isSelected -> Surface
                                        isCurrentYear -> Blue
                                        else -> Ink900
                                    },
                                )
                            }
                        }
                    }
                }
            }
        } else {
            // Weekday headers
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                WEEKDAY_LABELS.forEach { wd ->
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = wd,
                            style = TextStyle(
                                fontFamily = GeneralSans,
                                fontWeight = FontWeight.W400,
                                fontSize = 12.sp,
                            ),
                            color = Ink400,
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Day grid
            val firstOfMonth = viewMonth.atDay(1)
            val daysInMonth = viewMonth.lengthOfMonth()
            val startDayOfWeek = (firstOfMonth.dayOfWeek.value - 1) // 0 = Monday
            val cells = buildList {
                repeat(startDayOfWeek) { add(null) }
                for (d in 1..daysInMonth) add(d)
            }

            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                for (week in cells.chunked(7)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(2.dp),
                    ) {
                        for (day in week) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f),
                                contentAlignment = Alignment.Center,
                            ) {
                                if (day != null) {
                                    val date = viewMonth.atDay(day)
                                    val isToday = date == today
                                    val isSelected = date == selectedDate
                                    Box(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(CircleShape)
                                            .background(
                                                when {
                                                    isToday -> Blue
                                                    isSelected -> Ink900
                                                    else -> androidx.compose.ui.graphics.Color.Transparent
                                                }
                                            )
                                            .clickable(
                                                interactionSource = remember { MutableInteractionSource() },
                                                indication = null,
                                            ) { onDateSelected(date) },
                                        contentAlignment = Alignment.Center,
                                    ) {
                                        Text(
                                            text = day.toString(),
                                            style = TextStyle(
                                                fontFamily = GeneralSans,
                                                fontWeight = FontWeight.W500,
                                                fontSize = 14.sp,
                                            ),
                                            color = when {
                                                isToday || isSelected -> Surface
                                                else -> Ink900
                                            },
                                        )
                                    }
                                }
                            }
                        }
                        // Pad remaining cells in last row
                        repeat(7 - week.size) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}
