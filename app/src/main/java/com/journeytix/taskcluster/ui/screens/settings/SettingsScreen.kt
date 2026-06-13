package com.journeytix.taskcluster.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.journeytix.taskcluster.data.preferences.Settings
import com.journeytix.taskcluster.ui.components.core.Page
import com.journeytix.taskcluster.ui.components.core.PageGroup
import com.journeytix.taskcluster.ui.components.core.PageRow
import com.journeytix.taskcluster.ui.components.feedback.ConfirmDialog
import com.journeytix.taskcluster.ui.screens.legal.LegalUrls
import com.journeytix.taskcluster.ui.theme.GeneralSans
import com.journeytix.taskcluster.ui.theme.HairlineStrong
import com.journeytix.taskcluster.ui.theme.Ink900
import com.journeytix.taskcluster.ui.theme.Primary
import com.journeytix.taskcluster.ui.theme.SurfaceRaised
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    onOpenLegal: (title: String, url: String) -> Unit,
    viewModel: SettingsViewModel = koinViewModel(),
) {
    val settings by viewModel.settings.collectAsState()
    var picking by remember { mutableStateOf<SettingKey?>(null) }
    var confirmClear by remember { mutableStateOf(false) }
    var confirmReset by remember { mutableStateOf(false) }

    Page(title = "Settings", onBack = onBack) {
        PageGroup(title = "Appearance") {
            PageRow(
                title = "Theme",
                value = SettingKey.Theme.labelFor(settings.theme),
                onClick = { picking = SettingKey.Theme },
                last = true,
            )
        }
        PageGroup(title = "Tasks") {
            PageRow(
                title = "Default priority",
                value = SettingKey.DefaultPriority.labelFor(settings.defaultPriority),
                onClick = { picking = SettingKey.DefaultPriority },
            )
            PageRow(
                title = "Default sort order",
                value = SettingKey.DefaultSort.labelFor(settings.defaultSort),
                onClick = { picking = SettingKey.DefaultSort },
            )
            PageRow(
                title = "Auto-hide completed",
                value = SettingKey.AutoHideCompleted.labelFor(settings.autoHideCompleted),
                onClick = { picking = SettingKey.AutoHideCompleted },
            )
            PageRow(
                title = "Clear all completed",
                danger = true,
                onClick = { confirmClear = true },
                last = true,
            )
        }
        PageGroup(title = "Notifications") {
            PageRow(
                title = "Due-date reminders",
                value = if (settings.remindersEnabled) "On" else "Off",
                onClick = { picking = SettingKey.Reminders },
            )
            PageRow(
                title = "Default reminder time",
                value = SettingKey.ReminderOffset.labelFor(settings.reminderOffset),
                onClick = { picking = SettingKey.ReminderOffset },
                last = true,
            )
        }
        PageGroup(title = "Trash") {
            PageRow(
                title = "Auto-delete trashed items",
                value = SettingKey.TrashAutoDelete.labelFor(settings.trashAutoDelete),
                onClick = { picking = SettingKey.TrashAutoDelete },
                last = true,
            )
        }
        PageGroup(title = "Data") {
            PageRow(
                title = "Export tasks",
                value = ".csv · .json",
                onClick = { /* share intent — not yet built */ },
            )
            PageRow(
                title = "Import tasks",
                value = ".csv · .json",
                onClick = { /* file picker — not yet built */ },
                last = true,
            )
        }
        PageGroup(title = "Legal") {
            PageRow(
                title = "Privacy Policy",
                onClick = { onOpenLegal("Privacy Policy", LegalUrls.PRIVACY_POLICY) },
            )
            PageRow(
                title = "Terms of Service",
                onClick = { onOpenLegal("Terms of Service", LegalUrls.TERMS_OF_SERVICE) },
            )
            PageRow(
                title = "End User License Agreement",
                onClick = { onOpenLegal("End User License Agreement", LegalUrls.EULA) },
            )
            PageRow(
                title = "Data Deletion Policy",
                onClick = { onOpenLegal("Data Deletion Policy", LegalUrls.DATA_DELETION) },
                last = true,
            )
        }
        PageGroup(title = "Danger zone") {
            PageRow(
                title = "Reset to default settings",
                danger = true,
                onClick = { confirmReset = true },
                last = true,
            )
        }
    }

    picking?.let { key ->
        OptionsDialog(
            key = key,
            settings = settings,
            onPick = { value ->
                viewModel.onIntent(SettingsIntent.SetOption(key, value))
                picking = null
            },
            onDismiss = { picking = null },
        )
    }

    if (confirmClear) {
        ConfirmDialog(
            title = "Clear all completed",
            message = "All completed tasks will move to trash.",
            confirmLabel = "Clear",
            onConfirm = {
                viewModel.onIntent(SettingsIntent.ClearCompleted)
                confirmClear = false
            },
            onDismiss = { confirmClear = false },
        )
    }
    if (confirmReset) {
        ConfirmDialog(
            title = "Reset settings",
            message = "This will reset all settings to their defaults. Your tasks will not be affected.",
            confirmLabel = "Reset",
            onConfirm = {
                viewModel.onIntent(SettingsIntent.Reset)
                confirmReset = false
            },
            onDismiss = { confirmReset = false },
        )
    }
}

private fun currentValue(key: SettingKey, settings: Settings): String = when (key) {
    SettingKey.Theme -> settings.theme
    SettingKey.DefaultPriority -> settings.defaultPriority
    SettingKey.DefaultSort -> settings.defaultSort
    SettingKey.AutoHideCompleted -> settings.autoHideCompleted
    SettingKey.Reminders -> if (settings.remindersEnabled) "on" else "off"
    SettingKey.ReminderOffset -> settings.reminderOffset
    SettingKey.TrashAutoDelete -> settings.trashAutoDelete
}

@Composable
private fun OptionsDialog(
    key: SettingKey,
    settings: Settings,
    onPick: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    val selected = currentValue(key, settings)
    Dialog(onDismissRequest = onDismiss) {
        val shape = RoundedCornerShape(20.dp)
        Column(
            modifier = Modifier
                .widthIn(max = 320.dp)
                .background(SurfaceRaised, shape)
                .border(1.5.dp, HairlineStrong, shape)
                .padding(horizontal = 10.dp, vertical = 10.dp),
        ) {
            key.options.forEach { (value, label) ->
                val isSelected = value == selected
                Text(
                    text = label,
                    style = TextStyle(
                        fontFamily = GeneralSans,
                        fontWeight = if (isSelected) FontWeight.W500 else FontWeight.W400,
                        fontSize = 15.sp,
                    ),
                    color = if (isSelected) Primary else Ink900,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                        ) { onPick(value) }
                        .padding(horizontal = 12.dp, vertical = 12.dp),
                )
            }
        }
    }
}
