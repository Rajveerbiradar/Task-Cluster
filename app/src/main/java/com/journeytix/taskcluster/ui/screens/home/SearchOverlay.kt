package com.journeytix.taskcluster.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.journeytix.taskcluster.ui.components.core.TaskIcon
import com.journeytix.taskcluster.ui.components.core.TaskIconButton
import com.journeytix.taskcluster.ui.components.core.TaskIcons
import com.journeytix.taskcluster.ui.components.feedback.PopupShell
import com.journeytix.taskcluster.ui.components.planner.TimePill
import com.journeytix.taskcluster.ui.components.planner.TimePillStatus
import com.journeytix.taskcluster.ui.theme.*

/* A searchable entity — a task, a section, or a parent. [id] carries the kind so
   the caller knows what to reveal (e.g. "task:12", "section:4", "parent:7"). */
data class SearchItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val searchText: String,
    val status: TimePillStatus = TimePillStatus.Calm,
    val time: String? = null,
)

private data class ScoredItem(val item: SearchItem, val score: Int)

// Simple fuzzy match: substring match + character distance.
private fun fuzzyScore(query: String, text: String): Int {
    val q = query.lowercase()
    val t = text.lowercase()
    if (q.isEmpty()) return 0
    if (t.contains(q)) return 100 + (100 - t.indexOf(q).coerceAtMost(100))
    var score = 0
    var lastIdx = -1
    for (c in q) {
        val idx = t.indexOf(c, lastIdx + 1)
        if (idx > lastIdx) {
            score += 10
            if (idx == lastIdx + 1) score += 5
            lastIdx = idx
        }
    }
    return score
}

@Composable
fun SearchOverlay(
    items: List<SearchItem>,
    onDismiss: () -> Unit,
    onSelect: (SearchItem) -> Unit,
) {
    var query by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    val results = remember(query, items) {
        if (query.isBlank()) emptyList()
        else items
            .map { ScoredItem(it, fuzzyScore(query, it.searchText)) }
            .filter { it.score > 0 }
            .sortedByDescending { it.score }
            .take(30)
    }

    LaunchedEffect(Unit) { focusRequester.requestFocus() }

    PopupShell(onDismiss = onDismiss) {
        Text(
            text = "Search",
            style = TextStyle(fontFamily = GeneralSans, fontWeight = FontWeight.W500, fontSize = 18.sp),
            color = Ink900,
        )
        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .clip(RoundedCornerShape(RadiusSm))
                .background(SurfaceSunken)
                .border(1.dp, Hairline, RoundedCornerShape(RadiusSm))
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TaskIcon(TaskIcons.Search, null, size = 18.dp, tint = Ink500)
            Spacer(modifier = Modifier.width(10.dp))
            BasicTextField(
                value = query,
                onValueChange = { query = it },
                modifier = Modifier.weight(1f).focusRequester(focusRequester),
                textStyle = TextStyle(fontFamily = GeneralSans, fontWeight = FontWeight.W400, fontSize = 15.sp, color = Ink900),
                cursorBrush = SolidColor(Primary),
                singleLine = true,
                decorationBox = { inner ->
                    Box {
                        if (query.isEmpty()) Text("Search tasks, sections, parents", style = TextStyle(fontFamily = GeneralSans, fontSize = 15.sp), color = Ink400)
                        inner()
                    }
                },
            )
            if (query.isNotEmpty()) {
                TaskIconButton(icon = TaskIcons.X, label = "Clear", size = 24.dp, onClick = { query = "" })
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(modifier = Modifier.fillMaxWidth().height(300.dp)) {
            if (query.isNotBlank() && results.isEmpty()) {
                item {
                    Text(
                        text = "Nothing matches \"$query\"",
                        style = TextStyle(fontFamily = GeneralSans, fontSize = 14.sp),
                        color = Ink400,
                        modifier = Modifier.padding(vertical = 24.dp).fillMaxWidth(),
                    )
                }
            }
            items(results) { scored ->
                val item = scored.item
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) {
                            onSelect(item)
                        }
                        .padding(vertical = 10.dp, horizontal = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = item.subtitle,
                            style = TextStyle(fontFamily = GeneralSans, fontSize = 11.sp),
                            color = Ink400,
                            maxLines = 1,
                        )
                        Text(
                            text = item.title,
                            style = TextStyle(fontFamily = GeneralSans, fontWeight = FontWeight.W400, fontSize = 15.sp),
                            color = Ink900,
                            maxLines = 1,
                        )
                    }
                    if (item.time != null && item.status != TimePillStatus.Calm) {
                        TimePill(status = item.status, label = item.time)
                    }
                }
            }
        }
    }
}
