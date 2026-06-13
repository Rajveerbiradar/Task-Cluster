package com.journeytix.taskcluster.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
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
import com.journeytix.taskcluster.ui.theme.Ink600
import com.journeytix.taskcluster.ui.theme.Ink900

enum class AddChooserOption {
    NewParent, NewSection, Import
}

@Composable
fun AddChooser(
    onSelect: (AddChooserOption) -> Unit,
    onDismiss: () -> Unit,
) {
    PopupShell(onDismiss = onDismiss) {
        Text(
            text = "Create",
            style = TextStyle(
                fontFamily = GeneralSans,
                fontWeight = FontWeight.W500,
                fontSize = 18.sp,
            ),
            color = Ink900,
        )
        Spacer(modifier = Modifier.height(16.dp))
        ChooserRow(
            label = "New parent",
            icon = { TaskIcon(TaskIcons.Briefcase, null, tint = Ink600) },
            onClick = { onSelect(AddChooserOption.NewParent) },
        )
        ChooserRow(
            label = "New section",
            icon = { TaskIcon(TaskIcons.List, null, tint = Ink600) },
            onClick = { onSelect(AddChooserOption.NewSection) },
        )
        ChooserRow(
            label = "Import from .txt",
            icon = { TaskIcon(TaskIcons.Import, null, tint = Ink600) },
            onClick = { onSelect(AddChooserOption.Import) },
        )
    }
}

@Composable
private fun ChooserRow(
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
