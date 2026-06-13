package com.journeytix.taskcluster.ui.components.feedback

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
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
import com.journeytix.taskcluster.ui.components.core.TaskIcon
import com.journeytix.taskcluster.ui.components.core.TaskIcons
import com.journeytix.taskcluster.ui.theme.GeneralSans
import com.journeytix.taskcluster.ui.theme.Ink500
import com.journeytix.taskcluster.ui.theme.Ink600
import com.journeytix.taskcluster.ui.theme.RadiusMd
import com.journeytix.taskcluster.ui.theme.TaskClusterTheme
import com.journeytix.taskcluster.ui.theme.WarnBg
import com.journeytix.taskcluster.ui.theme.WarnBorder

enum class TaskBannerVariant(val copy: String) {
    ReadOnly("viewing — read only"),
    Planning("planning — hidden until then"),
    Info(""),
}

@Composable
fun TaskBanner(
    variant: TaskBannerVariant = TaskBannerVariant.Info,
    modifier: Modifier = Modifier,
    text: String? = null,
) {
    val shape = RoundedCornerShape(RadiusMd)
    Row(
        modifier = modifier
            .clip(shape)
            .background(WarnBg)
            .border(1.dp, WarnBorder, shape)
            .padding(horizontal = 14.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TaskIcon(
            icon = TaskIcons.Info,
            contentDescription = null,
            size = 18.dp,
            tint = Ink500,
        )
        Text(
            text = text ?: variant.copy,
            style = TextStyle(
                fontFamily = GeneralSans,
                fontWeight = FontWeight.W400,
                fontSize = 13.sp,
                lineHeight = 17.sp, // 1.3
            ),
            color = Ink600,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskBannerPreview() {
    TaskClusterTheme {
        TaskBanner(variant = TaskBannerVariant.ReadOnly)
    }
}
