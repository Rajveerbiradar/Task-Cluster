package com.journeytix.taskcluster.ui.components.planner

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.journeytix.taskcluster.ui.theme.Amber
import com.journeytix.taskcluster.ui.theme.Blue
import com.journeytix.taskcluster.ui.theme.GeneralSans
import com.journeytix.taskcluster.ui.theme.OverdueBg
import com.journeytix.taskcluster.ui.theme.OverdueBorder
import com.journeytix.taskcluster.ui.theme.OverdueText
import com.journeytix.taskcluster.ui.theme.RadiusSm
import com.journeytix.taskcluster.ui.theme.Red
import com.journeytix.taskcluster.ui.theme.TaskClusterTheme
import com.journeytix.taskcluster.ui.theme.TrackSnug
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement

/* The app's one piece of drama:
   calm (>50%) → nothing shown · on-track → blue text · close → amber text ·
   due → red text · overdue → red capsule, counting up. */
enum class TimePillStatus { Calm, OnTrack, Close, Due, Overdue }

@Composable
fun TimePill(
    status: TimePillStatus,
    label: String?,
    modifier: Modifier = Modifier,
) {
    if (status == TimePillStatus.Calm || label.isNullOrEmpty()) return

    if (status == TimePillStatus.Overdue) {
        val shape = RoundedCornerShape(RadiusSm)
        Text(
            text = label,
            style = TextStyle(
                fontFamily = GeneralSans,
                fontWeight = FontWeight.W500,
                fontSize = 12.sp,
                letterSpacing = TrackSnug,
            ),
            color = OverdueText,
            maxLines = 1,
            modifier = modifier
                .background(OverdueBg, shape)
                .border(1.dp, OverdueBorder, shape)
                .padding(horizontal = 9.dp, vertical = 4.dp),
        )
        return
    }

    val color = when (status) {
        TimePillStatus.OnTrack -> Blue
        TimePillStatus.Close -> Amber
        else -> Red // Due
    }
    Text(
        text = label,
        style = TextStyle(
            fontFamily = GeneralSans,
            fontWeight = FontWeight.W500,
            fontSize = 13.sp,
            letterSpacing = TrackSnug,
        ),
        color = color,
        maxLines = 1,
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
private fun TimePillPreview() {
    TaskClusterTheme {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TimePill(status = TimePillStatus.OnTrack, label = "4h 10m")
            TimePill(status = TimePillStatus.Close, label = "1h 30m")
            TimePill(status = TimePillStatus.Due, label = "12m")
            TimePill(status = TimePillStatus.Overdue, label = "−2h 14m")
        }
    }
}
