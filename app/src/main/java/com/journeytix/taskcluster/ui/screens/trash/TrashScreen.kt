package com.journeytix.taskcluster.ui.screens.trash

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.journeytix.taskcluster.data.model.Task
import com.journeytix.taskcluster.ui.components.core.Page
import com.journeytix.taskcluster.ui.components.core.TaskButton
import com.journeytix.taskcluster.ui.components.core.TaskButtonVariant
import com.journeytix.taskcluster.ui.components.feedback.ConfirmDialog
import com.journeytix.taskcluster.ui.components.feedback.TaskBanner
import com.journeytix.taskcluster.ui.theme.GeneralSans
import com.journeytix.taskcluster.ui.theme.Hairline
import com.journeytix.taskcluster.ui.theme.HairlineStrong
import com.journeytix.taskcluster.ui.theme.Ink400
import com.journeytix.taskcluster.ui.theme.Ink500
import com.journeytix.taskcluster.ui.theme.Ink700
import com.journeytix.taskcluster.ui.theme.Ink900
import com.journeytix.taskcluster.ui.theme.RadiusPill
import com.journeytix.taskcluster.ui.theme.Surface
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import org.koin.androidx.compose.koinViewModel

private val ShortDate = DateTimeFormatter.ofPattern("MMM d", Locale.ENGLISH)

private fun formatDate(epochMs: Long?): String =
    epochMs?.let {
        Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate().format(ShortDate)
    } ?: "—"

private sealed interface TrashConfirm {
    data class Delete(val task: Task) : TrashConfirm
    data object Empty : TrashConfirm
    data object RestoreAll : TrashConfirm
}

@Composable
fun TrashScreen(
    onBack: () -> Unit,
    viewModel: TrashViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()
    var confirm by remember { mutableStateOf<TrashConfirm?>(null) }

    Page(title = "Trash", onBack = onBack) {
        TaskBanner(
            text = state.bannerText,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 14.dp),
        )
        if (state.isEmpty) {
            Text(
                text = "Trash is empty",
                style = TextStyle(
                    fontFamily = GeneralSans,
                    fontWeight = FontWeight.W400,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                ),
                color = Ink400,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 40.dp),
            )
        } else {
            Column {
                state.items.forEachIndexed { index, item ->
                    TrashRow(
                        item = item,
                        onRestore = { viewModel.onIntent(TrashIntent.Restore(item.task)) },
                        onDelete = { confirm = TrashConfirm.Delete(item.task) },
                        divider = index < state.items.lastIndex,
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 18.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    TaskButton(
                        text = "Restore all",
                        onClick = { confirm = TrashConfirm.RestoreAll },
                        variant = TaskButtonVariant.Secondary,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
                Box(modifier = Modifier.weight(1f)) {
                    TaskButton(
                        text = "Empty trash",
                        onClick = { confirm = TrashConfirm.Empty },
                        variant = TaskButtonVariant.Primary,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    }

    when (val c = confirm) {
        is TrashConfirm.Delete -> ConfirmDialog(
            title = "Delete permanently",
            message = "This task will be permanently deleted. This action cannot be undone.",
            confirmLabel = "Delete",
            onConfirm = {
                viewModel.onIntent(TrashIntent.DeletePermanently(c.task))
                confirm = null
            },
            onDismiss = { confirm = null },
        )
        TrashConfirm.Empty -> ConfirmDialog(
            title = "Empty trash",
            message = "All ${state.items.size} items in trash will be permanently deleted. This cannot be undone.",
            confirmLabel = "Empty trash",
            onConfirm = {
                viewModel.onIntent(TrashIntent.EmptyTrash)
                confirm = null
            },
            onDismiss = { confirm = null },
        )
        TrashConfirm.RestoreAll -> ConfirmDialog(
            title = "Restore all",
            message = "All ${state.items.size} items will be restored to your task list.",
            confirmLabel = "Restore all",
            confirmDanger = false,
            onConfirm = {
                viewModel.onIntent(TrashIntent.RestoreAll)
                confirm = null
            },
            onDismiss = { confirm = null },
        )
        null -> Unit
    }
}

@Composable
private fun TrashRow(
    item: TrashItem,
    onRestore: () -> Unit,
    onDelete: () -> Unit,
    divider: Boolean,
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.task.title,
                    style = TextStyle(
                        fontFamily = GeneralSans,
                        fontWeight = FontWeight.W400,
                        fontSize = 16.sp,
                        lineHeight = 21.sp,
                    ),
                    color = Ink900,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                val daysLeft = item.daysLeft?.let { " · ${it}d left" } ?: ""
                Text(
                    text = "Due ${formatDate(item.task.dueDate)} · " +
                        "Deleted ${formatDate(item.task.trashedAt)}$daysLeft",
                    style = TextStyle(
                        fontFamily = GeneralSans,
                        fontWeight = FontWeight.W400,
                        fontSize = 12.sp,
                        lineHeight = 17.sp,
                    ),
                    color = Ink500,
                    modifier = Modifier.padding(top = 4.dp),
                )
            }
            PillAction(text = "Restore", onClick = onRestore)
            PillAction(text = "Delete", onClick = onDelete)
        }
        if (divider) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Hairline),
            )
        }
    }
}

@Composable
private fun PillAction(text: String, onClick: () -> Unit) {
    val shape = RoundedCornerShape(RadiusPill)
    Box(
        modifier = Modifier
            .height(30.dp)
            .background(Surface, shape)
            .border(1.dp, HairlineStrong, shape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick,
            )
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontFamily = GeneralSans,
                fontWeight = FontWeight.W500,
                fontSize = 13.sp,
            ),
            color = Ink700,
        )
    }
}
