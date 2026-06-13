package com.journeytix.taskcluster.ui.screens.legal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.journeytix.taskcluster.ui.theme.Canvas
import com.journeytix.taskcluster.ui.theme.Ink900
import com.journeytix.taskcluster.ui.theme.Title

// Placeholder — replaced in Goal 14.
@Composable
fun LegalWebViewScreen(
    title: String,
    url: String,
    onBack: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize().background(Canvas)) {
        Text(text = title, style = Title, color = Ink900)
    }
}
