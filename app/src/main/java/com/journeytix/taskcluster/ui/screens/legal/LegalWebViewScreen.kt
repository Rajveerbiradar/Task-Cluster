package com.journeytix.taskcluster.ui.screens.legal

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.journeytix.taskcluster.ui.components.core.Page
import com.journeytix.taskcluster.ui.components.core.TaskButton
import com.journeytix.taskcluster.ui.theme.GeneralSans
import com.journeytix.taskcluster.ui.theme.Ink500
import com.journeytix.taskcluster.ui.theme.Ink700

/* Legal page — a normal in-app page (back chevron + title + scrollable text).
   No WebView: the body is plain text with the canonical address shown as
   selectable text. Swap in the full policy text here if it gets bundled. */
@Composable
fun LegalWebViewScreen(
    title: String,
    url: String,
    onBack: () -> Unit,
) {
    val context = LocalContext.current
    Page(title = title, onBack = onBack) {
        Text(
            text = "Read the full $title for TaskCluster at the address below.",
            style = TextStyle(
                fontFamily = GeneralSans,
                fontWeight = FontWeight.W400,
                fontSize = 15.sp,
                lineHeight = 22.sp,
            ),
            color = Ink700,
        )
        Spacer(modifier = Modifier.height(12.dp))
        SelectionContainer {
            Text(
                text = url,
                style = TextStyle(
                    fontFamily = GeneralSans,
                    fontWeight = FontWeight.W400,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                ),
                color = Ink500,
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        TaskButton(
            text = "Open in browser",
            onClick = {
                try {
                    context.startActivity(
                        Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        }
                    )
                } catch (_: ActivityNotFoundException) {
                    Toast.makeText(context, "No browser found", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
