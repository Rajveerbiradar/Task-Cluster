package com.journeytix.taskcluster.ui.screens.trash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.journeytix.taskcluster.data.model.Parent
import com.journeytix.taskcluster.data.model.Section
import com.journeytix.taskcluster.data.model.Task
import com.journeytix.taskcluster.data.preferences.UserPreferences
import com.journeytix.taskcluster.data.repository.TaskRepository
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/* A single trashed thing — a task, a section, or a parent. */
sealed interface TrashEntry {
    val title: String
    val kind: String
    val trashedAt: Long?
    val daysLeft: Int?

    data class TaskEntry(val task: Task, override val daysLeft: Int?) : TrashEntry {
        override val title get() = task.title
        override val kind get() = "Task"
        override val trashedAt get() = task.trashedAt
    }

    data class SectionEntry(val section: Section, override val daysLeft: Int?) : TrashEntry {
        override val title get() = section.title
        override val kind get() = "Section"
        override val trashedAt get() = section.trashedAt
    }

    data class ParentEntry(val parent: Parent, override val daysLeft: Int?) : TrashEntry {
        override val title get() = parent.title
        override val kind get() = "Parent"
        override val trashedAt get() = parent.trashedAt
    }
}

data class TrashUiState(
    val items: List<TrashEntry> = emptyList(),
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
    data class Restore(val entry: TrashEntry) : TrashIntent
    data class DeletePermanently(val entry: TrashEntry) : TrashIntent
    data object RestoreAll : TrashIntent
    data object EmptyTrash : TrashIntent
}

class TrashViewModel(
    private val repository: TaskRepository,
    preferences: UserPreferences,
    private val nowMs: () -> Long = System::currentTimeMillis,
) : ViewModel() {

    private fun daysLeftFor(trashedAt: Long?, ttlDays: Int?): Int? {
        if (ttlDays == null) return null
        val elapsed = TimeUnit.MILLISECONDS.toDays(nowMs() - (trashedAt ?: nowMs())).toInt()
        return (ttlDays - elapsed).coerceAtLeast(0)
    }

    val state: StateFlow<TrashUiState> = combine(
        repository.observeTrashedTasks(),
        repository.observeTrashedSections(),
        repository.observeTrashedParents(),
        preferences.settings,
    ) { tasks, sections, parents, settings ->
        val ttlDays = settings.trashAutoDelete.toIntOrNull()
        val entries = buildList {
            parents.forEach { add(TrashEntry.ParentEntry(it, daysLeftFor(it.trashedAt, ttlDays))) }
            sections.forEach { add(TrashEntry.SectionEntry(it, daysLeftFor(it.trashedAt, ttlDays))) }
            tasks.forEach { add(TrashEntry.TaskEntry(it, daysLeftFor(it.trashedAt, ttlDays))) }
        }.sortedByDescending { it.trashedAt ?: 0L }
        TrashUiState(items = entries, autoDelete = settings.trashAutoDelete)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), TrashUiState())

    fun onIntent(intent: TrashIntent) {
        viewModelScope.launch {
            when (intent) {
                is TrashIntent.Restore -> restore(intent.entry)
                is TrashIntent.DeletePermanently -> deletePermanently(intent.entry)
                TrashIntent.RestoreAll -> {
                    state.value.items.forEach { restore(it) }
                }
                TrashIntent.EmptyTrash -> {
                    state.value.items.forEach { deletePermanently(it) }
                }
            }
        }
    }

    private suspend fun restore(entry: TrashEntry) {
        when (entry) {
            is TrashEntry.TaskEntry -> repository.updateTask(
                entry.task.copy(isTrashed = false, trashedAt = null)
            )
            is TrashEntry.SectionEntry -> repository.restoreSection(entry.section.id)
            is TrashEntry.ParentEntry -> repository.restoreParent(entry.parent.id)
        }
    }

    private suspend fun deletePermanently(entry: TrashEntry) {
        when (entry) {
            is TrashEntry.TaskEntry -> repository.deleteTaskPermanently(entry.task)
            is TrashEntry.SectionEntry -> repository.deleteSectionPermanently(entry.section.id)
            is TrashEntry.ParentEntry -> repository.deleteParentPermanently(entry.parent.id)
        }
    }
}
