package com.journeytix.taskcluster.ui.components.feedback

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.journeytix.taskcluster.ui.components.core.TaskButton
import com.journeytix.taskcluster.ui.components.core.TaskButtonVariant
import com.journeytix.taskcluster.ui.theme.GeneralSans
import com.journeytix.taskcluster.ui.theme.HairlineStrong
import com.journeytix.taskcluster.ui.theme.Ink600
import com.journeytix.taskcluster.ui.theme.Ink900
import com.journeytix.taskcluster.ui.theme.PrimaryOn
import com.journeytix.taskcluster.ui.theme.Red
import com.journeytix.taskcluster.ui.theme.SurfaceRaised

/* ConfirmDialog — required before every destructive action.
   Canceling (or tapping outside) never executes the action. */
@Composable
fun ConfirmDialog(
    title: String,
    message: String,
    confirmLabel: String,
    confirmDanger: Boolean = true,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    Dialog(onDismissRequest = onDismiss) {
        val shape = RoundedCornerShape(20.dp)
        Column(
            modifier = Modifier
                .widthIn(max = 380.dp)
                .background(SurfaceRaised, shape)
                .border(1.5.dp, HairlineStrong, shape)
                .padding(start = 22.dp, end = 22.dp, top = 20.dp, bottom = 24.dp),
        ) {
            Text(
                text = title,
                style = TextStyle(
                    fontFamily = GeneralSans,
                    fontWeight = FontWeight.W500,
                    fontSize = 18.sp,
                    lineHeight = 22.sp,
                ),
                color = Ink900,
            )
            Text(
                text = message,
                style = TextStyle(
                    fontFamily = GeneralSans,
                    fontWeight = FontWeight.W400,
                    fontSize = 15.sp,
                    lineHeight = 22.sp,
                ),
                color = Ink600,
                modifier = Modifier.padding(top = 10.dp, bottom = 20.dp),
            )
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Box(modifier = Modifier.weight(1f)) {
                    TaskButton(
                        text = "Cancel",
                        onClick = onDismiss,
                        variant = TaskButtonVariant.Secondary,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
                Box(modifier = Modifier.weight(1f)) {
                    if (confirmDanger) {
                        DangerButton(text = confirmLabel, onClick = onConfirm)
                    } else {
                        TaskButton(
                            text = confirmLabel,
                            onClick = onConfirm,
                            variant = TaskButtonVariant.Primary,
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DangerButton(text: String, onClick: () -> Unit) {
    val shape = RoundedCornerShape(999.dp)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Red, shape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick,
            )
            .padding(vertical = 14.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontFamily = GeneralSans,
                fontWeight = FontWeight.W500,
                fontSize = 16.sp,
            ),
            color = PrimaryOn,
        )
    }
}
