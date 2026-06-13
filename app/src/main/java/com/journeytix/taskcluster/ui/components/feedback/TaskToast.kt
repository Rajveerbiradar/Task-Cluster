package com.journeytix.taskcluster.ui.components.feedback

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.journeytix.taskcluster.ui.components.core.TaskIcon
import com.journeytix.taskcluster.ui.components.core.lucide
import com.journeytix.taskcluster.ui.theme.GeneralSans
import com.journeytix.taskcluster.ui.theme.RadiusMd
import com.journeytix.taskcluster.ui.theme.Space2
import com.journeytix.taskcluster.ui.theme.TaskClusterTheme
import com.journeytix.taskcluster.ui.theme.ToastBg
import com.journeytix.taskcluster.ui.theme.ToastText
import com.journeytix.taskcluster.ui.theme.TrackSnug

// The toast check is drawn heavier than the standard set (JSX stroke 2.25).
private val ToastCheck = lucide("toast-check", "M20 6 9 17l-5-5", stroke = 2.25f)

@Composable
fun TaskToast(
    text: String = "saved",
    modifier: Modifier = Modifier,
    showIcon: Boolean = true,
) {
    val shape = RoundedCornerShape(RadiusMd)
    Row(
        modifier = modifier
            .shadow(16.dp, shape)
            .background(ToastBg, shape)
            .padding(horizontal = 16.dp, vertical = 11.dp),
        horizontalArrangement = Arrangement.spacedBy(Space2),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (showIcon) {
            TaskIcon(
                icon = ToastCheck,
                contentDescription = null,
                size = 18.dp,
                tint = ToastText,
            )
        }
        Text(
            text = text,
            style = TextStyle(
                fontFamily = GeneralSans,
                fontWeight = FontWeight.W500,
                fontSize = 14.sp,
                letterSpacing = TrackSnug,
            ),
            color = ToastText,
            maxLines = 1,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskToastPreview() {
    TaskClusterTheme {
        TaskToast(text = "saved")
    }
}
