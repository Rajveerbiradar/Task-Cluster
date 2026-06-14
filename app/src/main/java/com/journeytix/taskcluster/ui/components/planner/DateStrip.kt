package com.journeytix.taskcluster.ui.components.planner

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.journeytix.taskcluster.ui.components.core.TaskIcon
import com.journeytix.taskcluster.ui.components.core.TaskIcons
import com.journeytix.taskcluster.ui.theme.GeneralSans
import com.journeytix.taskcluster.ui.theme.HairlineStrong
import com.journeytix.taskcluster.ui.theme.Ink200
import com.journeytix.taskcluster.ui.theme.Ink400
import com.journeytix.taskcluster.ui.theme.Ink500
import com.journeytix.taskcluster.ui.theme.Ink600
import com.journeytix.taskcluster.ui.theme.Ink900
import com.journeytix.taskcluster.ui.theme.Primary
import com.journeytix.taskcluster.ui.theme.PrimaryOn
import com.journeytix.taskcluster.ui.theme.RadiusPill
import com.journeytix.taskcluster.ui.theme.RadiusSm
import com.journeytix.taskcluster.ui.theme.Surface
import com.journeytix.taskcluster.ui.theme.TaskClusterTheme

data class DateStripDay(
    val key: String,
    val weekday: String,
    val date: Int,
    val isPlaceholder: Boolean = false,
)

private val WeekdayStyle = TextStyle(
    fontFamily = GeneralSans,
    fontWeight = FontWeight.W400,
    fontSize = 12.sp,
)
private val DayNumberStyle = TextStyle(
    fontFamily = GeneralSans,
    fontWeight = FontWeight.W500,
    fontSize = 16.sp,
)

/* DateStrip — a horizontally-scrollable week with a fixed calendar button on
   the left to jump to any date. Today fills ink; a selected non-today day
   fills Ink200. */
@Composable
fun DateStrip(
    days: List<DateStripDay>,
    selectedKey: String,
    todayKey: String,
    onSelect: (String) -> Unit,
    onCalendar: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        // Calendar / jump-to-date button
        Column(
            modifier = Modifier
                .width(44.dp)
                .clip(RoundedCornerShape(14.dp))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onCalendar,
                )
                .padding(vertical = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Text(text = "All", style = WeekdayStyle, color = Ink400)
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(RadiusPill))
                    .background(Surface)
                    .border(1.dp, HairlineStrong, RoundedCornerShape(RadiusPill)),
                contentAlignment = Alignment.Center,
            ) {
                TaskIcon(
                    icon = TaskIcons.Calendar,
                    contentDescription = "Open calendar",
                    size = 19.dp,
                    tint = Ink600,
                )
            }
        }

        // Divider between the button and the week
        Box(
            modifier = Modifier
                .padding(start = 2.dp, end = 4.dp)
                .align(Alignment.CenterVertically)
                .width(1.dp)
                .height(40.dp)
                .background(HairlineStrong),
        )

        // The full week — equal-weight cells so all seven days always fit on screen.
        Row(
            modifier = Modifier
                .weight(1f)
                .padding(top = 4.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            days.forEach { day ->
                val isToday = day.key == todayKey
                val isSelected = day.key == selectedKey
                val cellBg = when {
                    isToday -> Primary
                    isSelected -> Ink200
                    else -> Color.Transparent
                }
                val numColor = if (isToday) PrimaryOn else Ink900
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                        ) { onSelect(day.key) }
                        .padding(vertical = 4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Text(
                        text = day.weekday,
                        style = WeekdayStyle,
                        color = if (isToday) Primary else Ink500,
                        maxLines = 1,
                    )
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(RadiusSm))
                            .background(cellBg),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(text = day.date.toString(), style = DayNumberStyle, color = numColor)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DateStripPreview() {
    TaskClusterTheme {
        val week = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
            .mapIndexed { i, w -> DateStripDay(key = "${9 + i}", weekday = w, date = 9 + i) }
        DateStrip(
            days = week,
            selectedKey = "11",
            todayKey = "12",
            onSelect = {},
            onCalendar = {},
        )
    }
}
