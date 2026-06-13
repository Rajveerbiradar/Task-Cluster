package com.journeytix.taskcluster.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

private val TaskClusterColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = PrimaryOn,
    background = Canvas,
    onBackground = Ink900,
    surface = SurfaceRaised,
    onSurface = Ink900,
    surfaceVariant = SurfaceSunken,
    onSurfaceVariant = Ink600,
    outline = HairlineStrong,
    outlineVariant = Hairline,
    error = Red,
    scrim = Scrim,
)

@Composable
fun TaskClusterTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = TaskClusterColorScheme,
        typography = Typography,
        content = content,
    )
}

@Preview(showBackground = true, widthDp = 360, heightDp = 200)
@Composable
private fun CanvasPreview() {
    TaskClusterTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Text(
                text = "today",
                style = Display,
                color = Ink900,
                modifier = Modifier.padding(Space4),
            )
        }
    }
}
