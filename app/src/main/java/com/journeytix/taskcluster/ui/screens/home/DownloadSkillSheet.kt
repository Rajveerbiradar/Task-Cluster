package com.journeytix.taskcluster.ui.screens.home

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.journeytix.taskcluster.data.export.SkillExport
import com.journeytix.taskcluster.ui.components.core.TaskIcon
import com.journeytix.taskcluster.ui.components.core.TaskIcons
import com.journeytix.taskcluster.ui.components.feedback.PopupShell
import com.journeytix.taskcluster.ui.theme.GeneralSans
import com.journeytix.taskcluster.ui.theme.Ink500
import com.journeytix.taskcluster.ui.theme.Ink600
import com.journeytix.taskcluster.ui.theme.Ink900

/* Download SKILL — hands an AI a single prompt (icon list baked in) so it can
   write a valid TaskCluster import file. Copy it into a chat, or save it as a
   file. The detailed steps below explain the round-trip. */
@Composable
fun DownloadSkillSheet(onDismiss: () -> Unit) {
    val context = LocalContext.current
    // Folder picker (SAF) — writes the single SKILL file into the chosen directory.
    val saveLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenDocumentTree()
    ) { treeUri ->
        if (treeUri != null) {
            val ok = SkillExport.saveSkillFilesToTree(context, treeUri)
            Toast.makeText(
                context,
                if (ok) "SKILL saved" else "Couldn't save file",
                Toast.LENGTH_SHORT,
            ).show()
        }
        onDismiss()
    }
    PopupShell(onDismiss = onDismiss) {
        Text(
            text = "Download SKILL",
            style = TextStyle(
                fontFamily = GeneralSans,
                fontWeight = FontWeight.W500,
                fontSize = 18.sp,
            ),
            color = Ink900,
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "One prompt that turns any AI into your task writer. " +
                "The available section icons are already built into it.",
            style = TextStyle(
                fontFamily = GeneralSans,
                fontWeight = FontWeight.W400,
                fontSize = 14.sp,
                lineHeight = 20.sp,
            ),
            color = Ink500,
        )
        Spacer(modifier = Modifier.height(14.dp))
        Text(
            text = "How to use it",
            style = TextStyle(
                fontFamily = GeneralSans,
                fontWeight = FontWeight.W500,
                fontSize = 13.sp,
            ),
            color = Ink900,
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "1. Copy the prompt, or save it as a file.\n" +
                "2. Paste it into any AI chat — ChatGPT, Claude, Gemini.\n" +
                "3. Tell the AI what to plan (a roadmap, errands, a study list).\n" +
                "4. It replies with JSON. Save that as a .txt or .json file.\n" +
                "5. Back here: tap + → Import → pick that file.",
            style = TextStyle(
                fontFamily = GeneralSans,
                fontWeight = FontWeight.W400,
                fontSize = 13.sp,
                lineHeight = 20.sp,
            ),
            color = Ink600,
        )
        Spacer(modifier = Modifier.height(16.dp))
        SkillRow(
            label = "Copy prompt",
            icon = { TaskIcon(TaskIcons.Pencil, null, tint = Ink600) },
            onClick = {
                SkillExport.copySkillToClipboard(context)
                Toast.makeText(context, "SKILL copied", Toast.LENGTH_SHORT).show()
                onDismiss()
            },
        )
        SkillRow(
            label = "Save as file",
            icon = { TaskIcon(TaskIcons.Import, null, tint = Ink600) },
            onClick = { saveLauncher.launch(null) },
        )
    }
}

@Composable
private fun SkillRow(
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
