package com.journeytix.taskcluster.ui.screens.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.journeytix.taskcluster.ui.components.core.TaskButton
import com.journeytix.taskcluster.ui.components.feedback.PopupShell
import com.journeytix.taskcluster.ui.theme.GeneralSans
import com.journeytix.taskcluster.ui.theme.Ink400
import com.journeytix.taskcluster.ui.theme.Ink900
import com.journeytix.taskcluster.ui.theme.SurfaceSunken

enum class NameFormMode { Parent, Section }

@Composable
fun NameForm(
    mode: NameFormMode,
    initialTitle: String = "",
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    var title by remember { mutableStateOf(initialTitle) }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    PopupShell(onDismiss = onDismiss) {
        Text(
            text = if (mode == NameFormMode.Parent) "New parent" else "New section",
            style = TextStyle(
                fontFamily = GeneralSans,
                fontWeight = FontWeight.W500,
                fontSize = 18.sp,
            ),
            color = Ink900,
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = title,
            onValueChange = { title = it },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            placeholder = {
                Text(
                    text = if (mode == NameFormMode.Parent) "Parent name" else "Section name",
                    color = Ink400,
                )
            },
            textStyle = TextStyle(
                fontFamily = GeneralSans,
                fontWeight = FontWeight.W400,
                fontSize = 16.sp,
                color = Ink900,
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (title.isNotBlank()) {
                        onConfirm(title.trim())
                    }
                }
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = SurfaceSunken,
                unfocusedContainerColor = SurfaceSunken,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
        )
        Spacer(modifier = Modifier.height(20.dp))
        TaskButton(
            text = if (mode == NameFormMode.Parent) "Add parent" else "Add section",
            onClick = {
                if (title.isNotBlank()) {
                    onConfirm(title.trim())
                }
            },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
