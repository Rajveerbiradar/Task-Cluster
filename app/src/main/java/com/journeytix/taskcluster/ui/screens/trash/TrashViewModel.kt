package com.journeytix.taskcluster.ui.screens.trash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.journeytix.taskcluster.data.model.Task
import com.journeytix.taskcluster.data.preferences.UserPreferences
import com.journeytix.taskcluster.data.repository.TaskRepository
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class TrashItem(
    val task: Task,
    val daysLeft: Int?, // null when auto-delete is off
)

data class TrashUiState(
    val items: List<TrashItem> = emptyList(),
    val autoDelete: String = "30", // "7"|"14"|"30"|"never"
) {
    val isEmpty: Boolean get() = items.isEmpty()
    val bannerText: String
        get() = if (autoDelete == "never") {
            "Items in trash are kept until you delete them."
        } else {
            "Items are permanently deleted after $autoDelete days."
        }
}

sealed interface TrashIntent {
    data class Restore(val task: Task) : TrashIntent
    data class DeletePermanently(val task: Task) : TrashIntent
    data object RestoreAll : TrashIntent
    data object EmptyTrash : TrashIntent
}

class TrashViewModel(
    private val repository: TaskRepository,
    preferences: UserPreferences,
    private val nowMs: () -> Long = System::currentTimeMillis,
) : ViewModel() {

    val state: StateFlow<TrashUiState> = combine(
        repository.observeTrashedTasks(),
        preferences.settings,
    ) { tasks, settings ->
        val ttlDays = settings.trashAutoDelete.toIntOrNull()
        TrashUiState(
            items = tasks.map { task ->
                val daysLeft = if (ttlDays == null) {
                    null
                } else {
                    val elapsed = TimeUnit.MILLISECONDS.toDays(
                        nowMs() - (task.trashedAt ?: nowMs())
                    ).toInt()
                    (ttlDays - elapsed).coerceAtLeast(0)
                }
                TrashItem(task = task, daysLeft = daysLeft)
            },
            autoDelete = settings.trashAutoDelete,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), TrashUiState())

    fun onIntent(intent: TrashIntent) {
        viewModelScope.launch {
            when (intent) {
                is TrashIntent.Restore -> repository.updateTask(
                    intent.task.copy(isTrashed = false, trashedAt = null)
                )
                is TrashIntent.DeletePermanently -> repository.deleteTaskPermanently(intent.task)
                TrashIntent.RestoreAll -> repository.restoreAllTrashed()
                TrashIntent.EmptyTrash -> repository.emptyTrash()
            }
        }
    }
}
