package com.journeytix.taskcluster.ui.components.feedback

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.journeytix.taskcluster.ui.theme.Blue
import com.journeytix.taskcluster.ui.theme.GeneralSans
import com.journeytix.taskcluster.ui.theme.RadiusMd
import com.journeytix.taskcluster.ui.theme.RadiusSm
import com.journeytix.taskcluster.ui.theme.TaskClusterTheme
import com.journeytix.taskcluster.ui.theme.ToastBg
import com.journeytix.taskcluster.ui.theme.ToastText

@Composable
fun TaskSnackbar(
    text: String = "task deleted",
    action: String = "undo",
    onAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(RadiusMd)
    Row(
        modifier = modifier
            .shadow(16.dp, shape)
            .background(ToastBg, shape)
            .padding(start = 16.dp, top = 12.dp, end = 12.dp, bottom = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontFamily = GeneralSans,
                fontWeight = FontWeight.W400,
                fontSize = 14.sp,
            ),
            color = ToastText,
            modifier = Modifier.weight(1f),
        )
        Text(
            text = action,
            style = TextStyle(
                fontFamily = GeneralSans,
                fontWeight = FontWeight.W500,
                fontSize = 14.sp,
            ),
            color = Blue,
            modifier = Modifier
                .clip(RoundedCornerShape(RadiusSm))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onAction,
                )
                .padding(horizontal = 10.dp, vertical = 6.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskSnackbarPreview() {
    TaskClusterTheme {
        TaskSnackbar(text = "task deleted", action = "undo", onAction = {})
    }
}
