package com.journeytix.taskcluster.ui.components.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.journeytix.taskcluster.ui.theme.BluePress
import com.journeytix.taskcluster.ui.theme.BlueTint
import com.journeytix.taskcluster.ui.theme.GeneralSans
import com.journeytix.taskcluster.ui.theme.Ink600
import com.journeytix.taskcluster.ui.theme.RadiusPill
import com.journeytix.taskcluster.ui.theme.SurfaceSunken
import com.journeytix.taskcluster.ui.theme.TaskClusterTheme
import com.journeytix.taskcluster.ui.theme.TrackSnug

enum class TaskBadgeTone { Neutral, Accent }

@Composable
fun TaskBadge(
    text: String,
    modifier: Modifier = Modifier,
    tone: TaskBadgeTone = TaskBadgeTone.Neutral,
) {
    val bg = when (tone) {
        TaskBadgeTone.Neutral -> SurfaceSunken
        TaskBadgeTone.Accent -> BlueTint
    }
    val color = when (tone) {
        TaskBadgeTone.Neutral -> Ink600
        TaskBadgeTone.Accent -> BluePress
    }
    Row(
        modifier = modifier
            .height(24.dp)
            .clip(RoundedCornerShape(RadiusPill))
            .background(bg)
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontFamily = GeneralSans,
                fontWeight = FontWeight.W500,
                fontSize = 13.sp,
                letterSpacing = TrackSnug,
            ),
            color = color,
            maxLines = 1,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskBadgePreview() {
    TaskClusterTheme {
        Row {
            TaskBadge(text = "3 imported")
            TaskBadge(text = "2 sections", tone = TaskBadgeTone.Accent)
        }
    }
}
