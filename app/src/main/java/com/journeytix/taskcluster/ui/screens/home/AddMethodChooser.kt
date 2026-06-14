package com.journeytix.taskcluster.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.journeytix.taskcluster.ui.components.core.TaskIcon
import com.journeytix.taskcluster.ui.components.core.TaskIcons
import com.journeytix.taskcluster.ui.components.feedback.PopupShell
import com.journeytix.taskcluster.ui.theme.GeneralSans
import com.journeytix.taskcluster.ui.theme.Ink500
import com.journeytix.taskcluster.ui.theme.Ink600
import com.journeytix.taskcluster.ui.theme.Ink900

/* Asks how to add: type it in by hand, or bring it in from a file / pasted text.
   Both import paths open the same ImportSheet (which supports file and paste). */
@Composable
fun AddMethodChooser(
    onManual: () -> Unit,
    onImport: () -> Unit,
    onDismiss: () -> Unit,
) {
    PopupShell(onDismiss = onDismiss) {
        Text(
            text = "How do you want to add?",
            style = TextStyle(
                fontFamily = GeneralSans,
                fontWeight = FontWeight.W500,
                fontSize = 18.sp,
            ),
            color = Ink900,
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "Type it in, or bring it in from a file or pasted text.",
            style = TextStyle(
                fontFamily = GeneralSans,
                fontWeight = FontWeight.W400,
                fontSize = 14.sp,
                lineHeight = 20.sp,
            ),
            color = Ink500,
        )
        Spacer(modifier = Modifier.height(16.dp))
        MethodRow(
            label = "Create manually",
            icon = { TaskIcon(TaskIcons.Pencil, null, tint = Ink600) },
            onClick = onManual,
        )
        MethodRow(
            label = "Import a file",
            icon = { TaskIcon(TaskIcons.Import, null, tint = Ink600) },
            onClick = onImport,
        )
        MethodRow(
            label = "Paste text",
            icon = { TaskIcon(TaskIcons.List, null, tint = Ink600) },
            onClick = onImport,
        )
    }
}

@Composable
private fun MethodRow(
    label: String,
    icon: @Composable () -> Unit,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick,
            )
            .padding(horizontal = 4.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        icon()
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = label,
            style = TextStyle(
                fontFamily = GeneralSans,
                fontWeight = FontWeight.W400,
                fontSize = 16.sp,
            ),
            color = Ink900,
        )
    }
}
