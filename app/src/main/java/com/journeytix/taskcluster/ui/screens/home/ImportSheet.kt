package com.journeytix.taskcluster.ui.screens.home

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.journeytix.taskcluster.ui.components.core.TaskButton
import com.journeytix.taskcluster.ui.components.core.TaskButtonVariant
import com.journeytix.taskcluster.ui.components.core.TaskIcon
import com.journeytix.taskcluster.ui.components.core.TaskIcons
import com.journeytix.taskcluster.ui.components.feedback.PopupShell
import com.journeytix.taskcluster.ui.theme.GeneralSans
import com.journeytix.taskcluster.ui.theme.HairlineStrong
import com.journeytix.taskcluster.ui.theme.Ink400
import com.journeytix.taskcluster.ui.theme.Ink500
import com.journeytix.taskcluster.ui.theme.Ink600
import com.journeytix.taskcluster.ui.theme.Ink900
import com.journeytix.taskcluster.ui.theme.RadiusSm
import com.journeytix.taskcluster.ui.theme.Surface

/* Import — paste the AI-generated JSON directly, or pick a .txt file. Both paths
   hand the raw text to [onImport], which parses + inserts. */
@Composable
fun ImportSheet(
    onImport: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    val context = LocalContext.current
    var pasted by remember { mutableStateOf("") }

    val filePicker = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri != null) {
            val content = runCatching {
                context.contentResolver.openInputStream(uri)?.bufferedReader()?.use { it.readText() }
            }.getOrNull()
            if (!content.isNullOrBlank()) onImport(content)
        }
    }

    PopupShell(onDismiss = onDismiss) {
        Text(
            text = "Import",
            style = TextStyle(
                fontFamily = GeneralSans,
                fontWeight = FontWeight.W500,
                fontSize = 18.sp,
            ),
            color = Ink900,
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "Paste the JSON an AI gave you, or pick a .txt file.",
            style = TextStyle(
                fontFamily = GeneralSans,
                fontWeight = FontWeight.W400,
                fontSize = 14.sp,
                lineHeight = 20.sp,
            ),
            color = Ink500,
        )
        Spacer(modifier = Modifier.height(14.dp))
        // Bounded, internally scrollable paste field so big JSON can't push the
        // action buttons off-screen.
        val pasteScroll = rememberScrollState()
        val fieldShape = RoundedCornerShape(RadiusSm)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 120.dp, max = 180.dp)
                .background(Surface, fieldShape)
                .border(1.dp, HairlineStrong, fieldShape)
                .padding(horizontal = 14.dp, vertical = 12.dp),
        ) {
            BasicTextField(
                value = pasted,
                onValueChange = { pasted = it },
                textStyle = TextStyle(
                    fontFamily = GeneralSans,
                    fontWeight = FontWeight.W400,
                    fontSize = 15.sp,
                    lineHeight = 21.sp,
                    color = Ink900,
                ),
                cursorBrush = SolidColor(Ink900),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 156.dp)
                    .verticalScroll(pasteScroll),
                decorationBox = { inner ->
                    if (pasted.isEmpty()) {
                        Text(
                            text = "Paste import JSON here",
                            style = TextStyle(
                                fontFamily = GeneralSans,
                                fontWeight = FontWeight.W400,
                                fontSize = 15.sp,
                                lineHeight = 21.sp,
                            ),
                            color = Ink400,
                        )
                    }
                    inner()
                },
            )
        }
        Spacer(modifier = Modifier.height(12.dp))

        // Pick a .txt file
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                ) { filePicker.launch(arrayOf("text/plain")) }
                .padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TaskIcon(TaskIcons.Import, null, size = 18.dp, tint = Ink600)
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Choose a .txt file",
                style = TextStyle(
                    fontFamily = GeneralSans,
                    fontWeight = FontWeight.W400,
                    fontSize = 15.sp,
                ),
                color = Ink900,
            )
        }

        Spacer(modifier = Modifier.height(14.dp))
        TaskButton(
            text = "Import",
            onClick = { onImport(pasted) },
            variant = TaskButtonVariant.Primary,
            enabled = pasted.isNotBlank(),
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

/* Shown when imported parents clash with existing ones (same title). */
@Composable
fun ImportConflictDialog(
    duplicateTitles: List<String>,
    onResolve: (com.journeytix.taskcluster.ui.screens.home.ImportStrategy) -> Unit,
    onDismiss: () -> Unit,
) {
    PopupShell(onDismiss = onDismiss) {
        Text(
            text = "Already exists",
            style = TextStyle(
                fontFamily = GeneralSans,
                fontWeight = FontWeight.W500,
                fontSize = 18.sp,
            ),
            color = Ink900,
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "These parents are already here: ${duplicateTitles.joinToString(", ")}. " +
                "What should the import do with them?",
            style = TextStyle(
                fontFamily = GeneralSans,
                fontWeight = FontWeight.W400,
                fontSize = 14.sp,
                lineHeight = 20.sp,
            ),
            color = Ink500,
        )
        Spacer(modifier = Modifier.height(16.dp))
        TaskButton(
            text = "Keep both (rename new)",
            onClick = { onResolve(com.journeytix.taskcluster.ui.screens.home.ImportStrategy.Rename) },
            variant = TaskButtonVariant.Primary,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(8.dp))
        TaskButton(
            text = "Replace existing",
            onClick = { onResolve(com.journeytix.taskcluster.ui.screens.home.ImportStrategy.Replace) },
            variant = TaskButtonVariant.Secondary,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(8.dp))
        TaskButton(
            text = "Don't import duplicates",
            onClick = { onResolve(com.journeytix.taskcluster.ui.screens.home.ImportStrategy.Skip) },
            variant = TaskButtonVariant.Secondary,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
