package com.journeytix.taskcluster.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.journeytix.taskcluster.data.model.Parent
import com.journeytix.taskcluster.data.model.Section
import com.journeytix.taskcluster.data.model.Task
import com.journeytix.taskcluster.data.repository.TaskRepository
import com.journeytix.taskcluster.ui.components.planner.TimePillStatus
import java.time.LocalDate
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class HomePage { Home, Tasks }

data class SectionWithTasks(
    val section: Section,
    val tasks: List<Task>,
) {
    val done: Int get() = tasks.count { it.isCompleted }
    val total: Int get() = tasks.size
}

data class ParentWithSections(
    val parent: Parent,
    val sections: List<SectionWithTasks>,
)

data class HomeUiState(
    val today: LocalDate = LocalDate.now(),
    val selected: LocalDate = LocalDate.now(),
    val page: HomePage = HomePage.Home,
    val daily: List<SectionWithTasks> = emptyList(),
    val parents: List<ParentWithSections> = emptyList(),
    val standalone: List<SectionWithTasks> = emptyList(),
    val expandedSections: Set<Long> = emptySet(),
    val expandedParents: Set<Long> = emptySet(),
    val dailyExpanded: Boolean = true,
    val dailyEmoji: String? = null,
) {
    val isToday: Boolean get() = selected == today
    val isReadOnly: Boolean get() = selected.isBefore(today) // past — viewing only
    val isPlanning: Boolean get() = selected.isAfter(today) // future — hidden until then
}

sealed interface HomeIntent {
    data class SelectDate(val date: LocalDate) : HomeIntent
    data class SetPage(val page: HomePage) : HomeIntent
    data class ToggleTask(val task: Task, val checked: Boolean) : HomeIntent
    data class ToggleSection(val id: Long) : HomeIntent
    data class ToggleParent(val id: Long) : HomeIntent
    data class RevealTask(val parentId: Long?, val sectionId: Long) : HomeIntent
    data class AddTask(
        val sectionId: Long,
        val title: String,
        val description: String,
        val dueDate: Long?,
        val dueTime: Long?,
    ) : HomeIntent

    // Creation
    data class CreateParent(val title: String, val emoji: String?, val scheduledDate: String?) : HomeIntent
    data class CreateSection(val title: String, val parentId: Long?, val iconKey: String?, val scheduledDate: String?, val isDaily: Boolean = false) : HomeIntent

    // Parent actions
    data class AddSectionToParent(val parentId: Long) : HomeIntent
    data class RenameParent(val parentId: Long, val title: String) : HomeIntent
    data class ToggleFavourite(val parentId: Long) : HomeIntent
    data class SetParentEmoji(val parentId: Long, val emoji: String?) : HomeIntent
    data class DeleteParent(val parentId: Long) : HomeIntent

    // Section actions
    data class RenameSection(val sectionId: Long, val title: String) : HomeIntent
    data class SetSectionIcon(val sectionId: Long, val iconKey: String?) : HomeIntent
    data class DeleteSection(val sectionId: Long) : HomeIntent

    // Task actions
    data class TrashTask(val taskId: Long) : HomeIntent

    // Daily
    data object ToggleDaily : HomeIntent
    data class SetDailyEmoji(val emoji: String?) : HomeIntent
}

private data class LocalUi(
    val selected: LocalDate,
    val page: HomePage = HomePage.Home,
    val expandedSections: Set<Long> = emptySet(),
    val expandedParents: Set<Long> = emptySet(),
    val dailyExpanded: Boolean = true,
)

class HomeViewModel(
    private val repository: TaskRepository,
    private val userPreferences: com.journeytix.taskcluster.data.preferences.UserPreferences,
    private val today: LocalDate = LocalDate.now(),
) : ViewModel() {

    init {
        viewModelScope.launch {
            if (repository.sectionCount() == 0) {
                repository.addSection(
                    Section(title = "morning", isDaily = true, iconKey = "coffee")
                )
            }
        }
    }

    private val localUi = MutableStateFlow(LocalUi(selected = today))

    val state: StateFlow<HomeUiState> = combine(
        repository.observeSections(),
        repository.observeActiveTasks(),
        repository.observeParents(),
        localUi,
        userPreferences.settings,
    ) { sections, tasks, parents, local, settings ->
        val selectedDateStr = local.selected.toString()
        val tasksBySection = tasks.groupBy { it.sectionId }
        fun withTasks(section: Section) =
            SectionWithTasks(section, tasksBySection[section.id].orEmpty())

        // Filter by date: show if scheduledDate is null (always visible) or matches selected date
        fun Section.matchesDate() = scheduledDate == null || scheduledDate == selectedDateStr
        fun Parent.matchesDate() = scheduledDate == null || scheduledDate == selectedDateStr

        val filteredSections = sections.filter { it.matchesDate() }
        val filteredParents = parents.filter { it.matchesDate() }

        HomeUiState(
            today = today,
            selected = local.selected,
            page = local.page,
            daily = filteredSections.filter { it.isDaily }.map(::withTasks),
            parents = filteredParents.map { parent ->
                ParentWithSections(
                    parent = parent,
                    sections = filteredSections
                        .filter { !it.isDaily && it.parentId == parent.id }
                        .map(::withTasks),
                )
            },
            standalone = filteredSections
                .filter { !it.isDaily && it.parentId == null }
                .map(::withTasks),
            expandedSections = local.expandedSections,
            expandedParents = local.expandedParents,
            dailyExpanded = local.dailyExpanded,
            dailyEmoji = settings.dailyEmoji,
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        HomeUiState(today = today, selected = today),
    )

    fun onIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.SelectDate -> localUi.update { it.copy(selected = intent.date) }
            is HomeIntent.SetPage -> localUi.update { it.copy(page = intent.page) }
            is HomeIntent.ToggleSection -> localUi.update {
                it.copy(expandedSections = it.expandedSections.toggled(intent.id))
            }
            is HomeIntent.ToggleParent -> localUi.update {
                it.copy(expandedParents = it.expandedParents.toggled(intent.id))
            }
            is HomeIntent.RevealTask -> localUi.update {
                it.copy(
                    dailyExpanded = if (intent.parentId == null && state.value.daily.any { s -> s.section.id == intent.sectionId }) true else it.dailyExpanded,
                    expandedParents = if (intent.parentId != null) it.expandedParents + intent.parentId else it.expandedParents,
                    expandedSections = it.expandedSections + intent.sectionId,
                )
            }
            is HomeIntent.ToggleTask -> {
                if (state.value.isReadOnly) return
                viewModelScope.launch {
                    repository.updateTask(
                        intent.task.copy(
                            isCompleted = intent.checked,
                            completedAt = if (intent.checked) System.currentTimeMillis() else null,
                        )
                    )
                }
            }
            is HomeIntent.AddTask -> {
                viewModelScope.launch {
                    repository.addTask(
                        Task(
                            sectionId = intent.sectionId,
                            title = intent.title,
                            description = intent.description,
                            dueDate = intent.dueDate,
                            dueTime = intent.dueTime,
                        )
                    )
                }
            }
            is HomeIntent.CreateParent -> viewModelScope.launch {
                repository.addParent(Parent(title = intent.title, emoji = intent.emoji, scheduledDate = intent.scheduledDate))
            }
            is HomeIntent.CreateSection -> viewModelScope.launch {
                repository.addSection(Section(title = intent.title, parentId = intent.parentId, iconKey = intent.iconKey, scheduledDate = intent.scheduledDate, isDaily = intent.isDaily))
            }
            is HomeIntent.AddSectionToParent -> viewModelScope.launch {
                repository.addSection(Section(title = "new section", parentId = intent.parentId))
            }
            is HomeIntent.RenameParent -> viewModelScope.launch {
                val parent = repository.getParent(intent.parentId) ?: return@launch
                repository.updateParent(parent.copy(title = intent.title))
            }
            is HomeIntent.ToggleFavourite -> viewModelScope.launch {
                val parent = repository.getParent(intent.parentId) ?: return@launch
                repository.updateParent(parent.copy(isFavourite = !parent.isFavourite))
            }
            is HomeIntent.SetParentEmoji -> viewModelScope.launch {
                val parent = repository.getParent(intent.parentId) ?: return@launch
                repository.updateParent(parent.copy(emoji = intent.emoji))
            }
            is HomeIntent.DeleteParent -> viewModelScope.launch {
                repository.deleteParent(intent.parentId)
            }
            is HomeIntent.RenameSection -> viewModelScope.launch {
                val section = repository.getSection(intent.sectionId) ?: return@launch
                repository.updateSection(section.copy(title = intent.title))
            }
            is HomeIntent.SetSectionIcon -> viewModelScope.launch {
                val section = repository.getSection(intent.sectionId) ?: return@launch
                repository.updateSection(section.copy(iconKey = intent.iconKey))
            }
            is HomeIntent.DeleteSection -> viewModelScope.launch {
                repository.deleteSection(intent.sectionId)
            }
            is HomeIntent.TrashTask -> viewModelScope.launch {
                repository.trashTask(intent.taskId)
            }
            is HomeIntent.ToggleDaily -> localUi.update { it.copy(dailyExpanded = !it.dailyExpanded) }
            is HomeIntent.SetDailyEmoji -> viewModelScope.launch {
                userPreferences.setDailyEmoji(intent.emoji)
            }
        }
    }

    private fun Set<Long>.toggled(id: Long): Set<Long> =
        if (id in this) this - id else this + id
}

/* --- Time pill derivation ---
   calm (>50% of the window remaining) → nothing · on-track (>25%) → blue ·
   close (>10%) → amber · due → red · past due → overdue capsule counting up. */
internal fun timePillFor(task: Task, nowMs: Long): Pair<TimePillStatus, String?> {
    val due = task.dueTime ?: task.dueDate ?: return TimePillStatus.Calm to null
    val remaining = due - nowMs
    if (remaining < 0) {
        return TimePillStatus.Overdue to "−${formatDuration(-remaining)}"
    }
    val window = (due - task.createdAt).coerceAtLeast(1)
    val fraction = remaining.toDouble() / window
    val status = when {
        fraction > 0.5 -> TimePillStatus.Calm
        fraction > 0.25 -> TimePillStatus.OnTrack
        fraction > 0.1 -> TimePillStatus.Close
        else -> TimePillStatus.Due
    }
    return if (status == TimePillStatus.Calm) {
        TimePillStatus.Calm to null
    } else {
        status to formatDuration(remaining)
    }
}

internal fun formatDuration(ms: Long): String {
    val minutes = ms / 60_000
    val hours = minutes / 60
    val days = hours / 24
    return when {
        days > 0 -> "${days}d ${hours % 24}h"
        hours > 0 -> "${hours}h ${minutes % 60}m"
        else -> "${minutes}m"
    }
}
